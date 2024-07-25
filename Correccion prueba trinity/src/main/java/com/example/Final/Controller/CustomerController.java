package com.example.Final.Controller;

import com.example.Final.Entity.Customer;
import com.example.Final.IService.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

  @Autowired
  private ICustomerService service;

  @GetMapping
  public ResponseEntity<List<Customer>> findAll(){
    List<Customer> customers = service.findAll();
    return ResponseEntity.ok(customers);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Customer> findById( @PathVariable Long id){
    return service.findById(id).map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Customer> save(@Valid @RequestBody Customer customer){
    Customer newCustomer = service.save(customer);
    return ResponseEntity.ok(newCustomer);
  }

  @PutMapping("{id}")
  public ResponseEntity<Customer> update(@PathVariable Long id, @Valid @RequestBody Customer customer) {
    Customer updatedCustomer = service.update(customer, id);
    return ResponseEntity.ok(updatedCustomer);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }



}
