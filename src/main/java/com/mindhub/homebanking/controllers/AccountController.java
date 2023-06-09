package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import net.minidev.json.annotate.JsonIgnore;
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
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @GetMapping("/accounts")
    public List<AccountDTO> getAllAccounts(){
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(AccountDTO::new).collect(Collectors.toList());

//        FindALL devuelve lista. Map convierte elementos cliente (client) en objetos de tipo cliente
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication) {
        Client client = this.clientRepository.findByEmail(authentication.getName());
        return client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        Client client = this.clientRepository.findByEmail(authentication.getName());

//        No puede tener mas de 3 cuentas registradas

        if(client.getAccounts().size() >= 3){
            return new ResponseEntity<>("Client´s of accounts limit reached", HttpStatus.FORBIDDEN);
        }

        String accountNumber = "VIN"+ (int)((Math.random() * (1000 - 1)) + 1);

        accountRepository.save(new Account(accountNumber, LocalDateTime.now(), 0, client));


        return new ResponseEntity<>("Cuenta creada",HttpStatus.CREATED);
    }






}