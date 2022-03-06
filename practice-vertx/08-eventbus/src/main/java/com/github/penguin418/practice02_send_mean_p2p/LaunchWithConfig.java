package com.github.penguin418.practice02_send_mean_p2p;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class LaunchWithConfig {
    public static final String RECEIVER_ADDRESS = "receiver-address";
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(SenderVerticle.class.getName());
        vertx.deployVerticle(ReceiverVerticle.class.getName(), new DeploymentOptions().setInstances(2));
    }
}
