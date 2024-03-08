package com.example.AppEntidadFinanciera.service;

import com.example.AppEntidadFinanciera.DTO.RequestProductDTO;
import com.example.AppEntidadFinanciera.entity.FinancialProduct;

import java.util.List;

public interface IFinancialProductService {

    void createFinancialProduct(RequestProductDTO requestProductDTO, Long clientId);

    FinancialProduct findFinancialProductById(Long productId);

    List<FinancialProduct> findFinancialProductsByClientId(Long clientId);

    void updateFinancialProduct(Long productId, RequestProductDTO updateProductDTO);

    void deleteFinancialProduct(Long productId);

}




