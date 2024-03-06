package com.example.AppEntidadFinanciera.controllers;

import com.example.AppEntidadFinanciera.DTO.RequestProductDTO;
import com.example.AppEntidadFinanciera.entity.FinancialProduct;
import com.example.AppEntidadFinanciera.service.IClientService;
import com.example.AppEntidadFinanciera.service.IFinancialProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/financialProduct")
public class FinancialProductController {

    @Autowired
    private IFinancialProductService financialProductService;

    @Autowired
    private IClientService clientService;

    @PostMapping("/create/{clientId}")
    public void createFinancialProduct(@RequestBody RequestProductDTO requestProductDTO, @PathVariable Long clientId){
        financialProductService.createFinancialProduct(requestProductDTO, clientId);
    }

    @GetMapping("/findByClient/{clientId}")
    public List<FinancialProduct> findFinancialProductsByClientId(@PathVariable Long clientId){
        return financialProductService.findFinancialProductsByClientId(clientId);
    }

    @GetMapping("/{productId}")
    public FinancialProduct findFinancialProductById(@PathVariable Long productId){
        return financialProductService.findFinancialProductById(productId);
    }

    @PutMapping("/update/{productId}")
    public void updateFinancialProduct(@PathVariable Long productId, @RequestBody RequestProductDTO updateProductDTO){
        financialProductService.updateFinancialProduct(productId, updateProductDTO);
    }

    @DeleteMapping("/delete/{productId}")
    public void deleteFinancialProduct(@PathVariable Long productId){
        financialProductService.deleteFinancialProduct(productId);
    }
}
