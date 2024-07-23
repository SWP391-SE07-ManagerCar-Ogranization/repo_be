package com.example;

import com.example.entity.Customer;
import com.example.entity.DriverDetail;
import com.example.service.DriverDetail.DriverDetailService;
import com.example.service.DriverType.DriverTypeService;
import com.example.service.account.OurUserDetailsService;
import com.example.service.customer.CustomerService;
import com.example.service.feedback.FeedbackService;
import com.example.service.position.DirectionService;
import com.example.service.position.PositionService;
import com.example.service.transaction.UserTransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class BookingCarApplicationTests {

	@Autowired
	private DirectionService directionService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Test
	void contextLoads() {
		System.out.println(directionService.calculateDistance2(21.028511, 105.854443, 10.779784, 106.699184));
	}

}
