package com.example.AppEntidadFinanciera.service;

import com.example.AppEntidadFinanciera.models.Cliente;

import java.util.List;

public interface IClienteService {

    void createCliente(Cliente cliente);

    Cliente findClienteById (Long cliente_Id);

    List<Cliente> findAllClientes();

    void updateCliente(Long producto_Id, Cliente cliente);

    void deleteCliente(Long cliente_Id);


}
