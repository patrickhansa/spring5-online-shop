package com.ecommerce.spring5onlineshop.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Member extends BaseEntity {

    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String gender;
    private String birthDate;

    @Lob
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;
}
