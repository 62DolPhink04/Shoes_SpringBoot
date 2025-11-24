//package com.VanLang.application.controller.shop;
//
//import com.VanLang.application.entity.Rate;
//import entity.com.VanLang.application.User;
//import request.model.com.VanLang.application.CreateRateProductRequest;
//import security.com.VanLang.application.CustomUserDetails;
//import com.VanLang.application.service.RateService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//@Controller
//public class RateController {
//
//    @Autowired
//    private RateService rateService;
//
//    @PostMapping("/api/rates/product")
//    public ResponseEntity<Object> createRate(@RequestBody CreateRateProductRequest createRateProductRequest){
//        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//        Rate rate = rateService.createRate(createRateProductRequest,user.getId());
//        return ResponseEntity.ok(rate);
//    }
//
//}
