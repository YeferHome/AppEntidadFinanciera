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
    public void makeDeposit(Long productId, BigDecimal depositAmount) {
        FinancialProduct financialProduct = financialProductRepository.findById(productId).orElse(null);

        BigDecimal newBalance = financialProduct.getBalance().add(depositAmount);
        financialProduct.setBalance(newBalance);
        financialProductRepository.save(financialProduct);

        Transactions deposit = new Transactions();
        deposit.setAmount(depositAmount);
        deposit.setTransactionType(TransactionType.DEPOSIT);
        deposit.setSendDate(LocalDateTime.now());
        transactionsRepository.save(deposit);
    }

    @Override
    public void makeWithdrawal(Long productId, BigDecimal withdrawalAmount) {
        if (withdrawalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto de retiro debe ser mayor que cero.");
        }

        FinancialProduct financialProduct = financialProductRepository.findById(productId).orElse(null);

        if (financialProduct.getBalance().compareTo(withdrawalAmount) >= 0) {
            BigDecimal newBalance = financialProduct.getBalance().subtract(withdrawalAmount);
            financialProduct.setBalance(newBalance);
            financialProductRepository.save(financialProduct);

            Transactions withdrawal = new Transactions();
            withdrawal.setAmount(withdrawalAmount);
            withdrawal.setTransactionType(TransactionType.WITHDRAWAL);
            withdrawal.setSendDate(LocalDateTime.now());
            transactionsRepository.save(withdrawal);
        }
    }

    @Override
    public void makeTransfer(Long sourceAccountId, Long destinationAccountId, BigDecimal transferAmount) {
        FinancialProduct sourceAccount = financialProductRepository.findById(sourceAccountId).orElse(null);
        FinancialProduct destinationAccount = financialProductRepository.findById(destinationAccountId).orElse(null);

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
            sourceTransaction.setSendDate(LocalDateTime.now());
            transactionsRepository.save(sourceTransaction);

            Transactions destinationTransaction = new Transactions();
            destinationTransaction.setTransactionType(TransactionType.TRANSFER);
            destinationTransaction.setAmount(transferAmount);
            destinationTransaction.setSendDate(LocalDateTime.now());
            transactionsRepository.save(destinationTransaction);
        }
    }

    @Override
    public Transactions findTransactionById(Long transactionId) {
        return transactionsRepository.findById(transactionId).orElse(null);
    }

    @Override
    public List<Transactions> findTransactionsByProductId(Long productId) {
        List<Transactions> transactions = transactionsRepository.findByProduct_Id(productId);

        for (Transactions transaction : transactions) {
            FinancialProduct product = transaction.getProduct();

            if (product != null) {
                transaction.setProduct(product);
            }
        }
        return transactions;
    }

}
