# Quartz 라이브러리 연습

## Hello Job
가장 간단한 쿼츠 스케줄러

HelloService를 사용해서 John과 Jake에게 1초마다 인사합니다

## Synchronized Job
동기화가 추가된 스케줄러

한번만 수행해도 되는 Job에 동기화를 추가합니다

- DisallowConcurrentExecutionTest

  DisallowConcurrentExecution 어노테이션을 사용하는 방법입니다
  ``` java
  @DisallowConcurrentExecution
  public class SyncJob1 implements Job {
    ...
  ```
- SynchronizedExecutionTest
  
  Execution 내부에 synchronized 구문을 사용하는 방법입니다
  ``` java
    ...
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        synchronized (couponService) {
            logger.info("execute");
            couponService.issueCoupon();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    ...
  ```

## Synchronized Multiple Job
동기화 스케줄러
CouponService는 쿠폰을 발급합니다. SynchronizedJob을 사용하여 매분, 매초 쿠폰을 발급합니다

* Static 객체를 가진 StaticServiceJob에서 SyncMethod를 가진 인스턴스를 호출합니다
  
  동일한 Static 객체를 호출하므로 SyncMethod가 동기화됩니다
  
  ``` java
  public class StaticServiceJob implements Job {
    private static Logger logger = LoggerFactory.getLogger(StaticServiceJob.class);
    private static CouponService couponService;
    ...
  ```

