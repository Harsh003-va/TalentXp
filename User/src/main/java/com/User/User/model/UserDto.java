package com.User.User.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Data
public class UserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private long id;

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Collection<String> role;

}
