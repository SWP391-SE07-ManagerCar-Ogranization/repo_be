package com.example.service.payment;

import com.example.entity.PaymentMethod;
import com.example.entity.Transaction;
import com.example.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodServiceImp implements PaymentMethodService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public PaymentMethod add(PaymentMethod paymentMethod) {
        return paymentRepository.save(paymentMethod);
    }

    @Override
    public void delete(PaymentMethod paymentMethod) {
        paymentRepository.delete(paymentMethod);
    }

    @Override
    public PaymentMethod update(PaymentMethod paymentMethod) {
        Optional<PaymentMethod> existingPayment = paymentRepository.findById(paymentMethod.getPaymentMethodId());
        if(existingPayment.isPresent()){
            return paymentRepository.save(paymentMethod);
        }else {
            return null;
        }
    }

    @Override
    public List<PaymentMethod> getAll() {
        return paymentRepository.findAll();
    }

    @Override
    public PaymentMethod getById(Integer id) {
        return paymentRepository.findById(id).orElse(null);
    }
}
