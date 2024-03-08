package com.example.AppEntidadFinanciera.repository;

import com.example.AppEntidadFinanciera.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions,Long> {
}
