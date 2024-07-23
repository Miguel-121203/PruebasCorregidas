package com.example.Final.Entity;

import com.example.Final.Entity.Enum.AccountState;
import com.example.Final.Entity.Enum.AccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "cuentas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(unique = true, length = 10)
    private String accountNum;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountState accountState;

    @PositiveOrZero
    private Double balance;

    @NotNull
    private Boolean gmf;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime modificationDate;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cliente_id",nullable = false)
    private Customer customer;


    @PrePersist
    protected void onCreate(){
        this.creationDate = LocalDateTime.now();
        this.modificationDate = LocalDateTime.now();
        if (this.accountType == AccountType.AHORRO){
            this.accountState = AccountState.ACTIVA;
            this.accountNum = generateAccountNumber("53");
        } else if (this.accountType == AccountType.CORRIENTE) {
            this.accountNum = generateAccountNumber("33");
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.modificationDate = LocalDateTime.now();
    }

    // ESTE METODO SIRVE PARA GENERAR EL NUMERO DE CUENTA EN AUTOMATICO
        private String generateAccountNumber(String prefijo) {
        return prefijo + String.format("%08d", (int) (Math.random() * 100000000));
    }


}