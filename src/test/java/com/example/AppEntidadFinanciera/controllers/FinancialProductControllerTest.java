package com.example.AppEntidadFinanciera.controllers;

import com.example.AppEntidadFinanciera.DTO.RequestProductDTO;
import com.example.AppEntidadFinanciera.entity.AccountType;
import com.example.AppEntidadFinanciera.entity.Client;
import com.example.AppEntidadFinanciera.entity.FinancialProduct;
import com.example.AppEntidadFinanciera.entity.Status;
import com.example.AppEntidadFinanciera.service.IClientService;
import com.example.AppEntidadFinanciera.service.IFinancialProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FinancialProductControllerTest {

    @Mock
    private IFinancialProductService financialProductService;

    @Mock
    private IClientService clientService;

    @InjectMocks
    private FinancialProductController financialProductController;

    @Test
    public void testCreateFinancialProduct() {
        Long clientId = 1L;
        RequestProductDTO requestProductDTO = new RequestProductDTO();

        doNothing().when(financialProductService).createFinancialProduct(requestProductDTO, clientId);

        financialProductController.createFinancialProduct(requestProductDTO, clientId);

        verify(financialProductService, times(1)).createFinancialProduct(requestProductDTO, clientId);
    }


    @Test
    void findFinancialProductsByClientId() {
        Long clientId = 1L;

        List<FinancialProduct> financialProducts = new ArrayList<>();
        financialProducts.add(new FinancialProduct(1L, AccountType.AHORRO, "123456789", Status.ACTIVA, BigDecimal.valueOf(1000), true, LocalDateTime.now(), LocalDateTime.now(), new Client()));
        financialProducts.add(new FinancialProduct(2L, AccountType.CORRIENTE, "987654321", Status.ACTIVA, BigDecimal.valueOf(2000), false, LocalDateTime.now(), LocalDateTime.now(), new Client()));
        financialProducts.add(new FinancialProduct(3L, AccountType.AHORRO, "555555555", Status.ACTIVA, BigDecimal.valueOf(5000), true, LocalDateTime.now(), LocalDateTime.now(), new Client()));

        when(financialProductService.findFinancialProductsByClientId(clientId)).thenReturn(financialProducts);

        List<FinancialProduct> result = financialProductController.findFinancialProductsByClientId(clientId);

        assertEquals(financialProducts.size(), result.size());
        for (int i = 0; i < financialProducts.size(); i++) {
            assertEquals(financialProducts.get(i), result.get(i));
        }
    }

    @Test
    void findFinancialProductById() {
        Long productId = 1L;
        FinancialProduct expectedProduct = new FinancialProduct();
        when(financialProductService.findFinancialProductById(productId)).thenReturn(expectedProduct);

        FinancialProduct result = financialProductController.findFinancialProductById(productId);

        assertEquals(expectedProduct, result);
    }


    @Test
    void updateFinancialProduct() {
        Long productId = 1L;
        RequestProductDTO updateProductDTO = new RequestProductDTO();

        doNothing().when(financialProductService).updateFinancialProduct(productId, updateProductDTO);

        financialProductController.updateFinancialProduct(productId, updateProductDTO);

        verify(financialProductService, times(1)).updateFinancialProduct(productId, updateProductDTO);
    }


    @Test
    void deleteFinancialProduct() {
        Long productId = 1L;

        doNothing().when(financialProductService).deleteFinancialProduct(productId);

        financialProductController.deleteFinancialProduct(productId);

        verify(financialProductService, times(1)).deleteFinancialProduct(productId);
    }

}