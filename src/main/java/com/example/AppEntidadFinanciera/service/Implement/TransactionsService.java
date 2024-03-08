package com.example.AppEntidadFinanciera.service.Implement;

import com.example.AppEntidadFinanciera.entity.FinancialProduct;
import com.example.AppEntidadFinanciera.entity.TransactionType;
import com.example.AppEntidadFinanciera.entity.Transactions;
import com.example.AppEntidadFinanciera.repository.ClientRepository;
import com.example.AppEntidadFinanciera.repository.FinancialProductRepository;
import com.example.AppEntidadFinanciera.repository.TransactionsRepository;
import com.example.AppEntidadFinanciera.service.ITransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionsService implements ITransactionsService {

    private final FinancialProductRepository financialProductRepository;
    private final TransactionsRepository transactionsRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public TransactionsService(FinancialProductRepository financialProductRepository, TransactionsRepository transactionsRepository, ClientRepository clientRepository) {
        this.financialProductRepository = financialProductRepository;
        this.transactionsRepository = transactionsRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public void makeDeposit(Long productId, BigDecimal depositAmount) {
        try {
            FinancialProduct financialProduct = financialProductRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("No se encontró la cuenta para realizar el depósito"));

            BigDecimal newBalance = financialProduct.getBalance().add(depositAmount);
            financialProduct.setBalance(newBalance);
            financialProductRepository.save(financialProduct);

            Transactions deposit = new Transactions();
            deposit.setAmount(depositAmount);
            deposit.setTransactionType(TransactionType.DEPOSIT);
            deposit.setSendDate(LocalDateTime.now());
            transactionsRepository.save(deposit);
        } catch (Exception ex) {
            throw new RuntimeException("Ocurrio un error al momento de ralizar la consignacion a una cuenta");
        }

    }

    @Override
    public void makeWithdrawal(Long productId, BigDecimal withdrawalAmount) {
        try {
            if (withdrawalAmount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("El monto de retiro debe ser mayor que cero.");
            }

            FinancialProduct financialProduct = financialProductRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("No se encontró la cuenta para realizar el retiro"));

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
        } catch (Exception e) {
            throw new RuntimeException("¡¡Error!! no se pudo realizar retiro en una cuenta ");
        }
    }

    @Override
    public void makeTransfer(Long sourceAccountId, Long destinationAccountId, BigDecimal transferAmount) {
        FinancialProduct sourceAccount = financialProductRepository.findById(sourceAccountId).orElse(null);
        FinancialProduct destinationAccount = financialProductRepository.findById(destinationAccountId).orElse(null);
        try {
            if (sourceAccount == null) {
                throw new IllegalArgumentException("La cuenta de origen no existe");
            }

            if (destinationAccount == null) {
                throw new IllegalArgumentException("La cuenta de destino no existe");
            }

            if (sourceAccount.getBalance().compareTo(transferAmount) < 0) {
                throw new IllegalArgumentException("La cuenta de origen no tiene suficiente saldo para realizar la transferencia");
            }

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
        }catch (Exception ex){
            throw new RuntimeException("ERROR al momento de realizar una transferencia");
        }
    }

    @Override
    public Transactions findTransactionById(Long transactionId) {
        return transactionsRepository.findById(transactionId).orElse(null);
    }

}
