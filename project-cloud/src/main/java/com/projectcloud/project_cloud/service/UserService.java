package com.projectcloud.project_cloud.service;

import com.projectcloud.project_cloud.model.User;
import com.projectcloud.project_cloud.model.Card;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();

    // Cria um novo usuário sem cartões
    public User createUser(User user) {
        user.setId(generateUserId()); // Gera o ID automaticamente

        // Inicializa a lista de cartões como uma lista vazia se for nula
        if (user.getCards() == null) {
            user.setCards(new ArrayList<>());
        }

        users.add(user);  // Adiciona o usuário à lista
        return user;
    }

    // Retorna todos os usuários
    public List<User> getAllUsers() {
        return users;
    }

    // Retorna um usuário por ID
    public User getUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Atualiza as informações de um usuário existente (sem mexer nos cartões)
    public User updateUser(int id, User updatedUser) {
        User existingUser = getUserById(id);
        if (existingUser != null) {
            existingUser.setName(updatedUser.getName());
            existingUser.setCpf(updatedUser.getCpf());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setBirth_date(updatedUser.getBirth_date());
            return existingUser;
        }
        return null; // Se o usuário não existir, retorna null
    }

    // Remove um usuário por ID
    public boolean deleteUser(int id) {
        return users.removeIf(user -> user.getId() == id);
    }

    // Adiciona um cartão a um usuário existente
    public Card addCardToUser(int userId, Card card) {
        User user = getUserById(userId);
        if (user != null) {
            // Inicializa a lista de cartões se for nula
            if (user.getCards() == null) {
                user.setCards(new ArrayList<>());
            }

            card.setId(generateCardId(user)); // Gera um novo ID para o cartão
            user.getCards().add(card); // Adiciona o cartão ao usuário
            return card;
        }
        return null;
    }

    public Card getCardById(int cardId) {
        for (User user : users) {
            if (user.getCards() != null) {
                for (Card card : user.getCards()) {
                    if (card.getId() == cardId) {
                        return card;  // Retorna o cartão se o ID corresponder
                    }
                }
            }
        }
        return null; // Retorna null se o cartão não for encontrado
    }

    // Retorna todos os cartões de um usuário
    public List<Card> getCardsForUser(int userId) {
        User user = getUserById(userId);
        if (user != null) {
            if (user.getCards() == null) {
                user.setCards(new ArrayList<>()); // Inicializa se for nulo
            }
            return user.getCards();
        }
        return null;
    }

    // Gera um ID único para o usuário
    private int generateUserId() {
        return users.size() + 1;
    }

    // Gera um ID único para o cartão de um usuário
    private int generateCardId(User user) {
        return user.getCards().size() + 1;
    }
}
