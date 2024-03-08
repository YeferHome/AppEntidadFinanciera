package com.example.AppEntidadFinanciera.service;

import com.example.AppEntidadFinanciera.entity.Transactions;

import java.math.BigDecimal;

public interface ITransactionsService {

    void makeDeposit(Long productId, BigDecimal deposit);

    void makeWithdrawal(Long productId, BigDecimal withdrawal);

    void makeTransfer(Long sourceAccountId, Long destinationAccountId, BigDecimal transfer);

    Transactions findTransactionById(Long transactionId);

}
