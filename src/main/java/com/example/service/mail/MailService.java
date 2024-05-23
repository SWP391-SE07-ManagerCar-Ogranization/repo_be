package com.example.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;
    private int CODE = 0;

    public void sendMail(String mail) {
        CODE = generateRandomFiveDigitNumber();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject("[FCAR] FORGOT PASSWORD " + mail);
        simpleMailMessage.setText(generateContentForgot());
        simpleMailMessage.setTo(mail);
        mailSender.send(simpleMailMessage);
    }

    public boolean verifyOtpMail(int otp) {
        return CODE == otp;
    }

    public String generateContentForgot() {
        return "Your Code to recovery password is: " + CODE;
    }

    public int generateRandomFiveDigitNumber() {
        Random random = new Random();
        return 10000 + random.nextInt(90000);
    }

}


