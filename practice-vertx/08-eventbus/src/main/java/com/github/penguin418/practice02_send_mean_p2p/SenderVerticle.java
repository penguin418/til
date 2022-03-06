package com.github.penguin418.practice02_send_mean_p2p;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

import static com.github.penguin418.practice02_send_mean_p2p.LaunchWithConfig.RECEIVER_ADDRESS;

@Slf4j
public class SenderVerticle extends AbstractVerticle {
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
            eventBus.send(RECEIVER_ADDRESS, data, dOptions);
        });

        // 10초 후 종료
        vertx.setTimer(10_000, timerId->{
            vertx.close();
        });
        startPromise.complete();
    }
}
