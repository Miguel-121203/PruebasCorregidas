package com.example.Final.Entity;

import com.example.Final.Entity.Enum.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaccion")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction  {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Enumerated(EnumType.STRING)
  private TransactionType transactionType;

  @NotNull
  private Double monto;

  @CreationTimestamp
  @Column(nullable = false,updatable = false)
  private LocalDateTime fechaCreacion;

  // LLAVES FORANEAS
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "producto_origen_id")
  private Product productoOrigen;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "producto_destino_id")
  private Product productoDestino;

}
