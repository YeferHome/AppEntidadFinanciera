package com.example.AppEntidadFinanciera.repository;

import com.example.AppEntidadFinanciera.entity.FinancialProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialProductRepository extends JpaRepository<FinancialProduct, Long> {

    List<FinancialProduct> findByClientId(Long clientId);
}
