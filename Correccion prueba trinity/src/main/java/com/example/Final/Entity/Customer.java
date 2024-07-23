package com.example.Final.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String typeId;

    @NotNull
    private int numId;

    @Size(min = 2) // EL NUMERO MINIMO DE CARACTERES QUE DEBE TENER EL NOMBRE ES DE 2
    @NotBlank
    private String name;

    @Size(min = 2)
    @NotBlank
    private String lastName;

    @Email // VALIDAMOS QUE TENGA UN FORMATO DE CORREO ELECTRONICO
    @NotBlank
    private String email;

    @Past
    @Column(nullable = false)
    private LocalDate birthdate;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime modicationDate;


    // EL PREPERSIST SIRVE PARA INICIALIZAR LOS DATOS ANTES DE CREAR LA PERSISTENCIA EN LA BASE DE DATOS
    @PrePersist
    protected void onCreate(){
        this.creationDate = LocalDateTime.now();
        this.modicationDate = LocalDateTime.now();
    }

    // EN ESTA ACTUALIZAMOS LA FECHA DE MODIFICACION JUSTO ANTES DE HACER LA PERSISTENCIA DE NUEVO EN LA BASE DE DATOS
    @PreUpdate
    protected void onUpdate(){
        this.modicationDate = LocalDateTime.now();
    }



}
