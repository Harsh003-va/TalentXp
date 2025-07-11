package com.User.User.controller;


import com.User.User.model.CreateUserRequestModel;
import com.User.User.model.CreateUserResponseModel;
import com.User.User.model.DeleteResponse;
import com.User.User.model.UserDto;
import com.User.User.modelMapper.ModelMapperSingleton;
import com.User.User.service.UserService;

import com.User.User.shared.Roles;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/createUser")
    public ResponseEntity<CreateUserResponseModel> createUser(@Validated @RequestBody CreateUserRequestModel createUserRequestModel) {
        ModelMapper mapper = ModelMapperSingleton.getInstance();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(createUserRequestModel, UserDto.class);
        userDto.setRole(new HashSet<>(Arrays.asList(Roles.ROLE_USER.name())));
        UserDto createdUser = service.createUser(userDto);

        CreateUserResponseModel responce = mapper.map(createdUser, CreateUserResponseModel.class);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responce);


    }

    @GetMapping("/user")
    public ResponseEntity<List<UserDto>> getAllUser() {
        ModelMapper mapper = ModelMapperSingleton.getInstance();

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<UserDto> newDto = service.getAllUsers();

        return ResponseEntity.status(HttpStatus.FOUND).body(newDto);

    }


    @GetMapping(value = "/getAll/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CreateUserResponseModel> getUser(@PathVariable String userId) {
        UserDto userDetails = service.getUserById(userId);
        CreateUserResponseModel res = ModelMapperSingleton.getInstance().map(userDetails, CreateUserResponseModel.class);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(value = "/delete/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DeleteResponse> deleteByUserId(@PathVariable String userId) {
        UserDto userDetails = service.deleteUserDetailsByID(userId);
        DeleteResponse res = ModelMapperSingleton.getInstance().map(userDetails, DeleteResponse.class);
        return ResponseEntity.ok().contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE)).body(res);
    }
}
