package com.example.AppEntidadFinanciera.repository;

import com.example.AppEntidadFinanciera.models.ProductoFinanciero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProducFinancieroRepository extends JpaRepository<ProductoFinanciero,Long> {
    List<ProductoFinanciero> findProducFinanByIdCliente(Long Cliente_Id);
}
