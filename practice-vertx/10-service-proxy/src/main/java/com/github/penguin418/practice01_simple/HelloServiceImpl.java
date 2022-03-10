package com.github.penguin418.practice01_simple;

import io.vertx.core.*;

public class HelloServiceImpl implements HelloService {
    public HelloServiceImpl(Vertx vertx) {
    }

    @Override
    public void hello1(String username, Handler<AsyncResult<String>> resultHandler) {
        final String helloMsg = username + " hello";
        Promise<String> asyncHello =Promise.promise();
        asyncHello.complete(helloMsg);
        resultHandler.handle(asyncHello.future());
    }
}
