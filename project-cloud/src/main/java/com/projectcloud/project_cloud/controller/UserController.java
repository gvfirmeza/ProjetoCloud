package com.projectcloud.project_cloud.controller;

import com.projectcloud.project_cloud.model.User;
import com.projectcloud.project_cloud.model.Card;
import com.projectcloud.project_cloud.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint para criar um novo usuário sem cartões
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Retorna todos os usuários
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Retorna um usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        return user != null ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Atualiza as informações do usuário (sem a necessidade de cartões)
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @Valid @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return updatedUser != null ? new ResponseEntity<>(updatedUser, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Deleta um usuário por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) {
        boolean isDeleted = userService.deleteUser(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Endpoint para adicionar um cartão a um usuário existente
    @PostMapping("/{userId}/cards")
    public ResponseEntity<Card> addCardToUser(@PathVariable int userId, @Valid @RequestBody Card card) {
        Card newCard = userService.addCardToUser(userId, card);
        return newCard != null ? new ResponseEntity<>(newCard, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Retorna todos os cartões de um usuário
    @GetMapping("/{userId}/cards")
    public ResponseEntity<List<Card>> getCardsForUser(@PathVariable int userId) {
        List<Card> cards = userService.getCardsForUser(userId);
        return cards != null ? new ResponseEntity<>(cards, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
