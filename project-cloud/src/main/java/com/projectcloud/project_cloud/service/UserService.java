package com.projectcloud.project_cloud.service;

import com.projectcloud.project_cloud.model.Card;
import com.projectcloud.project_cloud.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static List<User> Users = new ArrayList<>();

    public List<User> getAllUsers() {
        return UserService.Users;
    }

    public User getUser(int id){
        return findUser(id);
    }

    public User createUser (User user) {
        if (user.getBirth_date() != null && isOlderThan18(user.getBirth_date())) {
            UserService.Users.add(user);
            return user;
        } else {
            throw new IllegalArgumentException("Usuário deve ter pelo menos 18 anos");
        }
    }

    public void addCard(Card card, int id) throws Exception {
        User user = this.findUser(id);

        if (user == null) {
            throw new Exception("Não foi encontrado este usuário");
        }

        if (!card.getActive()) {
            throw new Exception("Não é possível associar um cartão inativo ao usuário");
        }

        user.addCard(card);
    }

    private boolean isOlderThan18(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }

    private User findUser(int id) {
        User response = null;

        for (User user : Users) {
            if (user.getId() == id) {
                response = user;
                break;
            }
        }
        return response;
    }
}
