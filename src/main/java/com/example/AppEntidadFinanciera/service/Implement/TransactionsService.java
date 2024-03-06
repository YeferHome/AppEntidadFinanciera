package com.example.AppEntidadFinanciera.service.Implement;

import com.example.AppEntidadFinanciera.entity.FinancialProduct;
import com.example.AppEntidadFinanciera.entity.Transactions;
import com.example.AppEntidadFinanciera.entity.TransactionType;
import com.example.AppEntidadFinanciera.repository.FinancialProductRepository;
import com.example.AppEntidadFinanciera.repository.TransactionsRepository;
import com.example.AppEntidadFinanciera.service.ITransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionsService implements ITransactionsService {

    private final FinancialProductRepository financialProductRepository;
    private final TransactionsRepository transactionsRepository;

    @Autowired
    public TransactionsService(FinancialProductRepository financialProductRepository, TransactionsRepository transactionsRepository) {
        this.financialProductRepository = financialProductRepository;
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    public void makeDeposit(Long productId, Transactions deposit) {
        FinancialProduct financialProduct = financialProductRepository.findById(productId).orElse(null);
        BigDecimal newBalance = financialProduct.getBalance().add(deposit.getAmount());
        financialProduct.setBalance(newBalance);
        financialProductRepository.save(financialProduct);

        deposit.setTransactionType(TransactionType.DEPOSIT);
        deposit.setCreationDate(LocalDateTime.now());
        transactionsRepository.save(deposit);
    }

    @Override
    public void makeWithdrawal(Long productId, Transactions withdrawal) {
        FinancialProduct financialProduct = financialProductRepository.findById(productId).orElse(null);
        BigDecimal currentBalance = financialProduct.getBalance();
        BigDecimal withdrawalAmount = withdrawal.getAmount();
        if (currentBalance.compareTo(withdrawalAmount) >= 0) {
            BigDecimal newBalance = currentBalance.subtract(withdrawalAmount);
            financialProduct.setBalance(newBalance);
            financialProductRepository.save(financialProduct);

            withdrawal.setTransactionType(TransactionType.WITHDRAWAL);
            withdrawal.setCreationDate(LocalDateTime.now());
            transactionsRepository.save(withdrawal);
        }
    }

    @Override
    public void makeTransfer(Long sourceAccountId, Long destinationAccountId, Transactions transfer) {
        FinancialProduct sourceAccount = financialProductRepository.findById(sourceAccountId).orElse(null);
        FinancialProduct destinationAccount = financialProductRepository.findById(destinationAccountId).orElse(null);

        BigDecimal transferAmount = transfer.getAmount();
        if (sourceAccount.getBalance().compareTo(transferAmount) > 0) {
            BigDecimal newBalanceSource = sourceAccount.getBalance().subtract(transferAmount);
            BigDecimal newBalanceDestination = destinationAccount.getBalance().add(transferAmount);

            sourceAccount.setBalance(newBalanceSource);
            destinationAccount.setBalance(newBalanceDestination);

            financialProductRepository.save(sourceAccount);
            financialProductRepository.save(destinationAccount);

            Transactions sourceTransaction = new Transactions();
            sourceTransaction.setTransactionType(TransactionType.TRANSFER);
            sourceTransaction.setAmount(transferAmount.negate());
            sourceTransaction.setCreationDate(LocalDateTime.now());
            transactionsRepository.save(sourceTransaction);

            Transactions destinationTransaction = new Transactions();
            destinationTransaction.setTransactionType(TransactionType.TRANSFER);
            destinationTransaction.setAmount(transferAmount);
            destinationTransaction.setCreationDate(LocalDateTime.now());
            transactionsRepository.save(destinationTransaction);
        }
    }

    @Override
    public Transactions findTransactionById(Long transactionId) {
        return transactionsRepository.findById(transactionId).orElse(null);
    }

    @Override
    public List<Transactions> findTransactionsByProductId(Long productId) {
        return transactionsRepository.findByProduct_Id(productId);
    }

}
