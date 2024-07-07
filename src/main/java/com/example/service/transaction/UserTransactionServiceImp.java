package com.example.service.transaction;


import com.example.entity.*;
import com.example.repository.UserTransactionRepository;
import com.example.service.DriverType.DriverTypeService;
import com.example.service.payment.PaymentMethodService;
import com.example.service.position.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserTransactionServiceImp implements UserTransactionService {
    @Autowired
    private UserTransactionRepository userTransactionRepository;
    @Autowired
    private PositionService positionService;
    @Autowired
    private PaymentMethodService paymentMethodService;
    @Autowired
    private DriverTypeService driverTypeService;
    @Override
    public UserTransaction add(UserTransaction userTransaction) {
        return userTransactionRepository.save(userTransaction);
    }


    @Override
    public void delete(@RequestBody UserTransaction userTransaction) {
        userTransactionRepository.delete(userTransaction);
    }

    @Override
    public UserTransaction update(UserTransaction userTransaction) {
        Optional<UserTransaction> existingTransaction = userTransactionRepository.findById(userTransaction.getTransactionId());
        if (existingTransaction.isPresent()) {
            return userTransactionRepository.save(userTransaction);
        } else {
            return null;
        }
    }

    @Override
    public List<UserTransaction> getAll() {
        return userTransactionRepository.findAll();
    }

    @Override
    public UserTransaction getById(Integer id) {
        return userTransactionRepository.findById(id).orElse(null);
    }

    @Override
    public UserTransaction getByCreateAt(LocalDateTime creatAt) {
        return userTransactionRepository.findUserTransactionByCreateAt(creatAt);
    }

    @Override
    public double calculateMoneyByDistance(double distance) {
        return Math.round(distance * 10000 / 1000.0) * 1000.0;
    }

    @Override
    public double calculateMoneyByDistanceAndDriverType(double distance, DriverType type) {
        double rootAmount = calculateMoneyByDistance(distance);
        System.out.println(rootAmount);
        if (type.getCapacity() == 1) {
            return rootAmount * 0.5;
        } else if (type.getCapacity() == 4) {
            return rootAmount * 0.7;
        } else if (type.getCapacity() == 6) {
            return rootAmount * 0.8;
        } else {
            return rootAmount * 0.9;
        }
    }

    @Override
    public List<UserTransaction> findAllByDriverDetail(DriverDetail driverDetail) {
        return userTransactionRepository.findAllByDriverDetail(driverDetail);
    }

    @Override
    public void addTransactionWithGroupCar(GroupCar groupCar) {
        Set<Customer> customers = groupCar.getCustomers();
        double distance = positionService.calculateDistanceByName(groupCar.getStartPoint(), groupCar.getEndPoint());
        DriverType driverType = driverTypeService.getByCapacity(groupCar.getCapacity());
        PaymentMethod paymentMethod = paymentMethodService.getById(2);

        for (Customer customer : customers) {
            UserTransaction transaction = new UserTransaction();
            transaction.setCustomer(customer);
            transaction.setDriverDetail(groupCar.getDriverDetail());
            transaction.setCreateAt(groupCar.getCreateAt());
            transaction.setAmount(calculateMoneyByDistanceAndDriverType(distance, driverType)/groupCar.getCapacity());
            transaction.setPaymentMethod(paymentMethod);
            transaction.setGroupCar(groupCar);
            System.out.println("Transaction: " + transaction);
            add(transaction);
        }
    }

}
