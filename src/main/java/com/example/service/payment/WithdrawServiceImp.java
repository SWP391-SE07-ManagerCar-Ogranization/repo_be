package com.example.service.payment;

import com.example.entity.Withdraw;
import com.example.repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WithdrawServiceImp implements WithdrawService{
    @Autowired
    private WithdrawRepository repository;
    @Override
    public Withdraw save(Withdraw withdraw) {
        return repository.save(withdraw);
    }

    @Override
    public List<Withdraw> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteWithdraw(int withdrawId) {
        repository.deleteById(withdrawId);
    }

    @Override
    public Withdraw findById(int withdrawId) {
        return repository.findById(withdrawId).orElse(null);
    }
}
