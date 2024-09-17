package com.projectcloud.project_cloud.controller;

import com.projectcloud.project_cloud.model.Card;
import com.projectcloud.project_cloud.model.User;
import com.projectcloud.project_cloud.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<User>> getUser() {
        return new ResponseEntity(service.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserbyId(@PathVariable("id") int id) {
        User response = service.getUser(id);

        if(response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
        User response = service.createUser(user);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("{id}/addCard")
    public ResponseEntity<String> addCardToUser(@PathVariable("id") int id, @Valid @RequestBody Card card) {
        try {
            service.addCard(card, id);
            return new ResponseEntity<>("Cartão adicionado com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}/cards")
    public ResponseEntity<List<Card>> getUserCards(@PathVariable("id") int id) {
        User user = service.getUser(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user.getCardList(), HttpStatus.OK);
    }
}