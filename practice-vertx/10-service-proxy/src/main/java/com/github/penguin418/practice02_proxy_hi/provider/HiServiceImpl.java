package com.github.penguin418.practice02_proxy_hi.provider;

import com.github.penguin418.practice02_proxy_hi.common.HiMessage;
import com.github.penguin418.practice02_proxy_hi.common.HiService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HiServiceImpl implements HiService {
    private final String classIdentifier = Integer.toHexString(this.hashCode());
    private final Vertx vertx;

    @Override
    public void hi_1(String username, Handler<AsyncResult<String>> resultHandler) {
        final String msg = username + " hi there!";
        Promise<String> promise = Promise.promise();
        promise.complete(msg);
        resultHandler.handle(promise.future());
    }

    @Override
    public void hi_2(String username, Handler<AsyncResult<HiMessage>> resultHandler) {
        final String msg = username + " hi there! hi there!";
        Promise<HiMessage> promise = Promise.promise();
        promise.complete(new HiMessage(username, msg,classIdentifier));
        resultHandler.handle(promise.future());
    }
}
