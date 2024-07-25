package com.example.Final.Service;

import com.example.Final.Entity.Enum.AccountState;
import com.example.Final.Entity.Enum.AccountType;
import com.example.Final.Entity.Product;
import com.example.Final.IRepository.IProductRepository;
import com.example.Final.IService.IProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

  @Autowired
  private IProductRepository repository;

  @Override
  public List<Product> findAll() {
    return repository.findAll();
  }

  @Override
  public Optional<Product> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  public Product  save(@Valid Product product) {
    if (product.getAccountType() == AccountType.AHORRO && product.getSaldo() < 0 ){
      throw new IllegalArgumentException("La cuenta de ahorro no puede tener un saldo menor a 0");
    }

    product.setNumeroCuenta(generarNumeroCuenta(product.getAccountType()));

    if (product.getAccountType() == AccountType.AHORRO){
      product.setAccountState(AccountState.ACTIVA);
    }

    return repository.save(product);
  }

  @Override
  public Product update(@Valid Product product, Long id) {
    Optional<Product> productOptional = repository.findById(id);
    if (productOptional.isPresent()){
      Product products = productOptional.get();
      products.setAccountType(products.getAccountType());
      products.setAccountState(products.getAccountState());
      products.setSaldo(products.getSaldo());
      products.setGmf(products.getGmf());
      products.setFechaModificacion(products.getFechaModificacion());
      return repository.save(product);
    }else {
      throw new RuntimeException("Producto no encontrado con el id: " + id);
    }
  }

  @Override
  public void delete(Long id) {
    Optional<Product> product = repository.findById(id);
    if (product.isPresent()){
      repository.deleteById(id);
    }else {
      throw new RuntimeException("Producto no encontrado con el id: "+ id);
    }
  }

  @Override
  public void activateProduct(Long id) {

    Optional<Product> productOptional = repository.findById(id);

    if (productOptional.isPresent()){
      Product product = productOptional.get();

      product.setAccountState(AccountState.ACTIVA);
      product.setFechaModificacion(LocalDateTime.now());
      repository.save(product);
    }else {
      throw new EntityNotFoundException("Producto no encontrado con el id: "+ id);
    }

  }

  @Override
  public void desactivateProduct(Long id) {
    Optional<Product> productOptional = repository.findById(id);

    if (productOptional.isPresent()){
      Product product = productOptional.get();

      product.setAccountState(AccountState.INACTIVA);
      product.setFechaModificacion(LocalDateTime.now());
      repository.save(product);
    }else {
      throw new EntityNotFoundException("Producto no encontrado con el id: "+ id);
    }

  }

  @Override
  public void cancelateProduct(Long id) {
    Optional<Product> productOptional = repository.findById(id);

    if (productOptional.isPresent()){
      Product product = productOptional.get();
      if (product.getSaldo() != 0){
        throw new IllegalArgumentException("No se pude cancelar una cuenta con saldo diferente a 0 ");
      }

      product.setAccountState(AccountState.CANCELADA);
      product.setFechaModificacion(LocalDateTime.now());
      repository.save(product);
    }else {
      throw new EntityNotFoundException("Producto no encontrado con el id: "+ id);
    }

  }


  private String generarNumeroCuenta(AccountType accountType) {
    String prefijo = accountType == AccountType.AHORRO ? "53" : "33";
    return prefijo + String.format("%08d", (int) (Math.random() * 100000000));
  }
}
