package com.User.User.pojo;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,length = 100)
    private String todoId;

    @Column(nullable = false,length = 100)
    private String name;
    @Column(nullable = false,length = 100)
    private String description;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date manufactureTime;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private  Date expTime;

}
