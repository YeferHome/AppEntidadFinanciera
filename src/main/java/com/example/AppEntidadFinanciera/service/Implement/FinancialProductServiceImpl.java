package com.example.AppEntidadFinanciera.service.implement;

import com.example.AppEntidadFinanciera.DTO.RequestProductDTO;
import com.example.AppEntidadFinanciera.mapper.RequestMapperDTO;
import com.example.AppEntidadFinanciera.entity.Client;
import com.example.AppEntidadFinanciera.entity.FinancialProduct;
import com.example.AppEntidadFinanciera.repository.FinancialProductRepository;
import com.example.AppEntidadFinanciera.service.IClientService;
import com.example.AppEntidadFinanciera.service.IFinancialProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FinancialProductServiceImpl implements IFinancialProductService {

    private final FinancialProductRepository financialProductRepository;
    private final IClientService clientService;

    public FinancialProductServiceImpl(FinancialProductRepository financialProductRepository, IClientService clientService) {
        this.financialProductRepository = financialProductRepository;
        this.clientService = clientService;
    }

    @Override
    public void createFinancialProduct(RequestProductDTO requestProductDTO, Long clientId) {
        Client client = clientService.findClientById(clientId);
        FinancialProduct financialProduct = RequestMapperDTO.productToDto(requestProductDTO, client);
        financialProduct.setCreationDate(LocalDateTime.now());
        financialProduct.setClient(client);
        financialProductRepository.save(financialProduct);
    }

    @Override
    public FinancialProduct findFinancialProductById(Long productId) {
        return financialProductRepository.findById(productId).orElse(null);
    }

    @Override
    public List<FinancialProduct> findFinancialProductsByClientId(Long clientId) {
        return financialProductRepository.findByClientId(clientId);
    }

    @Override
    public void updateFinancialProduct(Long productId, RequestProductDTO updatedProductDTO) {
        FinancialProduct financialProduct = financialProductRepository.findById(productId).orElse(null);
        if (financialProduct != null) {
            financialProduct.setAccountType(updatedProductDTO.getAccountType());
            financialProduct.setAccountNumber(updatedProductDTO.getAccountNumber());
            financialProduct.setStatus(updatedProductDTO.getStatus());
            financialProduct.setBalance(updatedProductDTO.getBalance());
            financialProduct.setExemptFromGMF(updatedProductDTO.getExemptFromGMF());
            financialProduct.setModificationDate(LocalDateTime.now());
            financialProductRepository.save(financialProduct);
        }
    }

    @Override
    public void deleteFinancialProduct(Long productId) {
        financialProductRepository.deleteById(productId);
    }
}
