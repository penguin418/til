package com.github.penguin418.practice01_publish_subscribe;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class LaunchWithConfig {
    public static final String SUBSCRIBER_ADDRESS = "subscriber-address";
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(PublisherVerticle.class.getName());
        vertx.deployVerticle(SubscriberVerticle.class.getName(), new DeploymentOptions().setInstances(2));
    }
}
