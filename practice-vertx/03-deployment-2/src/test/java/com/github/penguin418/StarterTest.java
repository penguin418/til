package com.github.penguin418;

import com.github.penguin418.practice01_start_with_starter_verticle.HelloVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
class StarterTest {

    private String VERTICLE_NAME = HelloVerticle.class.getName();

    @Test
    public void deploy_test(Vertx vertx, VertxTestContext testContext){
        vertx.deployVerticle(VERTICLE_NAME)
                .onComplete(result->{
                    Assertions.assertTrue(result.succeeded());
                    testContext.completeNow();
                });
    }

    @Test
    public void deploy_multi_test(Vertx vertx, VertxTestContext testContext){
        int NUMBER_OF_INSTANCES = 5;

        DeploymentOptions dOption = new DeploymentOptions()
                .setInstances(NUMBER_OF_INSTANCES);

        vertx.deployVerticle(VERTICLE_NAME, dOption)
                .onComplete(result->{
                    System.out.println(result);
                    testContext.completeNow();
                });
    }
}