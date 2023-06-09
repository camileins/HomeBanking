package com.mindhub.homebanking.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
public class Loan {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private double maxAmount;

//Prestamo va a tener multiples cuotas
    @ElementCollection
    @Column(name="payment")
    private List<Integer> payments = new ArrayList<>();
//    ****
    @OneToMany(mappedBy = "loan",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClientLoan> clientLoans = new HashSet<>();


    public Loan(){
    }

    public Loan(String name, double maxAmount, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;

    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public Set<ClientLoan> getClients(){ return clientLoans;}

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }
}
