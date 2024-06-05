package com.example.controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/public")
public class SmsController {
    Map<String,String> otpMap = new HashMap<>();
    @GetMapping("/sendSms/{toMobileNo}")
    public ResponseEntity sendSMS(@PathVariable("toMobileNo") String toMobile) {
        Twilio.init("AC09fb019a8b07fcdcbaa80d2e9e3991b6","4788165ff74d451f99e78753defa7e60");
        Message.creator(new PhoneNumber(toMobile), new PhoneNumber("+17604529857"),generateOtp(toMobile)).create();
        return new ResponseEntity("Message sent successfully", HttpStatus.OK);
    }

    private String generateOtp(String mobileNo) {
        Random random = new Random();
        String otp =  "Your verification PIN is: " + (10000 + random.nextInt(90000));
        otpMap.put(mobileNo,otp);
        return otp;
    }
    @GetMapping("/validateOtp/{mNo}/{otp}")
    public ResponseEntity validateOtp(@PathVariable("mNo") String mNo, @PathVariable("otp") String otp) {
        if(otpMap.containsKey(mNo)) {
            if(otp.equals(otpMap.get(mNo))) {
                return new ResponseEntity("otp validation successfully",HttpStatus.OK);
            }
        }
        return new ResponseEntity("invalid OTP", HttpStatus.BAD_REQUEST);
    }
}
