package com.example.service.DriverType;

import com.example.entity.DriverType;
import java.util.List;

public interface DriverTypeService {
    List<DriverType> getAll();
    List<DriverType> getAllDriverCar();
    DriverType getByCapacity(int capacity);
}
