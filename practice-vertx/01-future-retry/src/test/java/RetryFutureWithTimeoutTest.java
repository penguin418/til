import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ExtendWith(VertxExtension.class)
class RetryFutureWithTimeoutTest {
    private final Logger log = LoggerFactory.getLogger(RetryFutureWithTimeoutTest.class.getName());

    public static class TestVerticle extends AbstractVerticle {
        private final Logger log = LoggerFactory.getLogger(TestVerticle.class.getName());

        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            RetryFutureWithTimeout<String> retryFutureWithTimeout = new RetryFutureWithTimeout<>(vertx);
            Future<String> retryFuture = retryFutureWithTimeout
                    .withTimeout(3000)
                    .retry(() -> {
                        log.info("do task");
                        Promise<String> promise = Promise.promise();
                        vertx.setTimer(5000, timerId -> promise.fail("intended fail"));
                        return promise.future();
                    }, 5);

            retryFuture.onComplete(result -> {
                assert result.failed();
                assert result.cause().getMessage().equals("timeout");
            });

            super.start(startPromise);
        }
    }

    @Test
    void retry(Vertx vertx, VertxTestContext vertxTestContext) {
        vertx.deployVerticle(new TestVerticle()).onComplete(r -> {
            log.info("deployment was succeed ? : " + r.succeeded());
            vertx.setTimer(10_000, timerId -> {
                vertx.undeploy(r.result());
                vertxTestContext.completeNow();
            });
        });
    }
}