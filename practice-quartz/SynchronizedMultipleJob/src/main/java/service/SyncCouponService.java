package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncCouponService implements CouponService{
    private static Logger logger = LoggerFactory.getLogger(SyncCouponService.class);
    private static int totalCoupon;

    public synchronized void issueCoupon(){
        logger.info("total coupon = {}", totalCoupon);
        System.out.println("issuing coupon ...");
        totalCoupon += 1;
    }
}
