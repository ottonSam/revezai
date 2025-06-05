package com.ottonsam.gestao_vagas.modules.user.grupos.service;

import com.ottonsam.gestao_vagas.modules.user.grupos.model.Grupo;
import com.ottonsam.gestao_vagas.modules.user.grupos.repository.GrupoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    public Grupo criarGrupo(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    public List<Grupo> listarGrupos() {
        return grupoRepository.findAll();
    }

    public Grupo buscarGrupoPorId(Long id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));
    }

    public Grupo atualizarGrupo(Long id, Grupo grupoAtualizado) {
        Grupo grupo = buscarGrupoPorId(id);
        grupo.setNome(grupoAtualizado.getNome());
        grupo.setDescricao(grupoAtualizado.getDescricao());
        return grupoRepository.save(grupo);
    }

    public void deletarGrupo(Long id) {
        grupoRepository.deleteById(id);
    }
}
