package com.autocrypt.mon.account;

import com.autocrypt.mon.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Account findByAccountId(String accountId);
}
