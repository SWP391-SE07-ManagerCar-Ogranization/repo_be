package com.example.controller;

import com.example.dto.ReqRes;
import com.example.service.account.UsersManagementService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/public")
public class SmsController {
    Map<String,String> otpMap = new HashMap<>();
    @Autowired
    private UsersManagementService usersManagementService;
    @PostMapping("/sendSms/{toMobileNo}")
    public ResponseEntity sendSMS(@PathVariable("toMobileNo") String toMobile) {
         Twilio.init("AC09fb019a8b07fcdcbaa80d2e9e3991b6","c3ab5390dacaec0b1abdd04c7d0121d7");
        Message.creator(new PhoneNumber(toMobile), new PhoneNumber("+17604529857"), "Your verification PIN is: " + generateOtp(toMobile)).create();
        return new ResponseEntity("Message sent successfully", HttpStatus.OK);
    }

    private String generateOtp(String mobileNo) {
        Random random = new Random();
        String otp = String.valueOf((10000 + random.nextInt(90000)));
        otpMap.put(mobileNo,otp);
        return otp;
    }
    @PostMapping("/validateOtp")
    public ResponseEntity<ReqRes> validateOtp(@RequestBody ReqRes reqRes) {
        boolean flag = false;
        if(otpMap.containsKey(reqRes.getPhone())) {
            if(reqRes.getOtp().equals(otpMap.get(reqRes.getPhone()))) {
                flag = true;
            }
        }
        return ResponseEntity.ok(usersManagementService.forgotPassword(reqRes.getPhone(),flag));
    }
}
