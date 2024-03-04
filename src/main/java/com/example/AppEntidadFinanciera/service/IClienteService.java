package com.example.AppEntidadFinanciera.service;

import com.example.AppEntidadFinanciera.DTO.RequestClienteDTO;
import com.example.AppEntidadFinanciera.models.Cliente;

import java.util.List;

public interface IClienteService {

    void createCliente(RequestClienteDTO requestClienteDTO);

    Cliente findClienteById (Long cliente_Id);

    List<Cliente> findAllClientes();

    void updateCliente(Long cliente_Id, RequestClienteDTO updateClienteDTO);

    void deleteCliente(Long cliente_Id);

}
