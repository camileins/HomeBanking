package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/transactions")
    public List<TransactionDTO> getAllTransactions(){
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream().map(TransactionDTO::new).collect(Collectors.toList());

//        FindALL devuelve lista. Map convierte elementos cliente (client) en objetos de tipo cliente
    }

//Creando el post de transacciones
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(@RequestParam String fromAccountNumber, @RequestParam String toAccountNumber, @RequestParam double amount,
                                                    @RequestParam String description, Authentication authentication) {
        Client client = this.clientRepository.findByEmail(authentication.getName());

        Account accountFrom = accountRepository.findByNumber(fromAccountNumber);
        Account accountTo = accountRepository.findByNumber(toAccountNumber);

        if(accountFrom == null ){
            return new ResponseEntity<>("Origin account doesn't exist", HttpStatus.FORBIDDEN);
        }
        if(accountTo == null){
            return new ResponseEntity<>("Destiny account doesn't exist", HttpStatus.FORBIDDEN);
        }


//        Controlar si puedo hacer la transferencia con el dinero que tengo en la cuenta, no puedo tener saldo negativo
        if(accountFrom.getBalance()< amount){
            return new ResponseEntity<>("Can't transfer that amount", HttpStatus.FORBIDDEN);
        }
// Que la cuenta de origen no sea la misma que de destino
        if(accountFrom.equals(accountTo)){
            return new ResponseEntity<>("Can't transfer to same account", HttpStatus.FORBIDDEN);
        }


        transactionRepository.save(new Transaction(TransactionType.debit, amount, description, LocalDateTime.now(), accountFrom));
        accountFrom.setBalance(accountFrom.getBalance()-amount);
        transactionRepository.save(new Transaction(TransactionType.credit, amount, description, LocalDateTime.now(), accountTo));
        accountTo.setBalance(accountTo.getBalance()+amount);



//        Guardar
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);


        return new ResponseEntity<>(HttpStatus.CREATED);
    }





}
