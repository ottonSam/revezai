package com.ottonsam.gestao_vagas.modules.user.grupos.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "grupo")
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    
    private String descricao;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private List<GrupoUsuario> membros;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<GrupoUsuario> getMembros() {
        return membros;
    }

    public void setMembros(List<GrupoUsuario> membros) {
        this.membros = membros;
    }
}
