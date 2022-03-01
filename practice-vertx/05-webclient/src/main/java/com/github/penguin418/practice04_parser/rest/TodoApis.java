package com.github.penguin418.practice04_parser.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.penguin418.exception.JsonPlaceHolderError;
import com.github.penguin418.model.Todo;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.predicate.ErrorConverter;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import io.vertx.ext.web.codec.BodyCodec;
import lombok.extern.slf4j.Slf4j;

import static io.vertx.ext.web.client.predicate.ResponsePredicate.status;

@Slf4j
public class TodoApis {
    private static final Integer PORT = 443;
    private static final String HOSTNAME = "jsonplaceholder.typicode.com";
    private final WebClient webClient;
    private final ObjectMapper mapper;
    private ErrorConverter notExpectedStatusCodeHandler;
    private BodyCodec<Todo> todoCodec;

    public TodoApis(WebClient webClient) {
        this.webClient = webClient;
        this.mapper = new ObjectMapper();
        setNotExpectedStatusCodeHandler();
        setTodoCodec();
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
        webClient.get(PORT, HOSTNAME, "/todos/10000") // which is not exist
                .ssl(true)
                // json 타입이 이면 통과, 아니면 에러
                .expect(ResponsePredicate.create(ResponsePredicate.JSON, (r->JsonPlaceHolderError.OBJECT_NOT_SUPPORTED_TYPE.exception())))
                // 200 이면 통과, 아니면 커스텀 핸들러로 분기
                .expect(ResponsePredicate.create(status(200), notExpectedStatusCodeHandler))
                .as(BodyCodec.json(Todo[].class))
                .send()
                .onSuccess(todosResponse -> {
                    Todo[] todos = todosResponse.body();
                    promise.complete(todos);
                }).onFailure(err -> {
                    log.error("webClient fail", err);
                    promise.fail(JsonPlaceHolderError.INVALID_URL.exception());
                });
        return promise.future();
    }

    public void setTodoCodec(){
        // 응답 결과를 변환하는 코덱
        // 만약 크롤링을 한다면 String 으로 받아서 해체해야 하는 경우도 있다.
        todoCodec = BodyCodec.create(response->{
            JsonObject jsonObject = response.toJsonObject();
            Todo newTodo = new Todo();
            newTodo.setId(jsonObject.getInteger("id"));
            newTodo.setUserId(jsonObject.getInteger("userId"));
            newTodo.setTitle(jsonObject.getString("title"));
            newTodo.setCompleted(jsonObject.getBoolean("completed"));
            return newTodo;
        });
    }

    public Future<Todo> getTodo(int i) {
        Promise<Todo> promise = Promise.promise();
        // https://jsonplaceholder.typicode.com/todos
        webClient.get(PORT, HOSTNAME, "/todos/"+i) // which is not exist
                .ssl(true)
                // json 타입이 이면 통과, 아니면 에러
                .expect(ResponsePredicate.create(ResponsePredicate.JSON, (r->JsonPlaceHolderError.OBJECT_NOT_SUPPORTED_TYPE.exception())))
                // 200 이면 통과, 아니면 커스텀 핸들러로 분기
                .expect(ResponsePredicate.create(status(200), notExpectedStatusCodeHandler))
                .as(todoCodec)
                .send()
                .onSuccess(todoResponse -> {
                    Todo todo = todoResponse.body();
                    promise.complete(todo);
                }).onFailure(err -> {
                    log.error("webClient fail", err);
                    promise.fail(JsonPlaceHolderError.INVALID_URL.exception());
                });
        return promise.future();
    }
}
