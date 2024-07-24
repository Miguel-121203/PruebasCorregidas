package com.example.Final.IService;

import com.example.Final.Entity.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {

    List<Customer> findAll();

    Optional<Customer> findById(Long id);

    Customer save(Customer customer);

    Customer update(Customer customer, Long id);

    void delete(Long id);
}
