package com.User.User.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.engine.internal.Cascade;
import org.springframework.data.repository.cdi.Eager;

import java.util.Collection;

@Entity
@Data
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String userId;
    @Column(nullable = false, length = 100)
    private String firstName;
    @Column(nullable = false, length = 100)
    private String lastName;
    @Column(nullable = false, length = 100,unique = true)
    private String email;
    @Column(nullable = false,unique = true)
    private String password;

    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns=@JoinColumn(name="users_id", referencedColumnName = "id"),
            inverseJoinColumns=@JoinColumn(name="roles_id", referencedColumnName="id"))
    private Collection<RoleEntity> roles;
}
