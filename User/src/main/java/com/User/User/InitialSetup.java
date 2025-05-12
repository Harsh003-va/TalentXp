package com.User.User;


import com.User.User.model.AuthorityEntity;
import com.User.User.model.RoleEntity;
import com.User.User.model.UserEntity;
import com.User.User.repository.AuthorityRepository;
import com.User.User.repository.RoleRepository;
import com.User.User.repository.UserRepository;
import com.User.User.shared.Authority;
import com.User.User.shared.Roles;
import com.User.User.shared.Utills;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

@Component
public class InitialSetup {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    Utills utills;

    @Transactional
    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event){
  AuthorityEntity readAuthority=    createAuthority(Authority.READ_AUTHORITY.name());
   AuthorityEntity writeAuthority=   createAuthority(Authority.WRITE_AUTHORITY.name());
     AuthorityEntity deleteAuthority= createAuthority(Authority.DELETE_AUTHORITY.name());

      createRole(Roles.ROLE_USER.name(), Arrays.asList(readAuthority,writeAuthority));
        RoleEntity roleAdmin=createRole(Roles.ROLE_ADMIN.name(),Arrays.asList(readAuthority,writeAuthority,deleteAuthority));
       if(roleAdmin==null)return;
        UserEntity adminUser=new UserEntity();
        adminUser.setFirstName("Harsh");
        adminUser.setLastName("Atigeri");
        adminUser.setEmail("harsh@gamil.com");
        adminUser.setUserId(utills.generateUserId(30));
        adminUser.setPassword(bCryptPasswordEncoder.encode("12456798"));
        adminUser.setRoles(Arrays.asList(roleAdmin));


        userRepository.save(adminUser);

    }

    @Transactional
   private AuthorityEntity createAuthority(String name){
        AuthorityEntity entity =authorityRepository.findByName(name);
        if(entity ==null) {
            entity = new AuthorityEntity(name);
            authorityRepository.save(entity);
        }
return entity;
    }

    @Transactional
    private RoleEntity createRole(String name, Collection<AuthorityEntity> authorities){
      RoleEntity entity=roleRepository.findByName(name);
      if(entity==null) {
          entity = new RoleEntity(name);
          entity.setAuthorities(authorities);
          roleRepository.save(entity);
      }
      return entity;
    }


}
