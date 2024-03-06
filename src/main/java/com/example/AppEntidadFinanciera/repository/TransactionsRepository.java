package com.example.AppEntidadFinanciera.repository;

import com.example.AppEntidadFinanciera.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions,Long> {
    List<Transactions> findByProduct_Id(Long ProductoId);
}
