package com.ottonsam.revezai.modules.user.grupos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ottonsam.revezai.modules.user.grupos.dto.ConviteRequest;
import com.ottonsam.revezai.modules.user.grupos.model.Grupo;
import com.ottonsam.revezai.modules.user.grupos.service.GrupoService;

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

     @PostMapping("/convidar")
    public void convidarUsuario(@RequestBody ConviteRequest conviteRequest) {
        grupoService.convidarUsuario(conviteRequest.getGrupoId(), conviteRequest.getUsuarioId());
    }

    @PostMapping("/convite/{id}/aceitar")
    public void aceitarConvite(@PathVariable Long id) {
        grupoService.aceitarConvite(id);
    }

    @PostMapping("/convite/{id}/recusar")
    public void recusarConvite(@PathVariable Long id) {
        grupoService.recusarConvite(id);
    }

    @PostMapping("/{grupoId}/convidar/{userId}")
public ResponseEntity<String> convidarUsuarioParaGrupo(@PathVariable Long grupoId, @PathVariable Long userId) {
    try {
        grupoService.adicionarUsuarioAoGrupo(grupoId, userId);
        return ResponseEntity.ok("Usuário adicionado ao grupo com sucesso!");
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
}