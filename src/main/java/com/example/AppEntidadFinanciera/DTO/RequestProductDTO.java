package com.example.AppEntidadFinanciera.DTO;

import com.example.AppEntidadFinanciera.entity.AccountType;
import com.example.AppEntidadFinanciera.entity.Status;

import java.math.BigDecimal;

public class RequestProductDTO {

    private AccountType accountType;
    private Status status;
    private BigDecimal balance;
    private Boolean exemptFromGMF;

    public AccountType getAccountType() {
        return accountType;
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

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setExemptFromGMF(Boolean exemptFromGMF) {
        this.exemptFromGMF = exemptFromGMF;
    }
}
