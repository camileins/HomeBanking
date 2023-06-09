package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
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
public class CardController {


    @Autowired
    CardRepository cardRepository;
    @Autowired
    ClientRepository clientRepository;


    @GetMapping("/cards")
    public List<CardDTO> getAllCards(){
        List<Card> cards = cardRepository.findAll();
        return cards.stream().map(CardDTO::new).collect(Collectors.toList());

    }


    // Generar numero y CVV aleatorio
//        Controlar limite de cartas diferenciado por tipo de carta (credito/debito), en total hay que tener 3 de cada maximo
//        Hacer el post de transferencias, ver transfers.js (post, ver en el account controller)

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor,Authentication authentication) {
        Client client = this.clientRepository.findByEmail(authentication.getName());


//        if(client.getCards().size>= 3){
//            return new ResponseEntity<>("Client´s cards limit reached", HttpStatus.FORBIDDEN);
//        }

//            Integer credit = 0;
//            Integer debit = 0;
//            for (Card card: client.getCards()){
//                if (card.getType() == CardType.DEBIT){
//                    debit++;
//                } else if (card.getType() == CardType.CREDIT){
//                    credit++;
//                }
//            }

//            if(cardType == CardType.DEBIT && debit >= 3){
//                return new ResponseEntity<>("Client´s  cards limit reached", HttpStatus.FORBIDDEN);
//            }
//            if(cardType == CardType.CREDIT && credit >= 3){
//                return new ResponseEntity<>("Client´s  creditcards limit reached", HttpStatus.FORBIDDEN);
//            }

//        Es lo mismo que el codigode arriba con un solo if
       if( client.getCards().stream().filter(card -> card.getType() == cardType).count() >= 3){
           return new ResponseEntity<>("Client´s  cards limit reached", HttpStatus.FORBIDDEN);
       }


        Integer cvv =  (int)((Math.random() * (1000 - 1)) + 1);
        String cardNumber = (int) ((Math.random() * (9999 - 1000)) + 1000)
                + " " + (int) ((Math.random() * (9999 - 1000)) + 1000)
                + " " + (int) ((Math.random() * (9999 - 1000)) + 1000)
                + " " + (int) ((Math.random() * (9999 - 1000)) + 1000);

        cardRepository.save(new Card(cardType, cardColor, cardNumber, cvv, LocalDateTime.now().plusYears(5), LocalDateTime.now(), client));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



}
