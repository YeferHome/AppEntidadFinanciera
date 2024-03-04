package com.example.AppEntidadFinanciera.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoFinanciero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private enum tipocuenta{cuenta_corriente,cuenta_ahorros}
    private String numcuenta;
    private enum estado{Activa,Inactiva,Cancelada}
    private BigDecimal saldo;
    private Boolean exentaGMF;
    private LocalDateTime fechacreacion;
    private LocalDateTime fechamodificacion;
}
