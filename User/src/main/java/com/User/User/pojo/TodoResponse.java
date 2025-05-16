package com.User.User.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class TodoResponse {
    private String todoId;
    private String name;
    private String description;
    private double price;
    private Date manufactureTime;
    private Date expTime;
}
