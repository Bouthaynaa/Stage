package com.example.stage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class SousTraitant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String titre;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private int phoneNumber;
    private String firstName;
    private String LastName;


    @ManyToOne
    private Company company; //(nullable)
}
