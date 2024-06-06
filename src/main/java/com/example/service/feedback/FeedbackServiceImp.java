package com.example.service.feedback;

import com.example.entity.Feedback;
import com.example.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

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

    @Override
    public List<Feedback> findAllFeedbackByDriverDetailId(int driverDetailId) {
        return feedbackRepository.findAllByDriverDetailId(driverDetailId);
    }

    @Override
    public Map<Integer, List<Feedback>> getFeedbacksForm() {



        return Map.of();
    }

}
