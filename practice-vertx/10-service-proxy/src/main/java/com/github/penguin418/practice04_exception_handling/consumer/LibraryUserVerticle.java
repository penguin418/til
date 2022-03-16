package com.github.penguin418.practice04_exception_handling.consumer;

import com.github.penguin418.practice02_proxy_hi.common.HiService;
import com.github.penguin418.practice04_exception_handling.common.LibraryService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ServiceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LibraryUserVerticle extends AbstractVerticle {

    private final String HI_SERVICE_ADDRESS = "services.hi";

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        log.info("create proxy");
        // 사용하는 쪽의 프록시 생성
        LibraryService libraryService = LibraryService.createProxy(vertx, HI_SERVICE_ADDRESS);
        log.info("elice hi to HelloService");
        borrowLittlePrince(libraryService);
        borrowLittlePrince(libraryService);

        startPromise.complete();
    }

    public void borrowLittlePrince(LibraryService libraryService){
        libraryService.checkOut("The Little Princess", msg->{
            if (msg.failed()){
                ServiceException se = (ServiceException) msg.cause();
                switch (se.failureCode()){
                    case 1: // NO_BOOKS_LEFT
                        log.info("NO_BOOKS_LEFT");
                        break;
                }
            }else {
                log.info(msg.result());
            }
        });
    }

    public static void main(String[] args) {
        DeploymentOptions dOptions = new DeploymentOptions();
        Vertx.vertx().deployVerticle(LibraryUserVerticle.class.getName(),dOptions);
    }
}
