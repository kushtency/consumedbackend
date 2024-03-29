package com.nagarro.consumedbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@NoArgsConstructor
@Table(name="Users")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;
    @Column(name = "name")
    private String name;
    @Column(name = "nationality")
    private String nationality;
    @Column(name = "age")
    private int age;
    @Column(name = "gender")
    private String gender;
    @Column(name = "verificationStatus")
    private String verificationStatus;
    @Column(name = "DOB")
    private String DOB;
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public User(String name,
                String nationality,
                int age,
                String gender,
                String verificationStatus,
                String DOB,
                LocalDateTime modifiedDate,
                LocalDateTime createdDate) {
        this.name = name;
        this.nationality = nationality;
        this.age = age;
        this.gender = gender;
        this.verificationStatus = verificationStatus;
        this.DOB = DOB;
        this.modifiedDate = modifiedDate;
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    }

    public String getCreatedDate() {
        return createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
