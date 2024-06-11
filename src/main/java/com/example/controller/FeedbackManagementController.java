package com.example.controller;

import com.example.dto.FeedbackReqRes;
import com.example.entity.Customer;
import com.example.entity.DriverDetail;
import com.example.entity.Feedback;
import com.example.repository.FeedbackRepository;
import com.example.service.customer.CustomerService;
import com.example.service.driver_detail.DriverDetailService;
import com.example.service.feedback.FeedbackService;
import com.example.service.feedback.FeedbackServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/public")
public class FeedbackManagementController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DriverDetailService driverDetailService;

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

    @PostMapping("/add-feedback")
    public ResponseEntity<Feedback> addFeedback(@RequestBody Feedback feedback) {
        return ResponseEntity.ok(feedbackService.saveFeedback(feedback));
    }

    @PostMapping("/add-new-feedback")
    public ResponseEntity<Feedback> addNewFeedback(@RequestBody FeedbackReqRes feedbackReqRes) {
         Customer customer = customerService.getCustomer(feedbackReqRes.getCustomerId());
         DriverDetail driverDetail = driverDetailService.getDriverDetail(feedbackReqRes.getDriverDetailId());
         driverDetail.setRating(feedbackReqRes.getRating());
         Feedback fb = feedbackService.saveFeedback(new Feedback(0, feedbackReqRes.getFeedbackContent(),new Date(),new Date(),customer,
                 driverDetail));
            return ResponseEntity.ok(fb);
    }
}

//public Feedback(Integer feedbackId, String feedbackContent, Date createAt, Date updateAt, Customer customer, DriverDetail driverDetail) {
//    this.feedbackId = feedbackId;
//    this.feedbackContent = feedbackContent;
//    this.createAt = createAt;
//    this.updateAt = updateAt;
//    this.customer = customer;
//    this.driverDetail = driverDetail;
