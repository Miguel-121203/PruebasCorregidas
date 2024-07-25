package com.example.Final.Controller;

import com.example.Final.Dto.TransactionRequestDto;
import com.example.Final.IService.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
public class transactionController {

  @Autowired
  private ITransactionService service;

  @PostMapping("/consignacion")
  public ResponseEntity<String> consignacion(@RequestBody TransactionRequestDto transactionRequestDto) {
    service.consignacion(transactionRequestDto);
    return ResponseEntity.ok("Consignación realizada exitosamente");
  }

  @PostMapping("/retiro")
  public ResponseEntity<String> retiro(@RequestBody TransactionRequestDto transactionRequestDto) {
    service.retiro(transactionRequestDto);
    return ResponseEntity.ok("Retiro realizado exitosamente");
  }

  @PostMapping("/transferencia")
  public ResponseEntity<String> transferencia(@RequestBody TransactionRequestDto transactionRequestDto) {
    service.transferencia(transactionRequestDto);
    return ResponseEntity.ok("Transferencia realizada exitosamente");
  }
}
