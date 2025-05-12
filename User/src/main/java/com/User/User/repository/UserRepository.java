package com.User.User.repository;


import com.User.User.model.UserDto;
import com.User.User.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findByEmail(String email);

    UserEntity findByUserId(String userId);

    UserEntity deleteByUserId(String userId);
}
