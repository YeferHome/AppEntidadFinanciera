package com.example.AppEntidadFinanciera.service;

import com.example.AppEntidadFinanciera.entity.Transactions;
import java.util.List;

public interface ITransactionsService {

    void makeDeposit(Long productId, Transactions deposit);

    void makeWithdrawal(Long productId, Transactions withdrawal);

    void makeTransfer(Long sourceAccountId, Long destinationAccountId, Transactions transfer);

    Transactions findTransactionById(Long transactionId);

    List<Transactions> findTransactionsByProductId(Long productId);

}
