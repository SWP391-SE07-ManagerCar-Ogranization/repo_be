package com.example.controller;

import com.example.dto.ReqRes;
import com.example.service.account.UsersManagementService;
import com.example.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/public")
public class MailController {
    @Autowired
    private MailService mailService;
    @Autowired
    private UsersManagementService usersManagementService;

    @PostMapping("/send/{mail}")
    public ResponseEntity<String> sendMail(@PathVariable String mail) {
        mailService.sendMail(mail);
        return ResponseEntity.ok("ok");
    }
//    @PostMapping("/verify/{otp}")
//    public ResponseEntity<ReqRes> verifyOtp(@PathVariable String otp, @RequestBody ReqRes ReqRes) {
//        return  ResponseEntity.ok(usersManagementService.forgotPassword(ReqRes,mailService.verifyOtpMail(ReqRes.getEmail(),otp)));
//    }

}
