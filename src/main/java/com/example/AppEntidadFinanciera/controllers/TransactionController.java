package com.example.AppEntidadFinanciera.controllers;


import com.example.AppEntidadFinanciera.entity.Transactions;
import com.example.AppEntidadFinanciera.service.ITransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    private ITransactionsService transactionsService;

    @PostMapping("/make_deposit/{productId}")
    public void makeDeposit(@PathVariable Long productId, @RequestBody BigDecimal deposit){
        transactionsService.makeDeposit(productId, deposit);
    }
    @PostMapping("/make_withdrawal/{productId}")
    public void makeWithsrwal(@PathVariable Long productId,@RequestBody BigDecimal Withdrawal){
        transactionsService.makeWithdrawal(productId, Withdrawal);
    }
    @PostMapping("/make_transfer/{sourceAccountId}/{destinationAccountId}")
    public void makeTransfer(@PathVariable Long  sourceAccountId, @PathVariable Long destinationAccountId, @RequestBody BigDecimal transfer ){
        transactionsService.makeTransfer(sourceAccountId, destinationAccountId, transfer);
    }
    @GetMapping("/find_transaction_By/{transactionId}")
    public Transactions findTransactionById(@PathVariable Long transactionId){
        return transactionsService.findTransactionById(transactionId);
    }
    @PostMapping("/list_transaction/{productId}")
    public List<Transactions> findTransactionsByProductId(@PathVariable Long productId){
        return transactionsService.findTransactionsByProductId(productId);
    }

}
