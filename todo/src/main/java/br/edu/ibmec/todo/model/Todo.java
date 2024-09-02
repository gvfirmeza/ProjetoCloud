package br.edu.ibmec.todo.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Todo {

    @NotBlank(message = "Campo id obrigatório")
    private int id;

    @NotBlank(message = "Campo name obrigatório")
    private String name;

    @NotBlank(message = "Campo owner obrigatório")
    private String owner;

    @NotBlank(message = "Campo status obrigatório")
    private String status;

    @NotBlank(message = "Campo description obrigatório")
    private String description;

}
