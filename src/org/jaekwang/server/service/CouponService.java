package org.jaekwang.server.service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CouponService {
    public static AtomicInteger remainingCoupons = new AtomicInteger(100);
    public static AtomicInteger issuedCoupons = new AtomicInteger(0);

    public static String issue() {
        while (true) {
            int currentRemaining = remainingCoupons.get();
            if (currentRemaining > 0) {
                if (remainingCoupons.compareAndSet(currentRemaining, currentRemaining - 1)) {
                    try {
                        Thread.sleep(8);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    issuedCoupons.incrementAndGet();
                    return "Success";
                }
            } else {
                return "Sold Out";

            }
        }
    }
}
