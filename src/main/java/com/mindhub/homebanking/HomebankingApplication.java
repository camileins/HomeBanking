package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication
public class HomebankingApplication {

//	@Autowired
//	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class);
	}

	@Bean
	public CommandLineRunner initData(@Autowired ClientRepository clientRepository, @Autowired AccountRepository accountRepository, @Autowired TransactionRepository transactionRepository,
									  @Autowired LoanRepository loanRepository,
									  @Autowired ClientLoanRepository clientLoanRepository,
									  @Autowired CardRepository cardRepository) {
		return (args) -> {
			// save a couple of customers;

//			Client client1 = clientRepository.save(new Client("Melba", "Morel", "melba@gmail.com", passwordEncoder.encode("123")));
//			Client client2 = clientRepository.save(new Client("Jack", "Bauer", "jack@mindhub.com", passwordEncoder.encode("jack123")));
//			Client admin = clientRepository.save(new Client("admin", "admin", "a@a.cl", passwordEncoder.encode("123")));
//
//			LocalDateTime date = (LocalDateTime.now());
//			LocalDateTime dateNextDay = (LocalDateTime.now().plusDays(1));
//			LocalDateTime dateFiveYears =  (LocalDateTime.now().plusYears(5));
//
//			Account account1 = accountRepository.save(new Account("VIN001",  date, 5000, client1));
//			Account account2 = accountRepository.save(new Account("VIN002", dateNextDay, 7500, client1));
//			Account account3 = accountRepository.save(new Account("VIN003", date, 10000, client2));
//
//			//Credito: sumar, debito: restar
//			Transaction transaction1 = transactionRepository.save(new Transaction(TransactionType.debit, -500,"Compra" ,date, account1));
//			Transaction transaction2 = transactionRepository.save(new Transaction(TransactionType.credit, 1000,"Compra" ,date, account1));
//			Transaction transaction3 = transactionRepository.save(new Transaction(TransactionType.debit, -2000,"Compra" ,date, account2));
//			Transaction transaction4 = transactionRepository.save(new Transaction(TransactionType.debit, -3000,"Compra" ,date, account3));
//
//
//			Loan loan1 = loanRepository.save(new Loan("Hipotecario", 500000, Arrays.asList(6, 12, 24, 36, 48,60)));
//			Loan loan2 = loanRepository.save(new Loan("Personal", 100000,Arrays.asList(6, 12, 24)));
//			Loan loan3 = loanRepository.save(new Loan("Automotriz", 300000,Arrays.asList(6, 12, 24, 36)));
//
//			ClientLoan c1loan = clientLoanRepository.save(new ClientLoan(400000, 60, client1, loan1));
//			ClientLoan c1loan2 = clientLoanRepository.save(new ClientLoan(50000, 12, client1, loan2));
//			ClientLoan c2loan1 = clientLoanRepository.save(new ClientLoan(100000, 24, client2, loan2));
//			ClientLoan c2loan2 = clientLoanRepository.save(new ClientLoan(200000, 36, client2, loan3));
//
//
//			String cardholder1 = client1.toString();
//
//			Card card1 = cardRepository.save(new Card(CardType.DEBIT, CardColor.GOLD, "1111 1115 5434 5677", 515, date, dateFiveYears, client1));
//			Card card2 = cardRepository.save(new Card(CardType.CREDIT, CardColor.TITANIUM, "7764 1115 5434 5677", 321, date, dateFiveYears, client1));
//			Card card3 = cardRepository.save(new Card(CardType.CREDIT, CardColor.SILVER, "1234 5432 5434 5677", 222, date, dateFiveYears, client2));
//
//
//			client1.addAccount(account1);
//			client1.addAccount(account2);
//			account2.setClient(client1);
//			transaction1.setAccount(account1);
//			transaction2.setAccount(account1);
//
//
//			clientRepository.save(client1);
//




		};
	}



}










