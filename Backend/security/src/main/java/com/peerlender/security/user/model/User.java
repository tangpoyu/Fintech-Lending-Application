package com.peerlender.security.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    private long id;

    @JoinColumn
    @OneToOne(cascade = CascadeType.ALL)
    private UserDetailsImpl userDetails;

    public User(UserDetailsImpl userDetails) {
        this.userDetails = userDetails;
    }
}
