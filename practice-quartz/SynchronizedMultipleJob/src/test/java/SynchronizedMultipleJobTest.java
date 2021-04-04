import job.StaticServiceJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CouponService;
import service.SyncCouponService;

public class SynchronizedMultipleJobTest {
    private static Logger logger = LoggerFactory.getLogger(SynchronizedMultipleJobTest.class);
    private static CouponService couponService;

    public static void main(String[] args) {
        System.out.println("StaticServiceJob과 SyncCouponService으로 동기화를 구현한다");
        couponService = new SyncCouponService();

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            JobDetail secondlyCoupon = JobBuilder
                    .newJob(StaticServiceJob.class)
                    .withIdentity("couponJob1", "group001")
                    .build();

            Trigger secondlyCouponTrigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("trigger001", "group001")
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever())
                    .build();

            JobDetail minutelyCoupon = JobBuilder
                    .newJob(StaticServiceJob.class)
                    .withIdentity("couponJob2", "group001")
                    .build();

            Trigger minutelyCouponTrigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("trigger002", "group001")
                    .withSchedule(SimpleScheduleBuilder.repeatMinutelyForever())
                    .build();

            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.getContext().put("couponService", couponService); // couponService 전달
            scheduler.start();
            scheduler.scheduleJob(secondlyCoupon, secondlyCouponTrigger);
            scheduler.scheduleJob(minutelyCoupon, minutelyCouponTrigger);
        } catch (SchedulerException e) {
            logger.info("종료");
            e.printStackTrace();
        }
    }
}
