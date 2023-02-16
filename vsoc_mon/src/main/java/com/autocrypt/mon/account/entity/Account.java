package com.autocrypt.mon.account.entity;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Collection;


@Entity
@Table(name = "vsoc_account")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account implements UserDetails {

    private static final String INITIAL_PASSWORD = "1234!@#$";

    @Id @NotNull
    @Size(max = 30)
    @Column(name = "account_id")
    private String accountId;

    @NotNull
    @Size(max = 30)
    @Column(name = "name")
    private String name;

    @NotNull
    @Size(max = 200)
    @Column(name = "password")
    private String password;

    @NotNull
    @Size(max = 20)
    @Column(name = "role")
    private String role;

    @NotNull
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_access_time")
    private LocalDateTime lastAccessTime;

    @Column(name = "log_check_time")
    private LocalDateTime logCheckTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorityCollection = new ArrayList<GrantedAuthority>();
        grantedAuthorityCollection.add(new SimpleGrantedAuthority(getRole()));

        return grantedAuthorityCollection;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
