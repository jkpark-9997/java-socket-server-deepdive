package org.jaekwang.server.service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CouponService {
    public static int remainingCoupons = 100;
    public static int issuedCoupons = 0;
    public static int failedCount = 0;
    public static int soldOutCount = 0;

    public static final Object failedCounterLock = new Object();
    public static final Object soldOutCounterLock = new Object();

    public static final Lock lock = new ReentrantLock();

    public static String issue() {
        try {
            if (lock.tryLock(500, TimeUnit.MILLISECONDS)) {
                try {
                    int currentRemaining = remainingCoupons;
                    if (currentRemaining > 0) {
                        try {
                            Thread.sleep(8);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        remainingCoupons = currentRemaining - 1;
                        issuedCoupons++;
                        return "Success";
                    }
                    synchronized (soldOutCounterLock) {
                        soldOutCount++;
                        return "Sold Out";
                    }

                } finally {
                    lock.unlock();
                }
            } else {
                synchronized (failedCounterLock) {
                    failedCount++;
                    return "Failed";
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
