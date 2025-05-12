package com.User.User.repository;

import com.User.User.model.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepository extends CrudRepository<AuthorityEntity,Long> {
    AuthorityEntity findByName(String name);
}
