package com.example.AppEntidadFinanciera.controllers;


import com.example.AppEntidadFinanciera.DTO.RequestClienteDTO;
import com.example.AppEntidadFinanciera.models.Cliente;
import com.example.AppEntidadFinanciera.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    @Autowired
    private IClienteService ClienteService;

    @PostMapping("/createcliente")
    public void createCliente(@RequestBody RequestClienteDTO requestClienteDTO){
        ClienteService.createCliente(requestClienteDTO);
    }
    @GetMapping("/findallclientes")
    public List<Cliente> findAllClientes(){
        return ClienteService.findAllClientes();
    }
    @GetMapping("/{cliente_Id}")
    public Cliente findClienteById(@PathVariable Long cliente_Id){
        return ClienteService.findClienteById(cliente_Id);
    }
    @PutMapping("/update/{cliente_Id}")
    public void updateCliente(@PathVariable Long cliente_Id, @RequestBody RequestClienteDTO requestClienteDTO){
        ClienteService.updateCliente(cliente_Id, requestClienteDTO);
    }
    @DeleteMapping("/delete/{Cliente_Id}")
    public void deleteCliente(@PathVariable Long Cliente_Id){
        ClienteService.deleteCliente(Cliente_Id);
    }
}
