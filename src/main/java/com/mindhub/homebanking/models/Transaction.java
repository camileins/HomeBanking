package com.mindhub.homebanking.models;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;
//    Id se autogenera y no se hereda de otras clases
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime date;

    //    Relacion uno es a muchos mapeados por Accounts
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    public Transaction(TransactionType type, double amount, String description, LocalDateTime date, Account account) {
        this.type = type;
        if (type == TransactionType.debit){
            this.amount=amount*-1;
        }else{
            this.amount = amount;
        }
        this.description = description;
        this.date = date;
        this.account = account;
    }

    public Transaction(){}

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

//    Hasta aca getter y setters auto-generados
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }


}
