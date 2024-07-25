package com.example.Final.Service;

import com.example.Final.Dto.TransactionRequestDto;
import com.example.Final.Entity.Enum.TransactionType;
import com.example.Final.Entity.Product;
import com.example.Final.Entity.Transaction;
import com.example.Final.IRepository.IProductRepository;
import com.example.Final.IRepository.ITransactionRepository;
import com.example.Final.IService.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {

  @Autowired
  private IProductRepository productRepository;

  @Autowired
  private ITransactionRepository transactionRepository;


  @Override
  @Transactional
  public void consignacion(TransactionRequestDto transactionRequestDto) {
    Optional<Product> productOptional = productRepository.findById(transactionRequestDto.getProductoOrigenId());
    if (productOptional.isPresent()){
      Product product = productOptional.get();
      product.setSaldo(product.getSaldo() + transactionRequestDto.getMonto()) ;
      productRepository.save(product);

      Transaction transaction = new Transaction();
      transaction.setTransactionType(TransactionType.CONSIGNACION);
      transaction.setMonto(transactionRequestDto.getMonto());
      transaction.setProductoOrigen(product);
      transactionRepository.save(transaction);
    }else
      throw new RuntimeException("Cuenta no encontrada");
  }

  @Override
  @Transactional
  public void retiro(TransactionRequestDto transactionRequestDto) {
    Optional<Product> productOptional = productRepository.findById(transactionRequestDto.getProductoOrigenId());
    if (productOptional.isPresent()){
      Product product = productOptional.get();
      if (product.getSaldo() >= transactionRequestDto.getMonto()){
        product.setSaldo(product.getSaldo() - transactionRequestDto.getMonto());
        productRepository.save(product);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.RETIRO);
        transaction.setMonto(transactionRequestDto.getMonto());
        transaction.setProductoOrigen(product);
        transactionRepository.save(transaction);
      }else
        throw new RuntimeException("Saldo insuficiente por favor consigne mas saldo a su cuenta");
    }else
      throw new RuntimeException("Cuenta no encontrada");
  }

  @Override
  @Transactional
  public void transferencia(TransactionRequestDto transactionRequestDto) {
    Optional<Product> productOrigenOptional = productRepository.findById(transactionRequestDto.getProductoOrigenId());
    Optional<Product> productDestinoOptional = productRepository.findById(transactionRequestDto.getProductoDestinoId());

    if (productOrigenOptional.isPresent() && productDestinoOptional.isPresent()){
      Product productOrigen = productOrigenOptional.get();
      Product productDestino = productDestinoOptional.get();

      if (productOrigen.getSaldo() >= transactionRequestDto.getMonto()){
        // DESCONTAMOS EL MONTO DE LA CUENTA DE ORIGEN
        productOrigen.setSaldo(productOrigen.getSaldo() - transactionRequestDto.getMonto());
        productRepository.save(productOrigen);

        // AGREGAMOS EL MONTO A LA CUENTA DESTINO
        productDestino.setSaldo(productDestino.getSaldo() + transactionRequestDto.getMonto());
        productRepository.save(productDestino);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.CONSIGNACION);
        transaction.setMonto(transactionRequestDto.getMonto());
        transaction.setProductoOrigen(productOrigen);
        transaction.setProductoDestino(productDestino);
        transactionRepository.save(transaction);


      }else
        throw new RuntimeException("Salfo insuficinete en la cuenta de origen");
    }else
      throw new RuntimeException("Producto origen o destino no fue encontrado");

  }
}
