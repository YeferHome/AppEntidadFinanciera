package com.example.AppEntidadFinanciera.controllers;

import com.example.AppEntidadFinanciera.DTO.RequestClientDTO;
import com.example.AppEntidadFinanciera.entity.Client;
import com.example.AppEntidadFinanciera.service.IClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    void createClient() throws Exception {
        RequestClientDTO requestClientDTO = new RequestClientDTO();

        doNothing().when(clientService).createClient(any(RequestClientDTO.class));

        mockMvc.perform(post("/api/client/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestClientDTO)))
                .andExpect(status().isOk());

        verify(clientService, times(1)).createClient(any(RequestClientDTO.class));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findAllClients() throws Exception {
        List<Client> clients = new ArrayList<>();

        when(clientService.findAllClients()).thenReturn(clients);

        mockMvc.perform(get("/api/client/findAll"))
                .andExpect(status().isOk());

        verify(clientService, times(1)).findAllClients();
    }

    @Test
    void findClientById() throws Exception {
        Long clientId = 1L;
        Client client = new Client();

        when(clientService.findClientById(clientId)).thenReturn(client);

        mockMvc.perform(get("/api/client/{clientId}", clientId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(client.getId()))
                .andExpect(jsonPath("$.identificationType").value(client.getIdentificationType()))
                .andExpect(jsonPath("$.identityNumber").value(client.getIdentityNumber()))
                .andExpect(jsonPath("$.firstName").value(client.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(client.getLastName()))
                .andExpect(jsonPath("$.email").value(client.getEmail()))
                .andExpect(jsonPath("$.birthDate").value(client.getBirthDate()))
                .andExpect(jsonPath("$.creationDate").value(client.getCreationDate()))
                .andExpect(jsonPath("$.modificationDate").value(client.getModificationDate()));

        verify(clientService, times(1)).findClientById(clientId);
    }

    @Test
    void updateClient() throws Exception {
        Long clientId = 1L;
        RequestClientDTO requestClientDTO = new RequestClientDTO();

        doNothing().when(clientService).updateClient(clientId, requestClientDTO);

        mockMvc.perform(put("/api/client/update/{clientId}", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestClientDTO)))
                .andExpect(status().isOk());

        verify(clientService, times(1)).updateClient(clientId, requestClientDTO);
    }

    /*
    @Test
    void deleteClient() {
    }*/
}
