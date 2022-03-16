package com.github.penguin418.practice04_exception_handling.provider;

import com.github.penguin418.practice04_exception_handling.common.LibraryService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ServiceException;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final Vertx vertx;

    public static final Integer NO_BOOKS_LEFT = 1;

    private Map<String, Integer> books = new HashMap<String ,Integer>(){{
        put("The Little Princess", 1);
    }};

    public LibraryServiceImpl(Vertx vertx){this.vertx = vertx;}

    @Override
    public void checkOut(String book, Handler<AsyncResult<String>> resultHandler) {
        Promise<String> promise = Promise.promise();
        Integer left = books.get(book);

        if (left > 0){
            books.put(book, left-1);
            promise.complete("success");
        }else{
            resultHandler.handle(ServiceException.fail(NO_BOOKS_LEFT, book));
        }
        resultHandler.handle(promise.future());
    }

    @Override
    public void checkIn(String book, Handler<AsyncResult<String>> resultHandler) {
        Promise<String> promise = Promise.promise();
        Integer left = books.get(book);
        if (left == null)
            left = 0;
        books.put(book, left+1);
        promise.complete("success");
        resultHandler.handle(promise.future());
    }
}
