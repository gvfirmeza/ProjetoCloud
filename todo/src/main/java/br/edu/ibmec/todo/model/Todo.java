package br.edu.ibmec.todo.model;

import lombok.Data;

@Data
public class Todo {

    private int id;

    private String name;

    private String owner;

    private String status;

    private String description;

}
