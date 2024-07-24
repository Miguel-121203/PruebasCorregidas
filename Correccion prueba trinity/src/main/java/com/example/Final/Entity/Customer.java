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
    private String tipoId;

    @NotNull
    private int numId;

    @Size(min = 2) // EL NUMERO MINIMO DE CARACTERES QUE DEBE TENER EL NOMBRE ES DE 2
    @NotBlank
    private String nombre;

    @Size(min = 2)
    @NotBlank
    private String apellido;

    @Email // VALIDAMOS QUE TENGA UN FORMATO DE CORREO ELECTRONICO
    @NotBlank
    private String email;

    @Past
    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime fechaModificacion;




}
