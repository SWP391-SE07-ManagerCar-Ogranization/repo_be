package com.example.service.account;


import com.example.entity.Account;

import java.util.List;


public interface AccountService {
    Account findByEmail(String email);
    boolean emailExists(String email);
    Account addAccount(Account account);
    List<Account> getAllAccount();
    void deleteAccount(int accountId);
    Account findById(Integer id);

}
