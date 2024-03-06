package com.example.AppEntidadFinanciera.service;

import com.example.AppEntidadFinanciera.DTO.RequestClientDTO;
import com.example.AppEntidadFinanciera.entity.Client;

import java.util.List;

public interface IClientService {

    void createClient(RequestClientDTO requestClientDTO);

    Client findClientById(Long clientId);

    List<Client> findAllClients();

    void updateClient(Long clientId, RequestClientDTO updateClientDTO);

    void deleteClient(Long clientId);

}