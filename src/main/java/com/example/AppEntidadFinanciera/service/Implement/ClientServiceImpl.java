package com.example.AppEntidadFinanciera.service.Implement;

import com.example.AppEntidadFinanciera.DTO.RequestClientDTO;
import com.example.AppEntidadFinanciera.entity.Client;
import com.example.AppEntidadFinanciera.entity.FinancialProduct;
import com.example.AppEntidadFinanciera.mapper.Mappers;
import com.example.AppEntidadFinanciera.repository.ClientRepository;
import com.example.AppEntidadFinanciera.repository.FinancialProductRepository;
import com.example.AppEntidadFinanciera.service.IClientService;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClientServiceImpl implements IClientService {

    private final ClientRepository clientRepository;
    private final Mappers mappers;
    private final FinancialProductRepository financialProductRepository;

    public ClientServiceImpl(ClientRepository clientRepository, Mappers mappers, FinancialProductRepository financialProductRepository) {
        this.clientRepository = clientRepository;
        this.mappers = mappers;
        this.financialProductRepository = financialProductRepository;
    }

    @Override
    public void createClient(RequestClientDTO requestClientDTO) {
        try {
            LocalDate birthDate = requestClientDTO.getBirthDate();
            LocalDate today = LocalDate.now();

            Period age = Period.between(birthDate, today);

            if (age.getYears() < 18) {
                throw new javax.validation.ConstraintViolationException("El cliente debe tener al menos 18 años de edad", null);
            }
            Client client = Mappers.clientToDto(requestClientDTO);
            clientRepository.save(client);
        } catch (javax.validation.ConstraintViolationException captuex) {
            throw new javax.validation.ConstraintViolationException("El cliente debe tener al menos 18 años de edad", null);
        }
    }


    @Override
    public Client findClientById(Long clientId) {
        return clientRepository.findById(clientId).orElse(null);
    }

    @Override
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }


    @Override
    public void updateClient(Long clientId, RequestClientDTO updateClientDTO) {
        try {
            Optional<Client> optionalClient = clientRepository.findById(clientId);
            if (optionalClient.isPresent()) {
                Client client = optionalClient.get();
                Client updatedClient = mappers.clientToDto(updateClientDTO);

                // Actualizar los campos del cliente
                client.setIdentificationType(updatedClient.getIdentificationType());
                client.setIdentityNumber(updatedClient.getIdentityNumber());
                client.setFirstName(updatedClient.getFirstName());
                client.setLastName(updatedClient.getLastName());
                client.setEmail(updatedClient.getEmail());
                client.setBirthDate(updatedClient.getBirthDate());
                client.setModificationDate(LocalDateTime.now());

                // Guardar el cliente actualizado
                clientRepository.save(client);
            } else {
                throw new NoSuchElementException("El cliente con ID " + clientId + " no existe");
            }
        } catch (DataAccessException ex) {
            throw new RuntimeException("Error al actualizar el cliente: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void deleteClient(Long clientId) {
        try {
            List<FinancialProduct> financialProducts = financialProductRepository.findByClientId(clientId);
            if (!financialProducts.isEmpty()) {
                throw new DataIntegrityViolationException("El cliente no puede ser eliminado porque tiene productos financieros vinculados");
            }

            clientRepository.deleteById(clientId);
        } catch (DataIntegrityViolationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Error al intentar eliminar al cliente: " + ex.getMessage(), ex);
        }
    }



}
