package com.mindhub.homebanking.models;

import javax.persistence.*;

@Entity
public class ClientLoan {

    @Id
    @GeneratedValue
    private Long id;

    private double amount;
    private Integer payments;


    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;


    public ClientLoan(){}
    public ClientLoan( double amount, Integer payments, Client client, Loan loan) {

        this.amount = amount;
        this.payments = payments;
        this.client = client;
        this.loan = loan;
    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }


}
