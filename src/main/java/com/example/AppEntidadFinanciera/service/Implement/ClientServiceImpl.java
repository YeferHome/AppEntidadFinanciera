package com.example.AppEntidadFinanciera.service.implement;

import com.example.AppEntidadFinanciera.DTO.RequestClientDTO;
import com.example.AppEntidadFinanciera.mapper.RequestMapperDTO;
import com.example.AppEntidadFinanciera.entity.Client;
import com.example.AppEntidadFinanciera.repository.ClientRepository;
import com.example.AppEntidadFinanciera.service.IClientService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Service
public class ClientServiceImpl implements IClientService {

    private final ClientRepository clientRepository;
    private final RequestMapperDTO requestMapperDTO;

    public ClientServiceImpl(ClientRepository clientRepository, RequestMapperDTO requestMapperDTO) {
        this.clientRepository = clientRepository;
        this.requestMapperDTO = requestMapperDTO;
    }

    @Override
    public void createClient(RequestClientDTO requestClientDTO) {

        LocalDate birthDate = requestClientDTO.getBirthDate();
        LocalDate today = LocalDate.now();

        Period age = Period.between(birthDate, today);

        if (age.getYears() < 18) {
            throw new ConstraintViolationException("The client must be at least 18 years old", null);
        }
        Client client = RequestMapperDTO.clientToDto(requestClientDTO);
        client.setBirthDate(LocalDate.from(LocalDateTime.now()));
        clientRepository.save(client);
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
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client != null) {
            Client updatedClient = requestMapperDTO.clientToDto(updateClientDTO);
            client.setIdentificationType(updatedClient.getIdentificationType());
            client.setIdentificationNumber(updatedClient.getIdentificationNumber());
            client.setFirstName(updatedClient.getFirstName());
            client.setLastName(updatedClient.getLastName());
            client.setEmail(updatedClient.getEmail());
            client.setBirthDate(updatedClient.getBirthDate());
            client.setModificationDate(LocalDateTime.now());

            clientRepository.save(client);
        }
    }

    @Override
    public void deleteClient(Long clientId) {
        clientRepository.deleteById(clientId);
    }
}
