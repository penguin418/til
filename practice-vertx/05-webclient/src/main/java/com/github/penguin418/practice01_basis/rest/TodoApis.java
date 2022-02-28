package com.github.penguin418.practice01_basis.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.penguin418.exception.JsonPlaceHolderError;
import com.github.penguin418.model.Todo;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.client.WebClient;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TodoApis {
    private static final Integer PORT = 443;
    private static final String HOSTNAME = "jsonplaceholder.typicode.com";
    private final WebClient webClient;
    private final ObjectMapper mapper;

    public TodoApis(WebClient webClient) {
        this.webClient = webClient;
        this.mapper = new ObjectMapper();
    }

    public Future<Todo[]> getTodos() {
        Promise<Todo[]> promise = Promise.promise();
        // https://jsonplaceholder.typicode.com/todos
        webClient.get(PORT, HOSTNAME, "/todos") // not exist
                .ssl(true)
                .send()
                .onSuccess(response -> {
                    if (response.statusCode() == 200) {
                        if (! response.getHeader("content-type").equals("application/json"))
                            promise.fail(JsonPlaceHolderError.OBJECT_NOT_SUPPORTED_TYPE.exception());
                        try {
                            JsonArray bodys = response.bodyAsJsonArray();
                            Todo[] todos = mapper.readValue(bodys.encode(), Todo[].class);
                            promise.complete(todos);
                        } catch (IOException e) {
                            log.error("", e);
                            promise.fail(JsonPlaceHolderError.BAD_PARSING.exception());
                        }
                    } else if (response.statusCode() == 429){
                        log.error("status code is not 200");
                        promise.fail(JsonPlaceHolderError.SERVER_BUSY.exception());
                    }else {
                        log.error("status code was not handled {}", response.statusCode());
                        promise.fail(JsonPlaceHolderError.UNKNOWN.exception());
                    }
                }).onFailure(err -> {
                    promise.fail(JsonPlaceHolderError.INVALID_URL.exception());
                });
        return promise.future();
    }
}
