package com.example.AppEntidadFinanciera.repository;

import com.example.AppEntidadFinanciera.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository <Cliente, Long> {
}
