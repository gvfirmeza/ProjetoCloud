package com.projectcloud.project_cloud.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class Card {

    @NotNull(message = "Campo id obrigatório")
    private int id;

    @NotBlank(message = "Número do cartão é obrigatório")
    private String cardNumber;

    @NotBlank(message = "Nome do titular do cartão é obrigatório")
    private String cardHolderName;

    private double creditLimit;
    private boolean isActive;

    private List<Transaction> transactions;
}
