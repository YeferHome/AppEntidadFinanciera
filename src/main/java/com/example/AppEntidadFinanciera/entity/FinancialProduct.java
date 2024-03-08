package com.example.AppEntidadFinanciera.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class FinancialProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private AccountType accountType;
    private String accountNumber;
    private Status status;
    private BigDecimal balance;
    private Boolean exemptFromGMF;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;


    // CONSTRUCTORS

    public FinancialProduct(Long id, AccountType accountType, String accountNumber, Status status, BigDecimal balance, Boolean exemptFromGMF, LocalDateTime creationDate, LocalDateTime modificationDate, Client client) {
        this.id = id;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.status = status;
        this.balance = balance;
        this.exemptFromGMF = exemptFromGMF;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.client = client;
    }

    public FinancialProduct() {
    }

    // GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Boolean getExemptFromGMF() {
        return exemptFromGMF;
    }

    public void setExemptFromGMF(Boolean exemptFromGMF) {
        this.exemptFromGMF = exemptFromGMF;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
