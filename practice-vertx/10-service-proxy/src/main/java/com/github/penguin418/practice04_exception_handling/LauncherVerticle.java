package com.github.penguin418.practice04_exception_handling;

import com.github.penguin418.practice04_exception_handling.consumer.LibraryUserVerticle;
import com.github.penguin418.practice04_exception_handling.provider.LibraryServiceVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LauncherVerticle extends AbstractVerticle {
    public static void main(String[] args) {
        DeploymentOptions dOptions = new DeploymentOptions();
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(LibraryServiceVerticle.class.getName(),dOptions);
        vertx.deployVerticle(LibraryUserVerticle.class.getName(),dOptions);
    }
}
