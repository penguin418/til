package com.github.penguin418.practice01_simple;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleHelloVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        HelloService helloService = HelloService.create(vertx);
        log.info("bob hi to HelloService");
        helloService.hello1("bob", msg->{
            log.info("HelloService responded as " + msg.result());
        });
        startPromise.complete();
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new SimpleHelloVerticle());
    }
}
