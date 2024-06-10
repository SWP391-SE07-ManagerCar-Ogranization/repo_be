package com.example.controller;

import com.example.entity.Feedback;
import com.example.service.feedback.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class FeedbackManagementController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/public/get-all-feedbacks")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        return ResponseEntity.ok(feedbackService.findAllFeedback());
    }

    @PostMapping("/public/feedback-driver/delete/{id}")
    public void deleteFeedback(@PathVariable Integer id) {
       feedbackService.deleteFeedbackById(id);
    }
    
    @GetMapping("/public/feedback-driver/find-all/{id}")
    public ResponseEntity<List<Feedback>> getAllFeedbackByDriverId(@PathVariable Integer id){
        return ResponseEntity.ok(feedbackService.findAllFeedbackByDriverDetailId(id));
    }
}