package com.example.repository;


import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByEmail(String email);

    List<Account> findByRoleRoleId(Integer roleId);

    Account findByAccountId(Integer accountId);

    @Modifying
    @Transactional
    @Query("update Account a set a.status = :status where a.accountId = :id")
    int updateStatusById( @Param("id")Integer id,@Param("status")Boolean status);
    Optional<Account> findByPhone(String phone);

}
