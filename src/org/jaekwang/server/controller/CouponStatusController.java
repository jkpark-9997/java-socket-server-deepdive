package org.jaekwang.server.controller;

import org.jaekwang.server.http.HttpRequest;
import org.jaekwang.server.http.HttpResponse;
import org.jaekwang.server.service.CouponService;

import java.io.IOException;

public class CouponStatusController implements Controller {
    @Override
    public void process(HttpRequest request, HttpResponse response) throws IOException {
        String message = String.format("remaining = %d, issued = %d", CouponService.remainingCoupons, CouponService.issuedCoupons);
        response.sendOk(message);
    }
}
