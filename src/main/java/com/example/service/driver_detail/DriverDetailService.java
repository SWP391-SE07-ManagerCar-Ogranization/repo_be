package com.example.service.driver_detail;

import com.example.entity.DriverDetail;

import java.util.List;

public interface DriverDetailService {
    DriverDetail getDriverDetail(Integer id);
    List<DriverDetail> getAllDriverDetails();
    void deleteDriverDetail(int id);
    DriverDetail saveDriverDetail(DriverDetail driverDetail);
}
