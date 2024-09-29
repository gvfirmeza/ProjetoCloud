package br.edu.ibmec.cartao_credito.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private LocalDateTime dataTransacao;

    @Column
    private double valor;

    @Column
    private String comerciante;

    @ManyToOne
    @JoinColumn(name = "cartao_id", referencedColumnName = "id")
    @JsonBackReference
    private Cartao cartao;
}
