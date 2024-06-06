package com.example.service.feedback;

import com.example.entity.Feedback;

import java.util.List;

public interface FeedbackService {
    List<Feedback> findAllFeedback();
    Feedback findFeedbackById(int id);
    void deleteFeedbackById(int id);
}
