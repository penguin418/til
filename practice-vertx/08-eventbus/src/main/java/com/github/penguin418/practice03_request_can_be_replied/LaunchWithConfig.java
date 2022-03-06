package com.github.penguin418.practice03_request_can_be_replied;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class LaunchWithConfig {
    public static final String REQUEST_ADDRESS = "request-address";
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(RequesterVerticle.class.getName());
        vertx.deployVerticle(RequestHandlerVerticle.class.getName(), new DeploymentOptions().setInstances(2));
    }
}
