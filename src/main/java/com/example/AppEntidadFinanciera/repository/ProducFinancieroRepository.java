package com.example.AppEntidadFinanciera.repository;

import com.example.AppEntidadFinanciera.models.ProductoFinanciero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducFinancieroRepository extends JpaRepository<ProductoFinanciero,Long> {
}
