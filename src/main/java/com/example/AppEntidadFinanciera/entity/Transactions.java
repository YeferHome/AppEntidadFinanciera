package com.example.AppEntidadFinanciera.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private TransactionType transactionType;
    private BigDecimal amount;
    private LocalDateTime sendDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private FinancialProduct product;

    // Constructors
    public Transactions(Long id, TransactionType transactionType, BigDecimal amount, LocalDateTime sendDate) {
        this.id = id;
        this.transactionType = transactionType;
        this.amount = amount;
        this.sendDate = sendDate;
    }

    public Transactions() {
    }

    // GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime creationDate) {
        this.sendDate = creationDate;
    }

    public FinancialProduct getProduct() {
        return product;
    }
    public void setProduct(FinancialProduct product) {
        this.product = product;
    }

}
