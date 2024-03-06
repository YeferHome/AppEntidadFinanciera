package com.example.AppEntidadFinanciera.controllers;


import com.example.AppEntidadFinanciera.entity.Transactions;
import com.example.AppEntidadFinanciera.service.IFinancialProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    private IFinancialProductService financialProductService;

    @GetMapping("/make_deposit")
    public void makeDeposit(@PathVariable Long productId, @RequestBody Transactions deposit){
        financialProductService.findFinancialProductById(productId);
    }


}
