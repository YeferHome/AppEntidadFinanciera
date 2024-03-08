package com.example.AppEntidadFinanciera.service.Implement;

import com.example.AppEntidadFinanciera.entity.FinancialProduct;
import com.example.AppEntidadFinanciera.entity.TransactionType;
import com.example.AppEntidadFinanciera.entity.Transactions;
import com.example.AppEntidadFinanciera.repository.FinancialProductRepository;
import com.example.AppEntidadFinanciera.repository.TransactionsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionsServiceTest {

    @Mock
    private FinancialProductRepository financialProductRepository;

    @Mock
    private TransactionsRepository transactionsRepository;

    @InjectMocks
    private TransactionsService transactionsService;

    @Test
    void makeDeposit() {
        Long productId = 1L;
        BigDecimal depositAmount = new BigDecimal(1000);

        FinancialProduct existingProduct = new FinancialProduct();
        existingProduct.setId(productId);
        existingProduct.setBalance(BigDecimal.valueOf(5000));

        when(financialProductRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        transactionsService.makeDeposit(productId, depositAmount);

        BigDecimal expectedBalance = BigDecimal.valueOf(6000);
        assertEquals(expectedBalance, existingProduct.getBalance());

        ArgumentCaptor<Transactions> transactionCaptor = ArgumentCaptor.forClass(Transactions.class);
        verify(transactionsRepository).save(transactionCaptor.capture());
        Transactions depositTransaction = transactionCaptor.getValue();
        assertEquals(depositAmount, depositTransaction.getAmount());
        assertEquals(TransactionType.DEPOSIT, depositTransaction.getTransactionType());
        assertNotNull(depositTransaction.getSendDate());
    }

        @Test
        void makeWithdrawal() {
            Long productId = 1L;
            BigDecimal withdrawalAmount = new BigDecimal(1000);

            FinancialProduct existingProduct = new FinancialProduct();
            existingProduct.setId(productId);
            existingProduct.setBalance(BigDecimal.valueOf(5000));

            when(financialProductRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

            transactionsService.makeWithdrawal(productId, withdrawalAmount);

            BigDecimal expectedBalance = BigDecimal.valueOf(4000);
            assertEquals(expectedBalance, existingProduct.getBalance());

            ArgumentCaptor<Transactions> transactionCaptor = ArgumentCaptor.forClass(Transactions.class);
            verify(transactionsRepository).save(transactionCaptor.capture());
            Transactions withdrawalTransaction = transactionCaptor.getValue();
            assertEquals(withdrawalAmount, withdrawalTransaction.getAmount());
            assertEquals(TransactionType.WITHDRAWAL, withdrawalTransaction.getTransactionType());
            assertNotNull(withdrawalTransaction.getSendDate());
        }



    @Test
    void makeTransfer() {
        Long sourceAccountId = 1L;
        Long destinationAccountId = 2L;
        BigDecimal transferAmount = BigDecimal.valueOf(1000);

        FinancialProduct sourceAccount = new FinancialProduct();
        sourceAccount.setId(sourceAccountId);
        sourceAccount.setBalance(BigDecimal.valueOf(5000));

        FinancialProduct destinationAccount = new FinancialProduct();
        destinationAccount.setId(destinationAccountId);
        destinationAccount.setBalance(BigDecimal.valueOf(2000));

        when(financialProductRepository.findById(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(financialProductRepository.findById(destinationAccountId)).thenReturn(Optional.of(destinationAccount));

        transactionsService.makeTransfer(sourceAccountId, destinationAccountId, transferAmount);

        BigDecimal expectedBalanceSource = BigDecimal.valueOf(4000);
        BigDecimal expectedBalanceDestination = BigDecimal.valueOf(3000);
        assertEquals(expectedBalanceSource, sourceAccount.getBalance());
        assertEquals(expectedBalanceDestination, destinationAccount.getBalance());

        ArgumentCaptor<Transactions> transactionCaptor = ArgumentCaptor.forClass(Transactions.class);
        verify(transactionsRepository, times(2)).save(transactionCaptor.capture());
        List<Transactions> savedTransactions = transactionCaptor.getAllValues();

        Transactions sourceTransaction = savedTransactions.get(0);
        Transactions destinationTransaction = savedTransactions.get(1);

        assertEquals(TransactionType.TRANSFER, sourceTransaction.getTransactionType());
        assertEquals(TransactionType.TRANSFER, destinationTransaction.getTransactionType());
        assertEquals(transferAmount.negate(), sourceTransaction.getAmount());
        assertEquals(transferAmount, destinationTransaction.getAmount());
        assertNotNull(sourceTransaction.getSendDate());
        assertNotNull(destinationTransaction.getSendDate());
    }

    @Test
    void findTransactionById() {
        Long transactionId = 1L;
        Transactions transaction = new Transactions();
        when(transactionsRepository.findById(transactionId)).thenReturn(Optional.of(transaction));

        Transactions foundTransaction = transactionsService.findTransactionById(transactionId);

        assertEquals(transaction, foundTransaction);
    }

}