#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<arpa/inet.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<unistd.h>
#include<signal.h>
#include<errno.h>
#include<pthread.h>

#define PADDING_FRONT 3
#define BUF_SIZE 200
#define PADDING_BACK 1

inline int check(char* call, int result){
    if(result == 0) return result;
    perror(call);
    exit(1);
}

// message 프로토콜 관련
char escape[] = "exit\n";
struct message{
    uint16_t len;
    char type;
    char load[];
};

// 쓰레드 세이프
char* createNamedMsg(char* msg, char* name){
    int msglen = strlen(msg);
    int namelen = strlen(name);
    char* buf = (char*)malloc(namelen + 2 + msglen + 1);

    memcpy(buf, name, namelen);
    memcpy(buf+namelen, ": ", 2);
    memcpy(buf+namelen + 2, msg, msglen);
    buf[namelen + 2 + msglen] = 0;
    return buf;
}

// user 관리 관련
pthread_mutex_t lock;
struct user{
    int sock;
    struct sockaddr_in addr;
    char name[BUF_SIZE];
};
const int reserved = 4;
int users_max = 5; // stdin, stdout, stderr, sockfd, --> 여기부터 CLIENT
int users_cnt = 0;
struct user** users;

// 쓰레드 세이프
void addUser(struct user new_user){
    pthread_mutex_lock(&lock);
    while(new_user.sock >= users_max){
        users_max *= 2;
        users = realloc(users, users_max * sizeof(struct user*));
    }
    struct user* new = (struct user*)malloc(sizeof(struct user));
    memcpy(new, &new_user, sizeof(struct user));
    users[new_user.sock] = new;
    users_cnt++;
    pthread_mutex_unlock(&lock);
}

// 자기 자신의 이름을 설정할 때만 사용
static inline void setUserName(int sock, char* name, int size){
    memcpy(users[sock]->name, name, size);
}

// 자기 자신의 이름을 가져올 때만 사용
static inline char* getUserName(int sock){
    return users[sock]->name;
}
// 자기 자신의 주소를 가져올 때만 사용
static inline char* getUserAddr(int sock){
    return inet_ntoa(users[sock]->addr.sin_addr);
}
// 자기 자신의 포트를 가져올 때만 사용
static inline int getUserPort(int sock){
    return ntohs(users[sock]->addr.sin_port);
}


// 쓰레드 세이프
void deleteUser(int sock){
    pthread_mutex_lock(&lock);
    struct user* disconnected = users[sock];
    free(disconnected);
    users[sock] = NULL;
    users_cnt -= 1;
    pthread_mutex_unlock(&lock);
}

// 쓰레드 세이프
void broadcast(int sock, char* stream, int size){
    pthread_mutex_lock(&lock);
    int sent = 0;
    int i=0;
    for(i = reserved; i<users_max;i++){
        if(users[i] == NULL) continue;
        send(users[i]->sock, stream, size, 0);
        sent++;
        if(sent == users_cnt)
            break;
    }
    pthread_mutex_unlock(&lock);
}

void *thread_receiver(void *arg){
    int size;
    int s = *(int*)arg;
    free((int*)arg);
    int max_size = BUF_SIZE;
    struct message* msg = malloc(max_size + PADDING_BACK);
    char* tmp;
    int connection = 1;
    while(connection){
        // 1. 메시지의 길이 처리
        // 2. 메시지의 길이만큼 한번더 처리
        // optional1. 만약 메시지를 모두 읽지 못했다면 계속 읽음
        // optional2. 만약 현재 최대 메시지 버퍼보다 크면 버퍼를 확장
        int curr_read = 0;
        int len = 0;
        do{
            size = recv(s, ((char*)msg) + curr_read, PADDING_FRONT + len - curr_read, 0);
            if(size < 0){
                perror("read");
                close(s);
                return;
            }
            if(size == 0){
                printf("Disconnected\n");
                connection = 0;
                break;
            }
            len = ntohs(msg->len);
            curr_read += size;
            
            if (max_size < PADDING_FRONT+len+PADDING_BACK){
                printf("reallocation\n");
                max_size = PADDING_FRONT+len+PADDING_BACK;
                msg = realloc(msg, max_size);
            }
        }while(curr_read < PADDING_FRONT+len);
        msg->load[len] = 0;

        // 프로토콜에 따라 처리
        switch(msg->type){
        case 'c':
            setUserName(s, msg->load, len);
            break;
        case 'm':
            // 이름 붙인 메시지로 변환 후 메시지 구조체에 저장
            tmp = createNamedMsg(msg->load, getUserName(s));
            size = strlen(tmp);
            memcpy(msg->load, tmp, size);
            free(tmp);

            // 전송
            msg->len = htons(size);
            broadcast(s, (char*)msg, 3 + size);
            break;
        case 'q':
            deleteUser(s);
            printf("Disconnected: num_client:  %d\n", users_cnt);
            connection = 0;
            break;
        }
    }
    close(s);
    free(msg);
    return NULL;
}

int main(int argc, char *argv[]){
    int sockfd, newfd;
    struct sockaddr_in my_addr, their_addr;
    unsigned int sin_size, len_inet;
    int pid, size;
    pthread_t t_id;
    int *arg;
    users = (struct user**)malloc(users_max*sizeof(struct user*));
    
    pthread_mutex_init(&lock, NULL);

    if(argc < 2){
        fprintf(stderr, "Usage: %s <PORT>\n", argv[0]);
        return 0;
    }
    sockfd = socket(PF_INET, SOCK_STREAM, 0);

    my_addr.sin_family = PF_INET;
    my_addr.sin_port = htons(atoi(argv[1]));
    my_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    memset(&(my_addr.sin_zero), 0, 8);

    sin_size = sizeof(struct sockaddr_in);

    check("bind", bind(sockfd, (struct sockaddr*)&my_addr, sin_size));

    check("listen", listen(sockfd, 5));

    printf("wating...\n");

    while(1){
        newfd = accept(sockfd, (struct sockaddr*)&their_addr, &sin_size);
        if(newfd == -1){
            perror("accept");
            exit(0);
        }
        // 새로운 유저 추가
        struct user new_user = {
            newfd, their_addr
        };
        addUser(new_user);
        printf("Connected: %s %d num_client: %d\n", 
            getUserAddr(newfd), getUserPort(newfd), users_cnt);
        // 새로운 쓰레드 추가
        arg = (int*)malloc(sizeof(int));
        *arg = newfd;
        pthread_create(&t_id, NULL, thread_receiver, arg);
        pthread_detach(t_id);
    }
    close(sockfd);
    return 0;

}