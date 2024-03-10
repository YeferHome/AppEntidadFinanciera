package com.example.AppEntidadFinanciera.controllers;

import com.example.AppEntidadFinanciera.entity.Transactions;
import com.example.AppEntidadFinanciera.service.ITransactionsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class TransactionControllerTest {

    @Mock
    private ITransactionsService transactionsService;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    void makeDeposit() {
        Long productId = 1L;
        BigDecimal depositAmount = BigDecimal.valueOf(1000);

        doNothing().when(transactionsService).makeDeposit(productId, depositAmount);

        transactionController.makeDeposit(productId, depositAmount);

        verify(transactionsService, times(1)).makeDeposit(productId, depositAmount);
    }

    @Test
    void makeWithdrawal() {
        Long productId = 1L;
        BigDecimal withdrawalAmount = BigDecimal.valueOf(500);

        doNothing().when(transactionsService).makeWithdrawal(productId, withdrawalAmount);

        transactionController.makeWithsrwal(productId, withdrawalAmount);

        verify(transactionsService, times(1)).makeWithdrawal(productId, withdrawalAmount);
    }

    @Test
    void makeTransfer() {
        Long sourceAccountId = 1L;
        Long destinationAccountId = 2L;
        BigDecimal transferAmount = BigDecimal.valueOf(500);

        doNothing().when(transactionsService).makeTransfer(sourceAccountId, destinationAccountId, transferAmount);

        transactionController.makeTransfer(sourceAccountId, destinationAccountId, transferAmount);

        verify(transactionsService, times(1)).makeTransfer(sourceAccountId, destinationAccountId, transferAmount);
    }
    @Test
    void findTransactionById() {
        Long transactionId = 1L;
        Transactions transaction = new Transactions();
        transaction.setId(transactionId);

        when(transactionsService.findTransactionById(transactionId)).thenReturn(transaction);

        Transactions result = transactionController.findTransactionById(transactionId);

        assertEquals(transaction.getId(), result.getId());
    }
}