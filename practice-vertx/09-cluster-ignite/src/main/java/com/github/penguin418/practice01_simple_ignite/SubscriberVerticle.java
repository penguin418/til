package com.github.penguin418.practice01_simple_zookeeper;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.ignite.IgniteClusterManager;
import lombok.extern.slf4j.Slf4j;

import static com.github.penguin418.practice01_simple_zookeeper.Config.SUBSCRIBER_ADDRESS;

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
    public static void main(String[] args) {

        ClusterManager mgr = new IgniteClusterManager();
        VertxOptions options = new VertxOptions().setClusterManager(mgr);
        Vertx.clusteredVertx(options).onSuccess(vertx->{
            vertx.deployVerticle(SubscriberVerticle.class.getName());
        });
    }
}
