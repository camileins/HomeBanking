package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.CreateClientDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.ClientRepository;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")

public class ClientController {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/clients")
    public List<ClientDTO> getAllClients(){
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(ClientDTO::new).collect(Collectors.toList());

//        FindALL devuelve lista. Map convierte elementos cliente (client) en objetos de tipo cliente
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }

    @GetMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication) {
         Client client = this.clientRepository.findByEmail(authentication.getName());
         return new ClientDTO(client);
    }
//    Es en funcion del DTO



    @PostMapping("/clients")
//    Quitar auntenticacion   y a la clase cliente ya que es antes del login
    public ResponseEntity<Object> createClient(@RequestParam String firstName,@RequestParam String lastName,@RequestParam String email,@RequestParam String password ) {
//        Client client = this.clientRepository.findByEmail(authentication.getName());

        clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));

        return new ResponseEntity<>("Cliente creado",HttpStatus.CREATED);
    }



}
