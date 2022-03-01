package com.github.penguin418.practice02_expect_in_handler.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.penguin418.exception.JsonPlaceHolderError;
import com.github.penguin418.model.Todo;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.predicate.ErrorConverter;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static io.vertx.ext.web.client.predicate.ResponsePredicate.status;

@Slf4j
public class TodoApis {
    private static final Integer PORT = 443;
    private static final String HOSTNAME = "jsonplaceholder.typicode.com";
    private final WebClient webClient;
    private final ObjectMapper mapper;
    private ErrorConverter notExpectedStatusCodeHandler;

    public TodoApis(WebClient webClient) {
        this.webClient = webClient;
        this.mapper = new ObjectMapper();
        setNotExpectedStatusCodeHandler();
    }

    private void setNotExpectedStatusCodeHandler(){
        this.notExpectedStatusCodeHandler = ErrorConverter.createFullBody(result->{
            HttpResponse<Buffer> response = result.response();
            if (response.getHeader("content-type").equals("application/json")){
                log.info("" + response.bodyAsJsonObject().encode());
            }
            return JsonPlaceHolderError.OBJECT_NOT_FOUND.exception();
        });
    }

    public Future<Todo[]> getTodos() {
        Promise<Todo[]> promise = Promise.promise();
        // https://jsonplaceholder.typicode.com/todos
        webClient.get(PORT, HOSTNAME, "/todos") // which is not exist
                .ssl(true)
                // json 타입이 이면 통과, 아니면 에러
                .expect(ResponsePredicate.create(ResponsePredicate.JSON, (r->JsonPlaceHolderError.OBJECT_NOT_SUPPORTED_TYPE.exception())))
                // 200 이면 통과, 아니면 커스텀 핸들러로 분기
                .expect(ResponsePredicate.create(status(200), notExpectedStatusCodeHandler))
                .send()
                .onSuccess(response -> {
                        try {
                            JsonArray bodys = response.bodyAsJsonArray();
                            Todo[] todos = mapper.readValue(bodys.encode(), Todo[].class);
                            promise.complete(todos);
                        } catch (IOException e) {
                            log.error("", e);
                            promise.fail(JsonPlaceHolderError.BAD_PARSING.exception());
                        }
                }).onFailure(err -> {
                    log.error("webClient fail", err);
                    promise.fail(JsonPlaceHolderError.INVALID_URL.exception());
                });
        return promise.future();
    }
}
