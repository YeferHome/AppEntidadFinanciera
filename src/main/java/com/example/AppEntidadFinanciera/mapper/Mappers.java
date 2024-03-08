package com.example.AppEntidadFinanciera.mapper;

import com.example.AppEntidadFinanciera.DTO.RequestClientDTO;
import com.example.AppEntidadFinanciera.DTO.RequestProductDTO;
import com.example.AppEntidadFinanciera.entity.Client;
import com.example.AppEntidadFinanciera.entity.FinancialProduct;
import org.springframework.stereotype.Component;

@Component
public class Mappers {

    public static Client clientToDto(RequestClientDTO requestClientDTO){
        Client client = new Client();
        client.setIdentificationType(requestClientDTO.getIdentificationType());
        client.setIdentityNumber(requestClientDTO.getIdentityNumber());
        client.setFirstName(requestClientDTO.getFirstName());
        client.setLastName(requestClientDTO.getLastName());
        client.setEmail(requestClientDTO.getEmail());
        client.setBirthDate(requestClientDTO.getBirthDate());
        client.setCreationDate(requestClientDTO.getCreationDate());
        return client;
    }

    public static FinancialProduct productToDto(RequestProductDTO requestProductDTO, Client client){
        FinancialProduct financialProduct = new FinancialProduct();
        financialProduct.setAccountType(requestProductDTO.getAccountType());
        financialProduct.setStatus(requestProductDTO.getStatus());
        financialProduct.setBalance(requestProductDTO.getBalance());
        financialProduct.setExemptFromGMF(requestProductDTO.getExemptFromGMF());
        financialProduct.setClient(client);
        return financialProduct;
    }
}
