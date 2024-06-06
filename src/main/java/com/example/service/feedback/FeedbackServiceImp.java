package com.example.service.feedback;

import com.example.entity.Feedback;
import com.example.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class FeedbackServiceImp implements FeedbackService{

    @Autowired
    FeedbackRepository feedbackRepository;

    @Override
    public List<Feedback> findAllFeedback() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback findFeedbackById(int id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteFeedbackById(int id) {
         feedbackRepository.deleteById(id);
    }
}
