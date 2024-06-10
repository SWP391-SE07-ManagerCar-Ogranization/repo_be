package com.example.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;
    Map<String,String> otpMap = new HashMap<>();
    public void sendMail(String mail) {
        String otp = String.valueOf(generateRandomFiveDigitNumber());
        otpMap.put(mail, otp);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject("[FCAR] FORGOT PASSWORD " + mail);
        simpleMailMessage.setText( "Your Code to recovery password is: " + otp);
        simpleMailMessage.setTo(mail);
        mailSender.send(simpleMailMessage);
    }

    public boolean verifyOtpMail(String mail, String otp) {
        if(otpMap.containsKey(mail)) {
            return otp.equals(otpMap.get(mail));
        }
        return false;
    }

    public int generateRandomFiveDigitNumber() {
        Random random = new Random();
        return 10000 + random.nextInt(90000);
    }

}


