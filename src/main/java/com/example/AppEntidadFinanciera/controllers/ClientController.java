package com.example.AppEntidadFinanciera.controllers;

import com.example.AppEntidadFinanciera.DTO.RequestClientDTO;
import com.example.AppEntidadFinanciera.entity.Client;
import com.example.AppEntidadFinanciera.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @PostMapping("/create")
    public void createClient(@RequestBody RequestClientDTO requestClientDTO){
        clientService.createClient(requestClientDTO);
    }

    @GetMapping("/findAll")
    public List<Client> findAllClients(){
        return clientService.findAllClients();
    }

    @GetMapping("/{clientId}")
    public Client findClientById(@PathVariable Long clientId){
        return clientService.findClientById(clientId);
    }

    @PutMapping("/update/{clientId}")
    public void updateClient(@PathVariable Long clientId, @RequestBody RequestClientDTO requestClienteDTO){
        clientService.updateClient(clientId, requestClienteDTO);
    }

    @DeleteMapping("/delete/{clientId}")
    public void deleteClient(@PathVariable Long clientId){
        clientService.deleteClient(clientId);
    }
}
