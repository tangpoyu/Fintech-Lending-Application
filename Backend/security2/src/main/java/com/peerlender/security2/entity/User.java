package com.peerlender.security2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    private String username;
    private String password;

//    private String firstName, lastName;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE",
        joinColumns = {
            @JoinColumn(name = "USER_ID")
        },
            inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID")
            }
    )
    private Set<Role> role ;

    public User(String username, String password, Set<Role> role) {
        this.username = username;
        this.password = password;
//        this.firstName = firstName;
//        this.lastName = lastName;
        this.role = role;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
