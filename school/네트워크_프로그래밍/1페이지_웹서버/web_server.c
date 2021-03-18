#include<stdio.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<arpa/inet.h>
#include<stdlib.h>
#include<signal.h>
#include<sys/wait.h>
#include<errno.h>
#include<string.h>
#include<sys/stat.h>
#include<unistd.h>
#include <fcntl.h>

void zombie_handler(){
    int status;
    pid_t spid;
    spid = wait(&status);
}
int main(int argc, char* argv[]){
    int listen_s, data_s;
    struct sockaddr_in server_addr;
    struct sockaddr_in client_addr;
    int addr_len;
    char buf[1000];
    int len;

    if(argc > 2){
        printf("too many arguments supplied\n");
    }
    if(argc != 2){
        printf("argument must be supplied with PORT\n \
        usage: ./web_server <PORT>\n");
        return 0;
    }
    // 시그액션
    struct sigaction action;
    action.sa_handler = zombie_handler;
    action.sa_flags = 0;
    sigemptyset(&action.sa_mask);
    sigaction(SIGCHLD, &action, 0);

    listen_s = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP);
    if(listen_s == -1){
        perror("listen");
    }

    memset(&server_addr, 0, sizeof(server_addr));
    server_addr.sin_family = PF_INET;
    server_addr.sin_port = htons(atoi(argv[1]));
    server_addr.sin_addr.s_addr = htonl(INADDR_ANY);

    if(bind(listen_s, (struct sockaddr*)&server_addr, sizeof(server_addr))){
        perror("bind");
        return 0;
    }

    if(listen(listen_s, 5) == -1){
        perror("listen");
        return 0;
    }
    printf("Waiting...\n");

    addr_len = sizeof(struct sockaddr_in);
    while(1){
        data_s = accept(listen_s, (struct sockaddr*)&client_addr, &addr_len);
        if(data_s == -1){
            switch(errno){
            case EINTR:
                printf("Interrupt\n");
                continue;
            default:
                perror("error");
                return 0;
            }
        }else{
            // 연결 성공
            char addr[INET_ADDRSTRLEN];
            inet_ntop(PF_INET, &(client_addr.sin_addr), addr, INET_ADDRSTRLEN);
            printf("Connected: %s %d\n", addr, htons(client_addr.sin_port));

        }

        if(fork() == 0){ // 포크
            close(listen_s);
            while(1){
                len = recv(data_s, buf, 1000, 0);
                if(len == -1){
                    perror("recv");
                    return 0;
                }
                if(len==0){
                    printf("disconnected\n");
                    break;
                }
                buf[1000] = '\0';
                printf("==== uri message ====\n%s", buf);

                // 필요한 정보
                char uri[100];
                // 라인 파싱
                char* line_start = buf;
                char* line_end;
                while(line_end = (char*)memchr(line_start, '\n', strlen(line_start)) ){
                    *line_end = 0;

                    if (*(line_end-1) == '\r') // 리턴 처리
                        *(line_end-1) = 0;
                    if (memcmp(line_start, "GET", 3) == 0){ // GET
                        char* parse_end = (char*)memchr(line_start+4,' ',strlen(line_start+4));
                        *parse_end = 0;
                        memcpy(uri, line_start+4, strlen(line_start+4));
                        uri[strlen(line_start+4)] = 0;
                        break; // 현재 나머지는 필요없음
                    }else{
                        ; // 그 외
                    }

                    line_start = line_end+1;
                }

                // /->index 처리
                if (strcmp(uri, "/") == 0){
                    strncpy(uri, "/index.html\0", sizeof("/index.html\0"));
                }

                // http uri message
                char response[] = "HTTP/1.1 %s\r\n" \
                                "Content-Type: %s\r\n" \
                                "Content-Length: %d\r\n" \
                                "\r\n";
                // 파일 읽기
                int fd = open(uri+1, O_RDONLY);
                struct stat st;
                
                // 404 처리
                if (fd == -1){
                    fd = open("error.html\0", O_RDONLY);
                    if(stat("error.html\0", &st) == -1){
                        perror("stat");
                        exit(1);
                    }
                    snprintf(buf, 1000, response, "404 Not Found", "text/html", st.st_size);
                    send(data_s, buf, strlen(buf), 0);
                    printf("==== Reply message ====\n%s", buf);
                    
                    read(fd, buf, st.st_size);
                    
                    send(data_s, buf, st.st_size, 0);
                    close(fd);
                    continue;
                }
                
                // 성공 처리
                if(stat(uri+1, &st) == -1){
                    perror("stat");
                    exit(1);
                }
                // 타입
                char contentType[100];
                char* tmp = strrchr(uri, '.')+1;
                if (memcmp(tmp, "html", 4) == 0){
                    strncpy(contentType, "text/html\0", sizeof("text/html\0")); 
                }else if(memcmp(tmp, "jpg", 3) == 0){
                    strncpy(contentType, "image/jpg\0", sizeof("image/jpg\0")); 
                }else if(memcmp(tmp, "png", 3) == 0){
                    strncpy(contentType, "image/png\0", sizeof("image/png\0")); 
                }else if(memcmp(tmp, "mpeg", 4) == 0){
                    strncpy(contentType, "audio/mpeg\0", sizeof("audio/mpeg\0")); 
                }else if(memcmp(tmp, "ogg", 3) == 0){
                    strncpy(contentType, "audio/ogg\0", sizeof("audio/ogg\0")); 
                }else if(memcmp(tmp, "mp4", 4) == 0){
                    strncpy(contentType, "video/mp4\0", sizeof("video/mp4\0")); 
                }

                // 헤더 먼저 보내기
                snprintf(buf, 1000, response, "200 OK", contentType, st.st_size);
                send(data_s, buf, strlen(buf), 0);
                printf("==== Reply message ====\n%s", buf);

                // 데이터 쪼개서 보내기
                len = 0;
                while(len < st.st_size){
                    len += read(fd, buf, 1000);
                    send(data_s, buf, 1000, 0);
                }
                close(fd);
                continue;
            }
            close(data_s);
            exit(0);
            // return 0; // return해도 상관없음
        }
        close(data_s);
    }
    close(listen_s);
    return 0;
}
