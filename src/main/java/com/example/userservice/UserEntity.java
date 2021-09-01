package com.example.userservice;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false,length = 50,unique = true)
    private String email;

    @Column(nullable = false,length = 50)
    private String userId;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    private String encryptedPwd;
}
