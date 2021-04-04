package job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CouponService;

public class StaticServiceJob implements Job {
    private static Logger logger = LoggerFactory.getLogger(StaticServiceJob.class);
    private static CouponService couponService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("execute");
        couponService.issueCoupon();
    }

    public void setCouponService(CouponService _couponService) {
        if (couponService == null) couponService = _couponService;
    }
}
