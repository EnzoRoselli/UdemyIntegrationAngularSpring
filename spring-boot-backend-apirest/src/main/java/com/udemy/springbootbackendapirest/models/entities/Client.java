package com.udemy.springbootbackendapirest.models.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "clients")
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message ="no puede estar vacio")
    @Size(min=3, max=12, message="el tamaño tiene que estar entre 3 y 12")
    private String name;

    @NotEmpty(message ="no puede estar vacio")
    private String surname;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @NotEmpty(message ="no puede estar vacio")
    @Email(message="no es una dirección de correo bien formada")
    private String email;

    @PrePersist
    public void prePersist(){
        createdAt = new Date();
    }
}
