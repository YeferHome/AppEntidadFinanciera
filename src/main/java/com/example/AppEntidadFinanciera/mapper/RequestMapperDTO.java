package com.example.AppEntidadFinanciera.mapper;

import com.example.AppEntidadFinanciera.DTO.RequestClienteDTO;
import com.example.AppEntidadFinanciera.models.Cliente;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RequestMapperDTO {

    public static Cliente dtoToRequestDto(RequestClienteDTO requestClienteDTO){
        Cliente cliente = new Cliente();
        cliente.setTipoIdentidad(requestClienteDTO.getTipoIdentidad());
        cliente.setNumIdentidad(requestClienteDTO.getNumIdentidad());
        cliente.setNombres(requestClienteDTO.getNombres());
        cliente.setApellidos(requestClienteDTO.getApellidos());
        cliente.setEdad(requestClienteDTO.getEdad());
        cliente.setCorreo(requestClienteDTO.getCorreo());
        cliente.setFechaNacimiento(requestClienteDTO.getFechaNacimiento());
        cliente.setFechaCreacion(requestClienteDTO.getFechaCreacion());
        cliente.setFechaModificacion(requestClienteDTO.getFechaModificacion());
        return cliente;
    }
}
