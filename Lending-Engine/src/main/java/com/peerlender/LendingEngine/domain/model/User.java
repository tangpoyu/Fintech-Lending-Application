package com.peerlender.LendingEngine.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;



@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public final class User {

    @Id
    private String username;
    private String firsName, lastname, occupation;
    private  int age;
}
