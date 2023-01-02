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
    private final int port;
    private HttpServer server;
    private final DummyHandler testHandler;

    public DummyServer(int port) {
        this.port = port;
        this.testHandler = new DummyHandler(){{
            setEnabled(true);
            setSuccessResponse(new String[] {
                    "{\"id\":\"1\", \"title\":\"title1\", \"description\":\"description1\"}",
                    "{\"id\":\"2\", \"title\":\"title2\", \"description\":\"description2\"}"
            });
            setFailedResponse("\"message\" : \"internal server error\"");
        }};
    }

    public void setEnable(boolean enabled){
        this.testHandler.setEnabled(enabled);
    }

    public void start() throws IOException {
        server =  HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext(TndService.GENERATE_TND_COPY_URI, testHandler);
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
