package com.example.Final.IRepository;

import com.example.Final.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionService extends JpaRepository<Transaction,Long> {
}
