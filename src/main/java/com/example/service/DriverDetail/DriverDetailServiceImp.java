package com.example.service.DriverDetail;

import com.example.entity.DriverDetail;
import com.example.entity.DriverType;
import com.example.entity.GroupCar;
import com.example.repository.DriverDetailRepository;
import com.example.repository.GroupCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverDetailServiceImp implements DriverDetailService {
    @Autowired
    private DriverDetailRepository driverDetailRepository;

    @Autowired
    private GroupCarRepository groupCarRepository;
    @Override
    public List<DriverDetail> findAllByDriverTypeId(Integer id) {
        return driverDetailRepository.findAllByDriverTypeId(id);
    }

    @Override
    public DriverDetail add(DriverDetail driverDetail) {
        return driverDetailRepository.save(driverDetail);
    }

    @Override
    public void delete(DriverType driverType) {

    }

    @Override
    public DriverDetail update(DriverType driverType) {
        return null;
    }

    @Override
    public List<DriverDetail> getAll() {
        return List.of();
    }

    @Override
    public DriverDetail findDriverDetailById(Integer id) {
        return driverDetailRepository.findById(id).orElse(null);
    }
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

    @Override
    public boolean checkValidDriver(DriverDetail driverDetail) {
        return driverDetail.getDriverLicence() != null && driverDetail.getVehicleNumber() != null && driverDetail.getAccount().getIdCard() != null;
    }

}
