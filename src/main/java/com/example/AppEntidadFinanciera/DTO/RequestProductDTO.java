package com.example.AppEntidadFinanciera.DTO;

import com.example.AppEntidadFinanciera.entity.AccountType;
import com.example.AppEntidadFinanciera.entity.Status;

import java.math.BigDecimal;

public class RequestProductDTO {

    private AccountType accountType;
    private String accountNumber;
    private Status status;
    private BigDecimal balance;
    private Boolean exemptFromGMF;

    public AccountType getAccountType() {
        return accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Status getStatus() {
        return status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Boolean getExemptFromGMF() {
        return exemptFromGMF;
    }
}
