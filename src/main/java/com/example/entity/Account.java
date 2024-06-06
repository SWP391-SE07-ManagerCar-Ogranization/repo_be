package com.example.entity;


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "account")
@Data
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer accountId;

    @Column(unique = true)
    private String email;

    private String name;

    private String password;

    private String idCard;

    @Column(unique = true)
    private String phone;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private String image;

    private String dob;

    private String address;

    private Date createdAt;

    private Date updateAt;

    private boolean status;

    private double accountBalance;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="role_id")
    private Role role;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private DriverDetail driverDetail;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Customer customer;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getRoleName()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
