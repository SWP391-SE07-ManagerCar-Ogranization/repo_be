package com.example.controller;

import com.example.entity.Feedback;
import com.example.repository.FeedbackRepository;
import com.example.service.feedback.FeedbackService;
import com.example.service.feedback.FeedbackServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class FeedbackManagementController {

    @Autowired
    private FeedbackServiceImp feedbackServiceImp;

    @GetMapping("/public/get-all-feedbacks")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        return ResponseEntity.ok(feedbackServiceImp.findAllFeedback());
    }

    @PostMapping("/public/feedback-driver/delete/{id}")
    public void deleteFeedback(@PathVariable Integer id) {
       feedbackServiceImp.deleteFeedbackById(id);
    }
}
