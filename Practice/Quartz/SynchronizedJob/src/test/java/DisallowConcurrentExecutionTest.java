import job.SyncJob1;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CouponService;
import service.PlainCouponService;

class DisallowConcurrentExecutionTest {
    private static Logger logger = LoggerFactory.getLogger(DisallowConcurrentExecutionTest.class);
    private static CouponService couponService;

    public static void main(String[] args){
        System.out.println("StaticServiceJob과 SyncCouponService의 동작을 확인한다");
        couponService = new PlainCouponService();

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try{
            JobDetail secondlyCoupon = JobBuilder
                    .newJob(SyncJob1.class)
                    .withIdentity("couponJob1", "group001")
                    .build();

            Trigger secondlyCouponTrigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("trigger001", "group001")
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever())
                    .build();

            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.getContext().put("couponService", couponService); // couponService 전달
            scheduler.start();
            scheduler.scheduleJob(secondlyCoupon, secondlyCouponTrigger);
        } catch (SchedulerException e) {
            logger.info("종료");
            e.printStackTrace();
        }
    }
}