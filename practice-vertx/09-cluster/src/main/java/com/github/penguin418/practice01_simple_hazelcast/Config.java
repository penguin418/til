package com.github.penguin418.practice01_simple_hazelcast;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class Config {
    public static final String SUBSCRIBER_ADDRESS = "subscriber-address";
}
