package com.autocrypt.mon.security.service;

import com.autocrypt.mon.account.AccountRepository;
import com.autocrypt.mon.account.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


@Service
public class VsocUserDetailsService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String accountId) {
        Account account = accountRepository.findByAccountId(accountId);

        Collection<GrantedAuthority> grantedAuthorityCollection = new ArrayList<>();
        grantedAuthorityCollection.add(new SimpleGrantedAuthority(account.getRole()));

        return new User(account.getAccountId(), account.getPassword(), grantedAuthorityCollection);
    }

}
