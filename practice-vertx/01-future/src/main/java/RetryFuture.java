import io.vertx.core.Future;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RetryFuture<T> {
    public static interface RetryLambda<T> {
        Future<T> task();
    }

    private Future<T> retryRunner(Promise<T> promise, RetryLambda<T> retryLambda, int retryLeft) {
        log.info("retryRunner: retryLeft=" + retryLeft);
        if (retryLeft > 0) {
            Future<T> future = retryLambda.task();
            if (future.succeeded()) promise.complete();
            else retryRunner(promise, retryLambda, retryLeft - 1);
        } else {
            promise.fail("exceed retry limit");
        }

        return promise.future();
    }

    public Future<T> retry(RetryLambda<T> retryLambda, int retryLeft){
        return retryRunner(Promise.promise(), retryLambda, retryLeft);
    }

    public static void main(String[] args) {
        RetryFuture<String> retryFuture = new RetryFuture<>();
        Future<String> retryResult = retryFuture.retry(new RetryLambda<String>() {
            private int myTry = 0;
            @Override
            public Future<String> task() {
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
