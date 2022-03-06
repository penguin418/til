package com.github.penguin418.practice01_publish_subscribe;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

import static com.github.penguin418.practice01_publish_subscribe.LaunchWithConfig.SUBSCRIBER_ADDRESS;

@Slf4j
public class SubscriberVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        EventBus eventBus = vertx.eventBus();
        eventBus.<JsonObject>consumer(SUBSCRIBER_ADDRESS, event -> {
           JsonObject data = event.body();
            log.info("receive message: " + data + " with header: " + event.headers().entries().toString());
        });
        startPromise.complete();
    }
}
