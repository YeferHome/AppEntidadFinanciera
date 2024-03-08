package com.example.AppEntidadFinanciera.service.Implement;

import com.example.AppEntidadFinanciera.DTO.RequestProductDTO;
import com.example.AppEntidadFinanciera.entity.AccountType;
import com.example.AppEntidadFinanciera.entity.Client;
import com.example.AppEntidadFinanciera.entity.FinancialProduct;
import com.example.AppEntidadFinanciera.entity.Status;
import com.example.AppEntidadFinanciera.repository.FinancialProductRepository;
import com.example.AppEntidadFinanciera.service.IClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    @Test
    void findFinancialProductsByClientId_ReturnsListOfFinancialProducts() {
        Long clientId = 1L;
        List<FinancialProduct> expectedProducts = new ArrayList<>();
        expectedProducts.add(new FinancialProduct());
        expectedProducts.add(new FinancialProduct());
        when(financialProductRepository.findByClientId(clientId)).thenReturn(expectedProducts);

        List<FinancialProduct> actualProducts = financialProductService.findFinancialProductsByClientId(clientId);

        assertEquals(expectedProducts.size(), actualProducts.size());
        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    void findFinancialProductsByClientId_ReturnsEmptyListWhenNoProductsFound() {
        Long clientId = 1L;
        List<FinancialProduct> expectedProducts = new ArrayList<>();
        when(financialProductRepository.findByClientId(clientId)).thenReturn(expectedProducts);

        List<FinancialProduct> actualProducts = financialProductService.findFinancialProductsByClientId(clientId);

        assertEquals(expectedProducts.size(), actualProducts.size());
        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    void updateFinancialProduct_ProductNotFound() {
        Long productId = 1L;
        RequestProductDTO updatedProductDTO = new RequestProductDTO();

        when(financialProductRepository.findById(productId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            financialProductService.updateFinancialProduct(productId, updatedProductDTO);
        });
        assertEquals("No hay una cuenta para actualizar", exception.getMessage());
        verify(financialProductRepository, never()).save(any());
    }


    @Test
    void updateFinancialProduct_SuccessfullyUpdated() {
        Long productId = 1L;
        RequestProductDTO updatedProductDTO = new RequestProductDTO();
        updatedProductDTO.setAccountType(AccountType.AHORRO);
        updatedProductDTO.setStatus(Status.CANCELADA);
        updatedProductDTO.setBalance(BigDecimal.ZERO);
        updatedProductDTO.setExemptFromGMF(true);

        FinancialProduct existingProduct = new FinancialProduct();
        existingProduct.setId(productId);
        existingProduct.setBalance(BigDecimal.ZERO);
        existingProduct.setStatus(Status.ACTIVA);
        existingProduct.setModificationDate(LocalDateTime.now().minusDays(1));

        when(financialProductRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        financialProductService.updateFinancialProduct(productId, updatedProductDTO);

        assertEquals(updatedProductDTO.getAccountType(), existingProduct.getAccountType());
        assertEquals(updatedProductDTO.getStatus(), existingProduct.getStatus());
        assertEquals(updatedProductDTO.getBalance(), existingProduct.getBalance());
        assertEquals(updatedProductDTO.getExemptFromGMF(), existingProduct.getExemptFromGMF());
        assertTrue(existingProduct.getModificationDate().isAfter(LocalDateTime.now().minusSeconds(1)));
        verify(financialProductRepository, times(1)).save(existingProduct);
    }



    @Test
    void deleteFinancialProduct() {
        Long productId = 1L;

        financialProductService.deleteFinancialProduct(productId);

        verify(financialProductRepository, times(1)).deleteById(productId);
    }

}