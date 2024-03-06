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
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "productId")
    private FinancialProduct product;


    // Constructors
    public Transactions(Long id, TransactionType transactionType, BigDecimal amount, LocalDateTime creationDate) {
        this.id = id;
        this.transactionType = transactionType;
        this.amount = amount;
        this.creationDate = creationDate;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
