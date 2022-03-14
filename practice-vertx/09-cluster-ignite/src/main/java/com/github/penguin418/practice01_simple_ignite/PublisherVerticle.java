package com.github.penguin418.practice01_simple_zookeeper;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.ignite.IgniteClusterManager;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

import static com.github.penguin418.practice01_simple_zookeeper.Config.SUBSCRIBER_ADDRESS;


@Slf4j
public class PublisherVerticle extends AbstractVerticle {
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
            eventBus.publish(SUBSCRIBER_ADDRESS, data, dOptions);
        });
        startPromise.complete();
    }

    public static void main(String[] args) {

        ClusterManager mgr = new IgniteClusterManager();
        VertxOptions options = new VertxOptions().setClusterManager(mgr);
        Vertx.clusteredVertx(options).onSuccess(vertx->{
            vertx.deployVerticle(PublisherVerticle.class.getName());
        });
    }
}
