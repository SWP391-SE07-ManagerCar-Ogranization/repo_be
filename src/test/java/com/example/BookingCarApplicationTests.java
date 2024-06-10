package com.example;

import com.example.service.feedback.FeedbackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookingCarApplicationTests {

	@Autowired
	private FeedbackService feedbackService;
	@Test
	void contextLoads() {
		System.out.println(feedbackService.findFeedbackById(7).getCustomer().toString());
	}

}
