package job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CouponService;

// 방법1, 복구시 스케줄러 순서까지 보장됨
//@DisallowConcurrentExecution
public class SynchronizedJob implements Job {
    private static Logger logger = LoggerFactory.getLogger(SynchronizedJob.class);
    private static boolean lock = false;
    private CouponService couponService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        synchronized (this){
            lock = true;
            try {
                couponService.issueCoupon();
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                lock = false;
            }
        }
    }

    public void setCouponService(CouponService couponService) {
        this.couponService = couponService;
    }
}
