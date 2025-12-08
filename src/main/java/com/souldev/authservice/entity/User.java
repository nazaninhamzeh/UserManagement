package com.souldev.authservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //equal and hashcode only for marked fields
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Include // this field will be used in equals and hashcode methods
    private String username;

    @Column(nullable = false)
    private String password;

    private String firstName;
    private String lastName;

}
