package com.github.penguin418.practice01_simple;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

public interface HelloService {
    static HelloService create(Vertx vertx){
        return new HelloServiceImpl(vertx);
    }

    void hello1(String username, Handler<AsyncResult<String>> resultHandler);
}
