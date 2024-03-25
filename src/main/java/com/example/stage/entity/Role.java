package com.example.stage.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role() {
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public void setName(ERole name) {
        this.name = name;
    }

    public Long getIdRole() {
        return idRole;
    }

    public ERole getName() {
        return name;
    }

    public Role(ERole name) {
        this.name = name;
    }

    public Role(Long idRole, ERole name) {
        this.idRole = idRole;
        this.name = name;
    }
}
