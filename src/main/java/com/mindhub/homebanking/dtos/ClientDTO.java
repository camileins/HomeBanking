package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;


import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO {

    private String firstName;
    private String lastName;
    private String email;
    private List<AccountDTO> accounts;
    private Long id;
    private List<ClientLoanDTO> loans;
    private List<CardDTO> cards;






    public ClientDTO() { }
    public ClientDTO(Client client) {

        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.id = client.getId();
        this.accounts = client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toList());
        this.loans = client.getClientLoans().stream().map(ClientLoanDTO::new).collect(Collectors.toList());
        this.cards = client.getCards().stream().map(CardDTO::new).collect(Collectors.toList());


    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String toString() {
        return firstName + " " + lastName;
    }
    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDTO> accounts)
    {
        this.accounts = accounts;
    }

    public List<ClientLoanDTO> getLoans() {
        return loans;
    }

    public void setLoans(List<ClientLoanDTO> loans) {
        this.loans = loans;
    }

    public List<CardDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardDTO> cards) {
        this.cards = cards;
    }
}


