#include<stdio.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<arpa/inet.h>
#include<stdlib.h>
#include<signal.h>
#include<sys/wait.h>
#include<errno.h>
#include<string.h>
#include<unistd.h>

int main(int argc, char* argv[]){
    int sockfd, newfd;
    struct sockaddr_in server_addr;
    struct sockaddr_in client_addr;
    int addr_len;
    char buf[1000];
    int len;
    fd_set readfds, readtemp;
    int max_fd = 0;
    int result;

    if(argc != 2){
        printf("usage: ./web_server <PORT>\n");
        return 0;
    }

    sockfd = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP);
    if(sockfd == -1){
        perror("listen");
    }

    memset(&server_addr, 0, sizeof(server_addr));
    server_addr.sin_family = PF_INET;
    server_addr.sin_port = htons(atoi(argv[1]));
    server_addr.sin_addr.s_addr = htonl(INADDR_ANY);

    if(bind(sockfd, (struct sockaddr*)&server_addr, sizeof(server_addr))){
        perror("bind");
        return 0;
    }

    if(listen(sockfd, 5) == -1){
        perror("listen");
        return 0;
    }

    addr_len = sizeof(struct sockaddr_in);
    FD_ZERO(&readfds);
    FD_SET(sockfd,&readfds); // 리스닝 소켓을 목록에 추가
    max_fd = sockfd;

    while(1){
        readtemp = readfds;  // FD_SET이 귀찮으므로 원본을 복사해서 쓴다
        result = select(max_fd + 1, &readtemp, NULL, NULL, NULL);
        if(result == -1){
            perror("select");
            return 0;
        }

        for (int i=0; i<max_fd+1; i++){ 
            if(FD_ISSET(i, &readtemp)){   // 1. 모든 descriptor검사 
                if(i==sockfd){              // 2. listening 소켓인지 검사
                    // listening용 소켓
                    newfd = accept(i, (struct sockaddr *)&client_addr, &addr_len);
                    if(newfd == -1){
                        perror("accept");
                        exit(0);
                    }
                    printf("Connected client: %s %d\n", inet_ntoa(client_addr.sin_addr), newfd);
                    FD_SET(newfd, &readfds); // 3. connection소켓을 감지할 FD목록에 추가
                    if(max_fd < newfd)      // 4. 최대 descriptor갱신
                            max_fd = newfd;
                }else{
                    // read용 소켓
                    len = recv(i, buf, 1000, 0);
                    if(len > 0){
                        buf[len] = 0;
                        printf("recv: %s\n", buf);
                        send(i, buf, len,0);
                    }else if(len == 0){        // 5. 접속 종료시 소켓을 목록에서 제거
                        FD_CLR(i, &readfds);
                        close(i);
                        printf("Disconnected\n");
                    }else if(len == -1){
                        perror("recv");
                        return 0;
                    }
                }
            }
        }
    }
    close(sockfd);
    return 0;
}