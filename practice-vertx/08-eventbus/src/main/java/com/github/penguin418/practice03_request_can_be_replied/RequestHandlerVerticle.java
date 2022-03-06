package com.github.penguin418.practice03_request_can_be_replied;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

import static com.github.penguin418.practice03_request_can_be_replied.LaunchWithConfig.REQUEST_ADDRESS;

@Slf4j
public class RequestHandlerVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        EventBus eventBus = vertx.eventBus();
        AtomicInteger messageId = new AtomicInteger(0);
        eventBus.<JsonObject>consumer(REQUEST_ADDRESS, event -> {
           JsonObject data = event.body();
            log.info("receive message: " + data + " with header: " + event.headers().entries().toString());

            // reply가 가능하기 때문에 아이디가 생성된다. 이외에는 아이디 null
            log.info("reply address: " + event.replyAddress());

            JsonObject replyData = new JsonObject()
                    .put("original", data)
                    .put("received", messageId.getAndIncrement());
            event.reply(replyData);
        });
        startPromise.complete();
    }
}
