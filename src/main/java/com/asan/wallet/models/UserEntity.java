package com.asan.wallet.models;


import com.asan.wallet.models.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends AbstractEntity {


    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String firstName;

    private String lastName;

    private String accountNumber;

    private Long cardNumber;

    @OneToOne
    private WalletEntity wallet;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Role role = Role.USER;


}
