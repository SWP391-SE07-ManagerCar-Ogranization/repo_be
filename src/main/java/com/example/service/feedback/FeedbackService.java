package com.example.service.feedback;

import com.example.entity.Feedback;

import java.util.List;
import java.util.Map;

public interface FeedbackService {
    List<Feedback> findAllFeedback();
    Feedback findFeedbackById(int id);
    void deleteFeedbackById(int id);
    List<Feedback> findAllFeedbackByDriverDetailId(int driverDetailId);
//    Map<Integer,List<Feedback>> getFeedbacksForm();
}
