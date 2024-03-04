package com.example.AppEntidadFinanciera.service;

import com.example.AppEntidadFinanciera.models.ProductoFinanciero;
import com.example.AppEntidadFinanciera.models.Transacciones;

import java.util.List;

public interface ITransaccionesService {

    void realizarConsignacion(Long Producto_Id, Transacciones consignacion);

    void realizarRetiro (Long Producto_Id, Transacciones retiro);

    void realizarTransferencia (Long CuentaOrigen_Id, Long CuentaDestino_Id, Transacciones transferencia );

    Transacciones findTransaccionesById (Long Transacciones_Id);

    List<ProductoFinanciero> findAllTransaccionesByIdProducFinan(Long Producto_Id);

}
