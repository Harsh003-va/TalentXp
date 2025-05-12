package com.User.User.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Data
@NoArgsConstructor

@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;

    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinTable(name = "roles_authorites",
            joinColumns=@JoinColumn(name="roles_id", referencedColumnName = "id"),
            inverseJoinColumns=@JoinColumn(name="authorities_id", referencedColumnName="id"))
    private Collection<AuthorityEntity> authorities;

    public RoleEntity(String name){this.name=name;}
}
