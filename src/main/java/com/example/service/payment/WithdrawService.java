package com.example.service.payment;

import com.example.entity.Role;
import com.example.entity.Withdraw;

import java.util.List;

public interface WithdrawService {
    Withdraw save(Withdraw withdraw);
    List<Withdraw> getAll();
    void deleteWithdraw(int withdrawId);
    Withdraw findById(int withdrawId);
}
