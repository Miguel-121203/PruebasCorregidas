package com.example.Final.Service;

import com.example.Final.Entity.Customer;
import com.example.Final.IRepository.ICustomerRepository;
import com.example.Final.IService.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {

  @Autowired
  private ICustomerRepository repository;


  @Override
  public List<Customer> findAll() {
    return repository.findAll();
  }

  @Override
  public Optional<Customer> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  public Customer save(@Valid Customer customer) {
    if (!isOfAge(customer.getFechaNacimiento())){
      throw new IllegalArgumentException("El cliente debe ser mayor de edad (18)");
    }
    customer.setFechaCreacion(LocalDateTime.now());
    customer.setFechaModificacion(LocalDateTime.now());
    return repository.save(customer);
  }

  @Override
  public Customer update(@Valid  Customer customer, Long id) {
    Optional<Customer> optionalCustomer = repository.findById(id);

    if (optionalCustomer.isPresent()){
      Customer customer1 = optionalCustomer.get();
      customer1.setTipoId(customer.getTipoId());
      customer1.setNumId(customer.getNumId());
      customer1.setNombre(customer.getNombre());
      customer1.setApellido(customer.getApellido());
      customer1.setEmail(customer.getEmail());
      customer1.setFechaNacimiento(customer.getFechaNacimiento());
      customer1.setFechaModificacion(LocalDateTime.now());
      return repository.save(customer);
    }else {
      throw new RuntimeException("Cliente no encontrado con el id: " + id);
    }
  }

  @Override
  public void delete(Long id) {
    Optional<Customer> customer = repository.findById(id);

    if (customer.isPresent()){
      repository.deleteById(id);
    }else {
      throw new RuntimeException("Cliente no encontrado con el id: "+ id);
    }

  }

  //METODO DE VALIDACION PARA QUE EL CLIENTE SEA MAYOR DE EDAD
  private boolean isOfAge(LocalDate fechaNacimiento) {
    return Period.between(fechaNacimiento, LocalDate.now()).getYears() >= 18;
  }
}
