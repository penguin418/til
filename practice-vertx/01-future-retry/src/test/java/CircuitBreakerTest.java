import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class CircuitBreakerTest {
    private final Logger log = LoggerFactory.getLogger(CircuitBreakerTest.class.getName());

    @Test
    public void CircuitBreakerTest(Vertx vertx, VertxTestContext testContext) {
        CircuitBreakerOptions cbOption = new CircuitBreakerOptions()
                .setMaxRetries(10)
                .setTimeout(1_000)
                .setFallbackOnFailure(true)
                .setResetTimeout(2_000);
        CircuitBreaker breaker = CircuitBreaker
                .create("new-breaker", vertx, cbOption)
                .openHandler(v -> {
                    log.info("circuit opened");
                }).closeHandler(v -> {
                    log.info("circuit closed");
                }).halfOpenHandler(v -> {
                    log.info("circuit half-opened");
                });


        breaker.execute(promise -> {
            vertx.setTimer(3_000, timerId -> {
                log.info("times up");
                try{
                    promise.complete("complete");
                }catch (Throwable t){
                    log.warn(t);
                }
            });
        }).onSuccess(r -> {
            log.info("success");
        }).onFailure(f -> {
            log.error("failed");
        }).onComplete(c -> {
            log.info("completed");
            testContext.completeNow();
        });
    }

}
