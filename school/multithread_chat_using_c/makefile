all: chat_client chat_server

chat_server: chat_server.c
	gcc -o chat_server chat_server.c -pthread
chat_client: chat_client.c
	gcc -o chat_client chat_client.c -pthread
