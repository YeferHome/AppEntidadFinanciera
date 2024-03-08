package com.example.AppEntidadFinanciera.DTO;

import com.example.AppEntidadFinanciera.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ResponseTransactionDTO {

    private Long id;
    private TransactionType transactionType;
    private BigDecimal amount;
    private LocalDateTime sendDate;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }

    public Long getId() {
        return id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }
}
