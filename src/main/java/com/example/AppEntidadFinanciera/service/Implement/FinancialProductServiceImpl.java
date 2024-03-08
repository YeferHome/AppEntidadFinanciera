package com.example.AppEntidadFinanciera.service.Implement;

import com.example.AppEntidadFinanciera.DTO.RequestProductDTO;
import com.example.AppEntidadFinanciera.entity.Client;
import com.example.AppEntidadFinanciera.entity.FinancialProduct;
import com.example.AppEntidadFinanciera.entity.Status;
import com.example.AppEntidadFinanciera.mapper.Mappers;
import com.example.AppEntidadFinanciera.repository.FinancialProductRepository;
import com.example.AppEntidadFinanciera.service.IClientService;
import com.example.AppEntidadFinanciera.service.IFinancialProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

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
        try {
            Client client = clientService.findClientById(clientId);
            if (client == null) {
                throw new IllegalArgumentException("No hay cliente creado con ese ID");
            }

            FinancialProduct financialProduct = Mappers.productToDto(requestProductDTO, client);
            if (financialProduct.getBalance().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("El monto no puede ser menor que cero");
            }
            String accountType = requestProductDTO.getAccountType().toString();
            String accountNumber = generateAccountNumber(accountType);
            financialProduct.setAccountNumber(accountNumber);

            financialProduct.setCreationDate(LocalDateTime.now());
            financialProduct.setClient(client);

            financialProductRepository.save(financialProduct);
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Error Cuenta no creada", ex);
        }
    }

    private String generateAccountNumber(String accountType) {

        try {
            String prefix = "";
            if (accountType.equalsIgnoreCase("Ahorro")) {
                prefix = "53";
            } else if (accountType.equalsIgnoreCase("Corriente")) {
                prefix = "33";
            } else {
                throw new IllegalArgumentException("Tipo de cuenta no válido: " + accountType);
            }

            String randomNumber = String.format("%08d", new Random().nextInt(100000000));

            return prefix + randomNumber;
        } catch (Exception ex) {
            throw new RuntimeException("El numero de cuenta no se genero");
        }
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
        FinancialProduct financialProduct = financialProductRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un producto financiero con el ID proporcionado"));

        if (financialProduct.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("No se puede cancelar la cuenta porque el saldo debe ser CERO");
        }

        if (!financialProduct.getStatus().equals(Status.CANCELADA)) {
            if (updatedProductDTO.getStatus().equals(Status.CANCELADA)) {
                financialProduct.setAccountType(updatedProductDTO.getAccountType());
                financialProduct.setStatus(updatedProductDTO.getStatus());
                financialProduct.setBalance(updatedProductDTO.getBalance());
                financialProduct.setExemptFromGMF(updatedProductDTO.getExemptFromGMF());
                financialProduct.setModificationDate(LocalDateTime.now());
                financialProductRepository.save(financialProduct);
            } else {
                throw new IllegalArgumentException("El estado de la cuenta solo puede cambiarse a 'CANCELADA' cuando el saldo es cero");
            }
        }
    }




    @Override
    public void deleteFinancialProduct(Long productId) {
        financialProductRepository.deleteById(productId);
    }
}
