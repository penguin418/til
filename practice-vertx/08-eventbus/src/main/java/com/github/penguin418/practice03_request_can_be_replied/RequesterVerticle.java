package com.github.penguin418.practice03_request_can_be_replied;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

import static com.github.penguin418.practice03_request_can_be_replied.LaunchWithConfig.REQUEST_ADDRESS;

@Slf4j
public class RequesterVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        EventBus eventBus = vertx.eventBus();
        // 1초마다 이벤트 발신
        AtomicInteger messageId = new AtomicInteger(0);
        vertx.setPeriodic(1000, timerId -> {
            JsonObject data = new JsonObject()
                    .put("timerId", messageId.incrementAndGet());
            DeliveryOptions dOptions = new DeliveryOptions()
                    .addHeader("someHeader", "someValue");

            log.info("publish message. " + data);
            Future<Message<JsonObject>> request = eventBus.request(REQUEST_ADDRESS, data, dOptions);
            request.onSuccess(event -> {
               JsonObject repliedData = event.body();
               log.info("repliedData: " + repliedData);
            });
        });

        // 10초 후 종료
        vertx.setTimer(10_000, timerId->{
            vertx.close();
        });
        startPromise.complete();
    }
}
