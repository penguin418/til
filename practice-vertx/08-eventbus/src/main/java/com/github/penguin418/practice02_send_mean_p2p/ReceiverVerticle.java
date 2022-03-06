package com.github.penguin418.practice02_send_mean_p2p;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

import static com.github.penguin418.practice02_send_mean_p2p.LaunchWithConfig.RECEIVER_ADDRESS;

@Slf4j
public class ReceiverVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        EventBus eventBus = vertx.eventBus();
        eventBus.<JsonObject>consumer(RECEIVER_ADDRESS, event -> {
           JsonObject data = event.body();
            log.info("receive message: " + data + " with header: " + event.headers().entries().toString());
        });
        startPromise.complete();
    }
}
