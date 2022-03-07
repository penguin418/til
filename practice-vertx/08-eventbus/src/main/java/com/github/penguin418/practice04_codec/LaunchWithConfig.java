package com.github.penguin418.practice04_codec;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class LaunchWithConfig {
    public static final String SUBSCRIBER_ADDRESS1 = "subscriber-address1";
    public static final String SUBSCRIBER_ADDRESS2 = "subscriber-address2";

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(PublisherVerticle.class.getName());
        vertx.deployVerticle(SubscriberVerticle.class.getName(), new DeploymentOptions().setInstances(2));
    }
}
