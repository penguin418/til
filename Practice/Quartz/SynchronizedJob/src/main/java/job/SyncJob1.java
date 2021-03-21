package job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.PlainCouponService;

// 방법1, 복구시 스케줄러 순서까지 보장됨
@DisallowConcurrentExecution
public class SyncJob1 implements Job {
    private static Logger logger = LoggerFactory.getLogger(SyncJob1.class);
    private static boolean lock = false;
    private PlainCouponService couponService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("execute");
        couponService.issueCoupon();
    }

    public void setCouponService(PlainCouponService couponService) {
        this.couponService = couponService;
    }
}
