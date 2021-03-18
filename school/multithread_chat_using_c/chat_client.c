#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<arpa/inet.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<unistd.h>
#include<pthread.h>
#include <netinet/tcp.h>

#define PADDING_FRONT 3
#define BUF_SIZE 500
#define PADDING_BACK 1

// message 프로토콜 관련
char escape[] = "/q\n";
struct message{
    uint16_t len;
    char type;
    char load[];
};

inline int check(char* call, int result){
    if(result == 0) return result;
    perror(call);
    exit(1);
}

void *thread_sender(void *arg){
    int sockfd = *(int*)arg;
    int size, sent;
    struct message* msg = malloc(3+BUF_SIZE+1);
    char* buf = (msg->load);
    
    msg->type = 'm';
    while(1){
        size = read(0, buf, BUF_SIZE);
        if(size <= 0){
            perror("read");
            exit(1);
        }
        if(strncmp(buf, escape, 2) == 0){
            msg->len = 0;
            msg->type = 'q';
            send(sockfd, msg, 3, 0);
            shutdown(sockfd, SHUT_WR);
            exit(1); // thread_sender 종료
        }
        // 입력된 문자열 끝의 '\n' 제거
        buf[size-1] = 0;
        // 전송
        msg->len = htons(size);
        send(sockfd, msg, PADDING_FRONT + size, 0);
    }
    return NULL;
}

int main(int argc, char *argv[]){
    int sockfd, newfd;
    struct sockaddr_in my_addr, their_addr;
    unsigned int sin_size, len_inet;
    int size, len;
    pthread_t t_id;
    int max_size = BUF_SIZE;
    struct message* msg = malloc(max_size + PADDING_BACK);

    if(argc < 4){
        fprintf(stderr, "Usage: %s <IP> <PORT> <USER>\n", argv[0]);
        return 0;
    }
    sockfd = socket(AF_INET, SOCK_STREAM, 0);

    int isActive = 1;

    my_addr.sin_family = AF_INET;
    my_addr.sin_port = htons(atoi(argv[2]));
    my_addr.sin_addr.s_addr = inet_addr(argv[1]);
    memset(&(my_addr.sin_zero), 0, 8);

    sin_size = sizeof(struct sockaddr_in);

    check("connect", connect(sockfd, (struct sockaddr *)&my_addr, sin_size));
    printf("Connected. (Enter \"/q\" to quit)\n");

    size = strlen(argv[3]);
    msg->len = htons(size);
    msg->type = 'c';
    memcpy(msg->load, argv[3], size);
    size = send(sockfd, msg, 3+size, 0);

    pthread_create(&t_id, NULL, thread_sender, &sockfd);
    pthread_detach(t_id);
    
    int connection = 1;
    while(connection){
        int curr_read = 0;

        len = 0;
        do{
            size = recv(sockfd, ((char*)msg) + curr_read, PADDING_FRONT + len - curr_read, 0);
            if(size < 0){
                perror("read");
                exit(1);
            }
            if(size == 0){
                printf("Disconnected\n");
                connection = 0;
                break;
            }
            len = ntohs(msg->len);
            curr_read += size;
            
            if (max_size < PADDING_FRONT+len+PADDING_BACK){
                max_size = PADDING_FRONT+len+PADDING_BACK;
                msg = realloc(msg, max_size);
            }
        }while(curr_read < PADDING_FRONT+len);
        if(!connection) 
            break;
        msg->load[len] = 0;
        printf("%s\n", msg->load);
    }
    close(sockfd);
    free(msg);
    return 0;
}