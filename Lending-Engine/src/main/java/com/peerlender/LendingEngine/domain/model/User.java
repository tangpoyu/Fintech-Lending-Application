package com.peerlender.LendingEngine.domain.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public final class User {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myUser_generator")
    private long id;
    private String firsName, lastname, occupation;
    private  int age;

    public User(){
        System.out.println("1");
    }

    public User(String firsName, String lastname, String occupation, int age) {
        this.firsName = firsName;
        this.lastname = lastname;
        this.occupation = occupation;
        this.age = age;
    }

    public String getFirsName() {
        return firsName;
    }

    public String getLastname() {
        return lastname;
    }

    public String getOccupation() {
        return occupation;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && Objects.equals(firsName, user.firsName) && Objects.equals(lastname, user.lastname) && Objects.equals(occupation, user.occupation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firsName, lastname, occupation, age);
    }

    @Override
    public String toString() {
        return "User{" +
                "firsName='" + firsName + '\'' +
                ", lastname='" + lastname + '\'' +
                ", occupation='" + occupation + '\'' +
                ", age=" + age +
                '}';
    }
}
