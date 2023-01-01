package com.github.penguin418;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Random;
import java.util.concurrent.Executors;

public class DummyServer {
    final static String API_URI = "/api";

    private final int port;
    private HttpServer server;
    private final DummyHandler testHandler;

    public DummyServer(int port) {
        this.port = port;
        this.testHandler = new DummyHandler(){{
            setEnabled(true);
            setSuccessResponse(new String[] {
                    "{\"id\":\"1\", \"name\":\"product1\", \"cost\":400, \"reg_dtm\":\"2023-01-01T15:00:00.000\"}",
                    "{\"id\":\"2\", \"name\":\"product2\", \"cost\":500, \"reg_dtm\":\"2023-01-01T15:01:00.000\"}"
            });
            setFailedResponse("\"message\" : \"internal server error\"");
        }};
    }

    public void setEnable(boolean enabled){
        this.testHandler.setEnabled(enabled);
    }

    public void start() throws IOException {
        server =  HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext(API_URI, testHandler);
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
    }

    public void stop(){
        try{
            server.stop(0);
        }catch (Throwable t){
            t.printStackTrace();
        }
    }
    static class DummyHandler implements HttpHandler {
        @Setter @Getter
        private boolean enabled = true;
        @Setter @Getter
        private String[] successResponse;
        @Setter @Getter
        private String failedResponse;

        @Override
        public void handle(HttpExchange t) throws IOException {
            if (this.isEnabled()){
                String[] responseCandidates = this.successResponse;
                String responseString = responseCandidates[new Random().nextInt(responseCandidates.length)];
                byte[] response = responseString.getBytes();
                t.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
                t.sendResponseHeaders(200, response.length);
                OutputStream os = t.getResponseBody();
                try {
                    os.write(response);
                }catch (IOException e){
                    e.printStackTrace();
                }
                os.close();
            }else{
                t.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
                t.sendResponseHeaders(500, this.failedResponse.getBytes().length);
                OutputStream os = t.getResponseBody();
                os.write(this.failedResponse.getBytes());
                os.close();
            }

        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        DummyServer dummyServer = new DummyServer(9000);
        dummyServer.start();
        Thread.sleep(15*1000);
        dummyServer.setEnable(false);
        Thread.sleep(15*1000);
        dummyServer.stop();
    }
}
