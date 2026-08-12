package org.jaekwang.server.service;

public class CouponService {
    public static int remainingCoupons = 100;
    public static int issuedCoupons = 0;

    public static String issue() {
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
        return "Sold Out";
    }
}
