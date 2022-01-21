package com.github.penguin418;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class HelloVerticle extends AbstractVerticle {

    Logger log = LoggerFactory.getLogger(HelloVerticle.class);
    @Override
    public void start(Promise<Void> startPromise) {
        
        final String deploymentID = vertx.getOrCreateContext().deploymentID();
        log.info(String.format("Hello from %s (hashcode=%s, deploymentID=%s)",
                HelloVerticle.class.getSimpleName(),
                this.hashCode(),
                deploymentID));
        startPromise.complete();
    }
}
