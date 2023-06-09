package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
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
public class LoanController {

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;



    @GetMapping("/loans")
    public List<LoanDTO> getAllLoans(){
        List<Loan> loans = loanRepository.findAll();
        return loans.stream().map(LoanDTO::new).collect(Collectors.toList());

    }
//    {loanId: this.loanTypeId, amount: this.amount, payments: this.payments, toAccountNumber: this.accountToNumber}

    @PostMapping("/loans")
    public ResponseEntity<Object> createLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {
        Client client = this.clientRepository.findByEmail(authentication.getName());

//    crear el post del loan
//        se creo un DTO desde donde se obtienen los datos hacia el controlador, los datos vienen desde la web y se transforman desde alli a un DTO
        //Buscar el account toAccountNumber y crear una transaccion que sume a esa cuenta, actualizar el balance la cuenta sumando lo pedido

        Loan loan = this.loanRepository.findById(loanApplicationDTO.getLoanId());
        Account accountTo = accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber());

// Que ni la cantidad solicitada ni el numero de cuotas sea 0
    if(loanApplicationDTO.getAmount()<=0 && loanApplicationDTO.getPayments() == 0){
        return new ResponseEntity<>("Can't apply to loan, enter a correct amount or number o payments", HttpStatus.FORBIDDEN);
    }
//    Verificar que la cantidad solicitada no exceda el maximo
    if (loanApplicationDTO.getAmount() > loan.getMaxAmount()){
        return new ResponseEntity<>("Can't apply to loan, amount can't exceed loan max amount", HttpStatus.FORBIDDEN);
    }
//    Verificar que cuenta exista
        if(accountTo == null ){
            return new ResponseEntity<>("Account doesn't exist", HttpStatus.FORBIDDEN);
        }


//Se debe crear una transacción “CREDIT” asociada a la cuenta de destino (el monto debe quedar positivo) con la descripción concatenando el nombre del préstamo y la frase “loan approved”
//Se debe actualizar la cuenta de destino sumando el monto solicitado.

        clientLoanRepository.save(new ClientLoan(loanApplicationDTO.getAmount(), loanApplicationDTO.getPayments(), client, loan));
        transactionRepository.save(new Transaction(TransactionType.credit, loanApplicationDTO.getAmount(), "Prestamo "+ loan.getName(), LocalDateTime.now(), accountTo));
//        accountTo.setBalance(accountTo.getBalance()+amount);
        accountTo.setBalance(accountTo.getBalance()+loanApplicationDTO.getAmount());
        accountRepository.save(accountTo);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }




}
