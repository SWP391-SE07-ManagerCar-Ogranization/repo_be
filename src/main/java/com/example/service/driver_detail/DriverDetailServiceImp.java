package com.example.service.driver_detail;

import com.example.entity.DriverDetail;
import com.example.repository.DriverDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverDetailServiceImp implements DriverDetailService{
    @Autowired
    DriverDetailRepository driverDetailRepository;

    @Override
    public DriverDetail getDriverDetail(Integer id) {
        return driverDetailRepository.findById(id).orElse(null);
    }

    @Override
    public List<DriverDetail> getAllDriverDetails() {
        return List.of();
    }

    @Override
    public void deleteDriverDetail(int id) {

    }

    @Override
    public DriverDetail saveDriverDetail(DriverDetail driverDetail) {
        return driverDetailRepository.save(driverDetail);
    }
}
