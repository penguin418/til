package com.github.penguin418.practice03_codec_in_handler;

import com.github.penguin418.model.Todo;
import com.github.penguin418.practice03_codec_in_handler.rest.TodoApis;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class CodecWebClient extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        // 웹 클라이언트는 내부에 커넥션 풀을 가지고 있다.
        // 이 장점을 살리려면 한번만 선언해서 나눠쓰는 게 좋다
        final WebClientOptions options = new WebClientOptions()
                .setUserAgent("vertx-webclient/4.2.1").setSsl(true);
        final WebClient webClient = WebClient.create(vertx,options);

        TodoApis todoApis = new TodoApis(webClient);
        todoApis.getTodos().onComplete(todosFuture->{
            Todo[] todos = todosFuture.result();
            log.info(Arrays.toString(todos));
        }).onFailure(f->{
            log.error("getTodos error", f);
        });


        todoApis.getTodo(1).onComplete(todoFuture->{
            Todo todo = todoFuture.result();
            log.info(todo.toString());
        }).onFailure(f->{
            log.error("getTodo error", f);
        });
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new CodecWebClient());
    }
}
