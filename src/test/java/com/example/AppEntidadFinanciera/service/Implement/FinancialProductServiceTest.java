package com.example.AppEntidadFinanciera.service.Implement;

import com.example.AppEntidadFinanciera.DTO.RequestProductDTO;
import com.example.AppEntidadFinanciera.entity.AccountType;
import com.example.AppEntidadFinanciera.entity.Client;
import com.example.AppEntidadFinanciera.entity.FinancialProduct;
import com.example.AppEntidadFinanciera.repository.FinancialProductRepository;
import com.example.AppEntidadFinanciera.service.IClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FinancialProductServiceTest {

    @Mock
    private IClientService clientService;

    @Mock
    private FinancialProductRepository financialProductRepository;

    @InjectMocks
    private FinancialProductServiceImpl financialProductService;


    @Test
    public void testCreateFinancialProduct_Success() {
        RequestProductDTO requestProductDTO = new RequestProductDTO();
        Client client = new Client();
        Long clientId = 123L;
        requestProductDTO.setAccountType(AccountType.AHORRO);
        requestProductDTO.setBalance(BigDecimal.valueOf(1000));

        when(clientService.findClientById(clientId)).thenReturn(client);

        financialProductService.createFinancialProduct(requestProductDTO, clientId);

        verify(clientService, times(1)).findClientById(clientId);
        verify(financialProductRepository, times(1)).save(any(FinancialProduct.class));
    }


    @Test
    public void testCreateFinancialProduct_InvalidClientId() {
        RequestProductDTO requestProductDTO = new RequestProductDTO();
        Long invalidClientId = 456L;

        when(clientService.findClientById(invalidClientId)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            financialProductService.createFinancialProduct(requestProductDTO, invalidClientId);
        });
        assertEquals("No hay cliente creado con ese ID", exception.getMessage());

        verify(financialProductRepository, never()).save(any(FinancialProduct.class));
    }

    @Test
    public void testCreateFinancialProduct_InvalidBalance() {
        RequestProductDTO requestProductDTO = new RequestProductDTO();
        requestProductDTO.setBalance(BigDecimal.valueOf(-100));

        Client client = new Client();
        Long clientId = 123L;

        when(clientService.findClientById(clientId)).thenReturn(client);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            financialProductService.createFinancialProduct(requestProductDTO, clientId);
        });
        assertEquals("El monto no puede ser menor que cero", exception.getMessage());

        verify(financialProductRepository, never()).save(any(FinancialProduct.class));
    }

    @Test
    void findFinancialProductById_ReturnsFinancialProduct() {
        Long productId = 1L;
        FinancialProduct expectedProduct = new FinancialProduct();
        expectedProduct.setId(productId);

        when(financialProductRepository.findById(productId)).thenReturn(java.util.Optional.of(expectedProduct));

        FinancialProduct actualProduct = financialProductService.findFinancialProductById(productId);

        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    void findFinancialProductById_ReturnsNullWhenNotFound() {
        Long productId = 1L;
        when(financialProductRepository.findById(productId)).thenReturn(java.util.Optional.empty());

        FinancialProduct actualProduct = financialProductService.findFinancialProductById(productId);

        assertEquals(null, actualProduct);
    }

/*
    @Test
    void findFinancialProductsByClientId() {
    }

    @Test
    void updateFinancialProduct() {
    }

    @Test
    void deleteFinancialProduct() {
    }*/
}