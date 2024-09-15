package com.projectcloud.project_cloud.service;

import com.projectcloud.project_cloud.model.Transaction;
import com.projectcloud.project_cloud.model.Card;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    private List<Transaction> transactions = new ArrayList<>();

    public String authorizeTransaction(Card card, Transaction transaction) {
        if (!card.isActive()) {
            return "Cartão não está ativo";
        }

        if (transaction.getAmount() > card.getCreditLimit()) {
            return "Limite insuficiente";
        }

        return "Transação autorizada";
    }

    public String processTransaction(Card card, Transaction transaction) {
        // Verifica se a transação foi autorizada
        String authorization = authorizeTransaction(card, transaction);
        if (!authorization.equals("Transação autorizada")) {
            return authorization;
        }

        card.setCreditLimit(card.getCreditLimit() - transaction.getAmount());

        transaction.setCard(card);
        transaction.setId(generateTransactionId());
        transactions.add(transaction);

        return "Transação processada com sucesso";
    }

    public List<Transaction> getTransactionsForCard(Card card) {
        List<Transaction> cardTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getCard().getId() == card.getId()) {
                cardTransactions.add(transaction);
            }
        }
        return cardTransactions;
    }

    private int generateTransactionId() {
        return transactions.size() + 1;
    }
}
