package com.example.service.DriverDetail;

import com.example.entity.DriverDetail;
import com.example.entity.DriverType;

import java.util.List;
public interface DriverDetailService {
   List<DriverDetail> findAllByDriverTypeId(Integer id);

   DriverDetail add(DriverDetail driverDetail);

   DriverDetail update(DriverType driverType);

   List<DriverDetail> getAll();

   DriverDetail findDriverDetailById(Integer id);
   DriverDetail getDriverDetail(Integer id);
}





