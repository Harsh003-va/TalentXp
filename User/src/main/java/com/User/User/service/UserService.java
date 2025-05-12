package com.User.User.service;

import com.User.User.model.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    public UserDto createUser(UserDto userDto);
    public UserDto getUserDetailsBYEmail(String emial);
    public List<UserDto> getAllUsers();

  public  UserDto getUserById(String userId);
  public UserDto deleteUserDetailsByID(String userId);

}
