package com.example.Final.Dto;

import lombok.Data;

@Data
public class TransactionRequestDto {
  private Long productoId;
  private Long productoOrigenId;
  private Long productoDestinoId;
  private Double monto;
}
