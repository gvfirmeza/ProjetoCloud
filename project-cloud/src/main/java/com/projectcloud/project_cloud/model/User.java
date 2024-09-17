package com.projectcloud.project_cloud.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class User {

    @NotNull(message = "Campo id obrigatório")
    private int id;

    @NotBlank(message = "Campo nome obrigatório")
    private String name;

    @NotBlank(message = "Campo cpf obrigatório")
    private String cpf;

    @NotBlank(message = "Campo telefone obrigatório")
    private String phone;

    @NotNull(message = "Campo data nascimento obrigatório")
    private LocalDate birth_date;

    private List<Card> cardList = new ArrayList<>();

    public void addCard(Card card) {
        this.cardList.add(card);
    }
}
