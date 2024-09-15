package com.projectcloud.project_cloud.controller;

import com.projectcloud.project_cloud.model.Transaction;
import com.projectcloud.project_cloud.model.Card;
import com.projectcloud.project_cloud.service.TransactionService;
import com.projectcloud.project_cloud.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    // Autorizar uma transação para um cartão específico
    @PostMapping("/card/{cardId}/authorize")
    public ResponseEntity<String> authorizeTransaction(@PathVariable int cardId, @Valid @RequestBody Transaction transaction) {
        // Verifica se o cartão existe
        Card card = userService.getCardById(cardId);
        if (card == null) {
            return new ResponseEntity<>("Cartão não encontrado", HttpStatus.NOT_FOUND);
        }

        // Autoriza a transação com base nas regras de negócio
        String result = transactionService.authorizeTransaction(card, transaction);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Processar uma transação para um cartão específico
    @PostMapping("/card/{cardId}/process")
    public ResponseEntity<String> processTransaction(@PathVariable int cardId, @Valid @RequestBody Transaction transaction) {
        // Verifica se o cartão existe
        Card card = userService.getCardById(cardId);
        if (card == null) {
            return new ResponseEntity<>("Cartão não encontrado", HttpStatus.NOT_FOUND);
        }

        // Processa a transação e debita do limite de crédito
        String result = transactionService.processTransaction(card, transaction);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Obter o histórico de transações de um cartão específico
    @GetMapping("/card/{cardId}/history")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable int cardId) {
        // Verifica se o cartão existe
        Card card = userService.getCardById(cardId);
        if (card == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Retorna o histórico de transações do cartão
        List<Transaction> transactions = transactionService.getTransactionsForCard(card);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
