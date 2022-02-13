import io.vertx.core.Future;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class RetryFuture<T> {

    protected Future<T> retryRunner(Promise<T> promise, Supplier<? extends Future<T>> supplier, int retryLeft) {
        log.info("retryRunner: retryLeft=" + retryLeft);
        if (retryLeft > 0) {
            Future<T> retryFuture = supplier.get();
            retryFuture.onComplete(retryResult->{
                if (retryResult.succeeded()) promise.complete();
                else retryRunner(promise, supplier, retryLeft - 1);
            });
        } else {
            promise.tryFail("exceed retry limit");
        }
        return promise.future();
    }

    public Future<T> retry(Supplier<? extends Future<T>> supplier, int retryLeft){
        return retryRunner(Promise.promise(), supplier, retryLeft);
    }

    public static void main(String[] args) {
        RetryFuture<String> retryFuture = new RetryFuture<>();
        Future<String> retryResult = retryFuture.retry(new Supplier<Future<String>>() {
            private int myTry = 0;
            @Override
            public Future<String> get() {
                Promise<String> promise = Promise.promise();
                myTry += 1;
                if (myTry < 3) {
                    promise.fail("fail");
                } else {
                    promise.complete("success");
                }
                return promise.future();
            }
        }, 5);

        assert retryResult.succeeded();
    }
}
