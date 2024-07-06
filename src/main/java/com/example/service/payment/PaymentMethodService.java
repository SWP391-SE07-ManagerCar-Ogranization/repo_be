package com.example.service.payment;

import com.example.entity.PaymentMethod;

import java.util.List;

public interface PaymentMethodService {
    PaymentMethod add (PaymentMethod paymentMethod);

    void delete(PaymentMethod paymentMethod);

    PaymentMethod update(PaymentMethod paymentMethod);

    List<PaymentMethod> getAll();

    PaymentMethod getById(Integer id);

}
