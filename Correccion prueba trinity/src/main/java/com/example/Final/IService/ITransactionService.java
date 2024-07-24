package com.example.Final.IService;

import com.example.Final.Dto.TransactionRequestDto;

public interface ITransactionService {

  void consignacion(TransactionRequestDto transactionRequestDto);

  void retiro(TransactionRequestDto transactionRequestDto);

  void transferencia(TransactionRequestDto transactionRequestDto);

}
