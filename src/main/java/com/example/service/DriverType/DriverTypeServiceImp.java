package com.example.service.DriverType;

import com.example.entity.DriverType;
import com.example.repository.DriverTypeRepository;
import com.example.service.driverType.DriverTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DriverTypeServiceImp implements DriverTypeService {
    @Autowired
    private DriverTypeRepository driverTypeRepository;

    @Override
    public List<DriverType> getAll() {
        return  driverTypeRepository.findAll();
    }
}
