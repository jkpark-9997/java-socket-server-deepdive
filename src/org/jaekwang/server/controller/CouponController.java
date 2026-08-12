package org.jaekwang.server.controller;

import org.jaekwang.server.service.CouponService;

import org.jaekwang.server.http.HttpRequest;
import org.jaekwang.server.http.HttpResponse;

import java.io.IOException;


public class CouponController implements Controller {
    @Override
    public void process(HttpRequest request, HttpResponse response) throws IOException {
        CouponService couponService = new CouponService();
        String message = couponService.issue();
        response.sendOk(message);

    }
}
