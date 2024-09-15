package com.projectcloud.project_cloud.controller;

import com.projectcloud.project_cloud.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    // Endpoint para excluir um cartão por ID
    @DeleteMapping("/{userId}/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable int userId, @PathVariable int cardId) {
        boolean isDeleted = cardService.deleteCard(userId, cardId);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna 204 No Content
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 se o cartão ou usuário não forem encontrados
        }
    }

    // Outros endpoints para manipular cartões podem ser adicionados aqui...
}
