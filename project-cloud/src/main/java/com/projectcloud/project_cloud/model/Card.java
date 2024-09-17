package com.projectcloud.project_cloud.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Card {

    @NotNull(message = "Campo id obrigatório")
    public int id;

    @NotNull(message = "Campo ativo obrigatório")
    public Boolean active;

    @NotNull(message = "Campo limite obrigatório")
    public double limit;

    @NotNull(message = "Campo número obrigatório")
    public String number;

    // Adicionar lista de transações
}
