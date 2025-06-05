package com.ottonsam.gestao_vagas.modules.user.grupos.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;
}

