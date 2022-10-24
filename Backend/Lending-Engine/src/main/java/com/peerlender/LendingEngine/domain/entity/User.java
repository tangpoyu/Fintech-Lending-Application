package com.peerlender.LendingEngine.domain.entity;

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
    private String first_name, last_name, occupation;
    private  int age;
    @OneToOne(cascade = CascadeType.ALL)
    private Balance balance;

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
