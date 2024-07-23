package com.example.controller;

import com.example.dto.FeedbackReqRes;
import com.example.entity.*;
import com.example.service.account.OurUserDetailsService;
import com.example.service.customer.CustomerService;
import com.example.service.DriverDetail.DriverDetailService;
import com.example.service.feedback.FeedbackService;
import com.example.service.invoice.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/public")
public class FeedbackManagementController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private DriverDetailService driverDetailService;
    @Autowired
    private OurUserDetailsService ourUserDetailsService;
    @GetMapping("/get-all-feedbacks")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        return ResponseEntity.ok(feedbackService.findAllFeedback());
    }

    @DeleteMapping("/feedback-driver/delete/{id}")
    public void deleteFeedback(@PathVariable Integer id) {
       feedbackService.deleteFeedbackById(id);
    }

    @GetMapping("/feedback-driver/find-all/{id}")
    public ResponseEntity<List<Feedback>> getAllFeedbackByDriverId(@PathVariable Integer id){
        return ResponseEntity.ok(feedbackService.findAllFeedbackByDriverDetailId(id));
    }
    @PostMapping("/add-new-feedback")
    public ResponseEntity<Feedback> addNewFeedback(@RequestBody FeedbackReqRes feedbackReqRes) {
        // convert driver detail id to invoice id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        System.out.println("email here: "+email);
        Account account = ourUserDetailsService.findByEmail(email);
        Invoice invoice = invoiceService.getById(feedbackReqRes.getDriverDetailId());
        DriverDetail driverDetail = invoice.getDriverDetail();
        int newTotalRating = driverDetail.getTotalRating() + 1;
        double newRating = (feedbackReqRes.getRating() + driverDetail.getRating()*driverDetail.getTotalRating())/ newTotalRating;
        driverDetail.setTotalRating(newTotalRating);
        driverDetail.setRating(newRating);
        Feedback fb = feedbackService.saveFeedback(new Feedback(0, feedbackReqRes.getFeedbackContent(),new Date(),new Date(),account.getCustomer(),
                driverDetail));
        return ResponseEntity.ok(fb);
    }
}

