package com.github.penguin418.practice01_basis;

import com.github.penguin418.model.Todo;
import com.github.penguin418.practice01_basis.rest.TodoApis;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class BasicWebClient extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        // 웹 클라이언트는 내부에 커넥션 풀을 가지고 있다.
        // 이 장점을 살리려면 한번만 선언해서 나눠쓰는 게 좋다
        final WebClientOptions options = new WebClientOptions()
                .setUserAgent("vertx-webclient/4.2.1").setSsl(true);
        final WebClient webClient = WebClient.create(vertx,options);

        TodoApis todoApis = new TodoApis(webClient);
        todoApis.getTodos().onComplete(todoFuture->{
            Todo[] todos = todoFuture.result();
            log.info(Arrays.toString(todos));
        });
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new BasicWebClient());
    }
}
