package com.projectcloud.project_cloud.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Transaction {

    @NotNull(message = "Campo id da transação é obrigatório")
    private int id;

    @NotNull(message = "Valor da transação é obrigatório")
    private double amount;

    @NotBlank(message = "Comerciante é obrigatório")
    private String merchant;

    private long timestamp;

    private Card card;
}
