package com.example;

import com.example.entity.Customer;
import com.example.entity.DriverDetail;
import com.example.service.DriverDetail.DriverDetailService;
import com.example.service.DriverType.DriverTypeService;
import com.example.service.account.OurUserDetailsService;
import com.example.service.customer.CustomerService;
import com.example.service.feedback.FeedbackService;
import com.example.service.position.PositionService;
import com.example.service.transaction.UserTransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class BookingCarApplicationTests {

	@Autowired
	private DriverTypeService customerService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Test
	void contextLoads() {
		System.out.println(customerService.getAllDriverCar());
	}

}
