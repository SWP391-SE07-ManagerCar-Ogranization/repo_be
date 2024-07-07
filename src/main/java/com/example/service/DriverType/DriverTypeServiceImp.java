package com.example.service.DriverType;

import com.example.entity.DriverType;
import com.example.repository.DriverTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class DriverTypeServiceImp implements DriverTypeService {
    @Autowired
    private DriverTypeRepository driverTypeRepository;

    @Override
    public List<DriverType> getAll() {
        return driverTypeRepository.findAll();
    }

    @Override
    public List<DriverType> getAllDriverCar() {
        List<DriverType> list = driverTypeRepository.findAll();
        List<DriverType> listDriverCar = new ArrayList<>();
        for(DriverType type : list) {
            if (type.getDriverTypeName().toLowerCase().contains("Seater".toLowerCase())) {
                listDriverCar.add(type);
            }
        }
        return listDriverCar;
    }

    @Override
    public DriverType getByCapacity(int capacity) {
        return driverTypeRepository.getDriverTypeByCapacity(capacity);
    }

}
