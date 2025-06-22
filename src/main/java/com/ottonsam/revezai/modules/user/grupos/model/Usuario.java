package com.ottonsam.revezai.modules.user.grupos.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<GrupoUsuario> grupos;

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

    public List<GrupoUsuario> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<GrupoUsuario> grupos) {
        this.grupos = grupos;
    }
}