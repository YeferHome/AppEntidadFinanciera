package com.example.AppEntidadFinanciera.mapper;

import com.example.AppEntidadFinanciera.DTO.RequestClienteDTO;
import com.example.AppEntidadFinanciera.DTO.RequestProductoDTO;
import com.example.AppEntidadFinanciera.models.Cliente;
import com.example.AppEntidadFinanciera.models.ProductoFinanciero;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RequestMapperDTO {

    public static Cliente clienteToDto(RequestClienteDTO requestClienteDTO){
        Cliente cliente = new Cliente();
        cliente.setTipoIdentidad(requestClienteDTO.getTipoIdentidad());
        cliente.setNumIdentidad(requestClienteDTO.getNumIdentidad());
        cliente.setNombres(requestClienteDTO.getNombres());
        cliente.setApellidos(requestClienteDTO.getApellidos());
        cliente.setCorreo(requestClienteDTO.getCorreo());
        cliente.setFechaNacimiento(requestClienteDTO.getFechaNacimiento());
        return cliente;
    }
    public static ProductoFinanciero productoToDto(RequestProductoDTO requestProductoDTO){
        ProductoFinanciero productoFinanciero = new ProductoFinanciero();
        productoFinanciero.setTipoCuenta(requestProductoDTO.getTipoCuenta());
        productoFinanciero.setNumCuenta(requestProductoDTO.getNumCuenta());
        productoFinanciero.setEstado(requestProductoDTO.getEstado());
        productoFinanciero.setSaldo(requestProductoDTO.getSaldo());
        productoFinanciero.setExentaGMF(requestProductoDTO.getExentaGMF());
        return productoFinanciero;
    }

}
