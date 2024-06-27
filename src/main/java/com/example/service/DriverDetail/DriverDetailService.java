package com.example.service.DriverDetail;

import com.example.entity.DriverDetail;
import com.example.entity.DriverType;

import java.util.List;
public interface DriverDetailService {
   List<DriverDetail> findAllByDriverTypeId(Integer id);

   DriverDetail add (DriverDetail driverDetail);

   void delete(DriverType driverType);

   DriverDetail update(DriverType driverType);

   List<DriverDetail> getAll();

   DriverDetail findDriverDetailById(Integer id);
   DriverDetail getDriverDetail(Integer id);
   List<DriverDetail> getAllDriverDetails();
   void deleteDriverDetail(int id);
   DriverDetail saveDriverDetail(DriverDetail driverDetail);
}





