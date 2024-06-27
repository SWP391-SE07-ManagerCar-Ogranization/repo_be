package com.example.controller;

import com.example.dto.ReqRes;
import com.example.entity.DriverDetail;
import com.example.service.account.OurUserDetailsService;
import com.example.service.DriverDetail.DriverDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/public/driver")
public class DriverController {
    @Autowired
    OurUserDetailsService ourUserDetailsService;
    @Autowired
    DriverDetailService driverDetailService;

    @PostMapping("/update-status")
    public ResponseEntity<ReqRes> updateStatus(@RequestBody ReqRes reqRes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        DriverDetail driverDetail = ourUserDetailsService.findByEmail(email).getDriverDetail();
            driverDetail.setWorkingStatus(reqRes.isWorkingStatus());
            driverDetailService.saveDriverDetail(driverDetail);
            reqRes.setMessage("success");
        return ResponseEntity.ok(reqRes);
    }
}
