package com.github.penguin418;

import com.github.penguin418.config.RestClient;
import com.github.penguin418.model.Tnd;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TndServiceTest {
    DummyServer dummyServer;

    @BeforeEach
    void beforeEach() throws IOException {
        dummyServer = new DummyServer(9000);
        dummyServer.start();
    }

    @AfterEach
    void afterEach(){
        dummyServer.stop();
    }

    @Test
    void generateTndCopy() {
        RestClient.getInstance().setBaseUrl("http://localhost:9000");

        TndService tndService = new TndService();
        Assertions.assertNotNull(tndService.generateTndCopy("product", "target"));
        Assertions.assertDoesNotThrow(()->{tndService.generateTndCopy("product", "target");});
    }
}