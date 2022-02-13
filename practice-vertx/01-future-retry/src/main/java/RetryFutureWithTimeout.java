import io.vertx.core.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class RetryFutureWithTimeout<T> extends RetryFuture<T> {
    private final Vertx vertx;
    private int timeout = 30;
    AtomicBoolean isTimedOut = new AtomicBoolean();

    public RetryFutureWithTimeout(Vertx vertx) {
        this.vertx = vertx;
    }

    public RetryFutureWithTimeout<T> withTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    @Override
    protected Future<T> retryRunner(Promise<T> promise, Supplier<? extends Future<T>> supplier, int retryLeft) {
        log.info("retryRunner: retryLeft=" + retryLeft);
        if (retryLeft > 0) {
            Future<T> retryFuture = supplier.get();
            retryFuture.onComplete(retryResult -> {
                if (retryResult.succeeded()) promise.complete();
                else {
                    if (isTimedOut.get()) promise.tryFail("timeout");
                    else retryRunner(promise, supplier, retryLeft - 1);
                }
            });
        } else {
            promise.tryFail("exceed retry limit");
        }
        return promise.future();
    }

    protected Future<T> retryRunner(Promise<T> promise, Function<Vertx, ? extends Future<T>> func, int retryLeft) {
        log.info("retryRunner: retryLeft=" + retryLeft);
        if (retryLeft > 0) {
            Future<T> retryFuture = func.apply(vertx);
            retryFuture.onComplete(retryResult -> {
                if (retryResult.succeeded()) promise.complete();
                else {
                    if (isTimedOut.get()) promise.tryFail("timeout");
                    else retryRunner(promise, func, retryLeft - 1);
                }
            });
        } else {
            promise.tryFail("exceed retry limit");
        }
        return promise.future();
    }

    public Future<T> retry(Function<Vertx, ? extends Future<T>> retryLambda, int retryLeft) {
        Promise<T> promise = Promise.promise();

        Promise<T> retryPromise = Promise.promise();

        TimeoutStream timeoutStream = vertx.timerStream(Math.max(1, timeout));
        timeoutStream.handler(timerId-> isTimedOut.set(true));
        retryRunner(retryPromise, retryLambda, retryLeft);

        retryPromise.future().onSuccess(cf -> {
            log.info("timer or task has done!");
            if (isTimedOut.get()) {
                // time's up.
                log.info("timer done first");
                promise.tryFail("timeout");
            } else {
                log.info("task done first");
                retryPromise.future().onSuccess(promise::complete).onFailure(promise::fail);
            }
        }).onFailure(f -> {
            log.info("composite future failed");
        }).onComplete(c -> {
            log.info("composite future completed");
        });

        return promise.future();
    }

    @Override
    public Future<T> retry(Supplier<? extends Future<T>> retryLambda, int retryLeft) {
        Promise<T> promise = Promise.promise();

        Promise<T> retryPromise = Promise.promise();

        TimeoutStream timeoutStream = vertx.timerStream(Math.max(1, timeout));
        timeoutStream.handler(timerId-> isTimedOut.set(true));
        retryRunner(retryPromise, retryLambda, retryLeft);

        retryPromise.future().onSuccess(cf -> {
            log.info("timer or task has done!");
            if (isTimedOut.get()) {
                // time's up.
                log.info("timer done first");
                promise.tryFail("timeout");
            } else {
                log.info("task done first");
                retryPromise.future().onSuccess(promise::complete).onFailure(promise::fail);
            }
        }).onFailure(f -> {
            log.info("composite future failed");
        }).onComplete(c -> {
            log.info("composite future completed");
        });

        return promise.future();
    }
}
