package com.projectcloud.project_cloud.service;

import com.projectcloud.project_cloud.model.Card;
import com.projectcloud.project_cloud.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    private final UserService userService;

    public CardService(UserService userService) {
        this.userService = userService;
    }

    public boolean deleteCard(int userId, int cardId) {
        User user = userService.getUserById(userId);
        if (user != null && user.getCards() != null) {
            List<Card> cards = user.getCards();
            return cards.removeIf(card -> card.getId() == cardId); // Exclui o cartão pelo ID
        }
        return false;
    }

    // Outros métodos relacionados aos cartões podem ser adicionados aqui...
}
