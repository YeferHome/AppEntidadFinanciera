package com.example.AppEntidadFinanciera.DTO;

import com.example.AppEntidadFinanciera.models.Estado;
import com.example.AppEntidadFinanciera.models.TipoCuenta;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class RequestProductoDTO {

    private TipoCuenta tipoCuenta;
    private String numCuenta;
    private Estado estado;
    private BigDecimal saldo;
    private Boolean exentaGMF;
}
