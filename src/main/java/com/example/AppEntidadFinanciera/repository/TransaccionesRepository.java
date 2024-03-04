package com.example.AppEntidadFinanciera.repository;

import com.example.AppEntidadFinanciera.models.Transacciones;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionesRepository extends JpaRepository<Transacciones,Long> {
}
