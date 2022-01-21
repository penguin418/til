package com.github.penguin418;

import io.vertx.core.Vertx;

public class Starter {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(HelloVerticle.class.getName()).onComplete(result->{
            if (result.succeeded()){
                System.out.println("Deployment successfully completed !");
            }else{
                System.out.println("Deployment failed. cause = " + result.cause());
            }
        });
    }
}
