package com.User.User.repository;

import com.User.User.model.RoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity,Long> {
    RoleEntity findByName(String name);
}
