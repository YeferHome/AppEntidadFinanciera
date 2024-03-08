package com.example.AppEntidadFinanciera.service.Implement;

import com.example.AppEntidadFinanciera.DTO.RequestClientDTO;
import com.example.AppEntidadFinanciera.entity.Client;
import com.example.AppEntidadFinanciera.entity.FinancialProduct;
import com.example.AppEntidadFinanciera.repository.ClientRepository;
import com.example.AppEntidadFinanciera.repository.FinancialProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private FinancialProductRepository financialProductRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createClient_shouldCreateClientIfAgeIsAbove18() {
        RequestClientDTO requestClientDTO = new RequestClientDTO();
        requestClientDTO.setBirthDate(LocalDate.now().minusYears(20)); // Age above 18

        clientService.createClient(requestClientDTO);

        verify(clientRepository).save(any(Client.class));
    }

    @Test
    void createClient_shouldThrowExceptionIfAgeIsBelow18() {
        RequestClientDTO requestClientDTO = new RequestClientDTO();
        requestClientDTO.setBirthDate(LocalDate.now().minusYears(15)); // Age below 18

        assertThrows(ConstraintViolationException.class, () -> clientService.createClient(requestClientDTO));
        verify(clientRepository, never()).save(any(Client.class));
    }


    @Test
    void findClientById() {
        Client fakeClient = new Client();
        fakeClient.setId(1L);
        fakeClient.setIdentificationType("CC");
        fakeClient.setIdentityNumber(12345678);
        fakeClient.setFirstName("Yefer");
        fakeClient.setLastName("rojas");
        fakeClient.setEmail("yefer@example.com");
        fakeClient.setBirthDate(LocalDate.of(2002, 1, 16));
        fakeClient.setCreationDate(LocalDateTime.now());
        fakeClient.setModificationDate(LocalDateTime.now());

        when(clientRepository.findById(1L)).thenReturn(Optional.of(fakeClient));

        Client foundClient = clientService.findClientById(1L);

        verify(clientRepository, times(1)).findById(1L);

        assertNotNull(foundClient);
        assertEquals(1L, foundClient.getId());
        assertEquals("CC", foundClient.getIdentificationType());
        assertEquals(12345678, foundClient.getIdentityNumber());
        assertEquals("Yefer", foundClient.getFirstName());
        assertEquals("rojas", foundClient.getLastName());
        assertEquals("yefer@example.com", foundClient.getEmail());
        assertEquals(LocalDate.of(2002, 1, 16), foundClient.getBirthDate());
        assertNotNull(foundClient.getCreationDate());
        assertNotNull(foundClient.getModificationDate());
    }


    @Test
    void findAllClients() {
        Client client1 = new Client();
        client1.setId(1L);
        client1.setIdentificationType("CC");
        client1.setIdentityNumber(12345678);
        client1.setFirstName("Yefer");
        client1.setLastName("Home");
        client1.setEmail("xxxx@example.com");
        client1.setBirthDate(LocalDate.of(1990, 5, 15));
        client1.setCreationDate(LocalDateTime.now());
        client1.setModificationDate(LocalDateTime.now());

        Client client2 = new Client();
        client2.setId(2L);
        client2.setIdentificationType("CC EXTRANJERA");
        client2.setIdentityNumber(87654321);
        client2.setFirstName("Andres");
        client2.setLastName("rojas");
        client2.setEmail("xxxxxx@example.com");
        client2.setBirthDate(LocalDate.of(1995, 8, 20));
        client2.setCreationDate(LocalDateTime.now());
        client2.setModificationDate(LocalDateTime.now());

        List<Client> fakeClients = Arrays.asList(client1, client2);

        when(clientRepository.findAll()).thenReturn(fakeClients);

        List<Client> foundClients = clientService.findAllClients();

        verify(clientRepository, times(1)).findAll();

        assertFalse(foundClients.isEmpty());

        assertEquals(fakeClients.size(), foundClients.size());

        for (int i = 0; i < fakeClients.size(); i++) {
            Client expectedClient = fakeClients.get(i);
            Client actualClient = foundClients.get(i);

            assertEquals(expectedClient.getId(), actualClient.getId());
            assertEquals(expectedClient.getIdentificationType(), actualClient.getIdentificationType());
            assertEquals(expectedClient.getIdentityNumber(), actualClient.getIdentityNumber());
            assertEquals(expectedClient.getFirstName(), actualClient.getFirstName());
            assertEquals(expectedClient.getLastName(), actualClient.getLastName());
            assertEquals(expectedClient.getEmail(), actualClient.getEmail());
            assertEquals(expectedClient.getBirthDate(), actualClient.getBirthDate());
            assertEquals(expectedClient.getCreationDate(), actualClient.getCreationDate());
            assertEquals(expectedClient.getModificationDate(), actualClient.getModificationDate());
        }
    }


    @Test
    void updateClient() {
        Client existingClient = new Client();
        existingClient.setId(1L);
        existingClient.setIdentificationType("DNI");
        existingClient.setIdentityNumber(12345678);
        existingClient.setFirstName("John");
        existingClient.setLastName("Doe");
        existingClient.setEmail("john.doe@example.com");
        existingClient.setBirthDate(LocalDate.of(1990, 5, 15));
        existingClient.setCreationDate(LocalDateTime.now().minusDays(1));
        existingClient.setModificationDate(LocalDateTime.now().minusDays(1));

        //los datos se actalizan
        RequestClientDTO updateClientDTO = new RequestClientDTO();
        updateClientDTO.setIdentificationType("NIE");
        updateClientDTO.setIdentityNumber(87654321);
        updateClientDTO.setFirstName("Jane");
        updateClientDTO.setLastName("Doe");
        updateClientDTO.setEmail("jane.doe@example.com");
        updateClientDTO.setBirthDate(LocalDate.of(1995, 8, 20));

        when(clientRepository.findById(1L)).thenReturn(Optional.of(existingClient));

        clientService.updateClient(1L, updateClientDTO);

        verify(clientRepository, times(1)).findById(1L);

        verify(clientRepository, times(1)).save(existingClient);

        assertEquals("NIE", existingClient.getIdentificationType());
        assertEquals(87654321, existingClient.getIdentityNumber());
        assertEquals("Jane", existingClient.getFirstName());
        assertEquals("Doe", existingClient.getLastName());
        assertEquals("jane.doe@example.com", existingClient.getEmail());
        assertEquals(LocalDate.of(1995, 8, 20), existingClient.getBirthDate());
        assertNotNull(existingClient.getCreationDate());
        assertNotNull(existingClient.getModificationDate());
    }


    @Test
    void deleteClient_shouldDeleteClientIfNoFinancialProductsLinked() {
        Client clientToDelete = new Client();
        clientToDelete.setId(1L);

        when(financialProductRepository.findByClientId(1L)).thenReturn(Collections.emptyList());

        clientService.deleteClient(1L);

        verify(financialProductRepository, times(1)).findByClientId(1L);

        verify(clientRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteClient_shouldThrowExceptionIfFinancialProductsLinked() {
        Long clientId = 1L;
        when(financialProductRepository.findByClientId(clientId)).thenReturn(Arrays.asList(new FinancialProduct()));

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            clientService.deleteClient(clientId);
        });

        assertEquals("El cliente no puede ser eliminado porque tiene productos financieros vinculados", exception.getMessage());
    }


}
