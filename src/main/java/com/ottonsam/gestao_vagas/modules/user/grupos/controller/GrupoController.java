package com.ottonsam.gestao_vagas.modules.user.grupos.controller;

import com.ottonsam.gestao_vagas.modules.user.grupos.model.Grupo;
import com.ottonsam.gestao_vagas.modules.user.grupos.service.GrupoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @PostMapping
    public Grupo criarGrupo(@RequestBody Grupo grupo) {
        return grupoService.criarGrupo(grupo);
    }

    @GetMapping
    public List<Grupo> listarGrupos() {
        return grupoService.listarGrupos();
    }

    @GetMapping("/{id}")
    public Grupo buscarGrupoPorId(@PathVariable Long id) {
        return grupoService.buscarGrupoPorId(id);
    }

    @PutMapping("/{id}")
    public Grupo atualizarGrupo(@PathVariable Long id, @RequestBody Grupo grupo) {
        return grupoService.atualizarGrupo(id, grupo);
    }

    @DeleteMapping("/{id}")
    public void deletarGrupo(@PathVariable Long id) {
        grupoService.deletarGrupo(id);
    }
}