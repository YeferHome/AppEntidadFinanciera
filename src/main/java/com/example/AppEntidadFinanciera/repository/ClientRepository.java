package com.example.AppEntidadFinanciera.repository;

import com.example.AppEntidadFinanciera.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends JpaRepository <Client, Long> {
}
