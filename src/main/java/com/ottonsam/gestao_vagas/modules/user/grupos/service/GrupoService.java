package com.ottonsam.gestao_vagas.modules.user.grupos.service;

import com.ottonsam.gestao_vagas.modules.user.grupos.model.*;
import com.ottonsam.gestao_vagas.modules.user.grupos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GrupoUsuarioRepository grupoUsuarioRepository;

    public Grupo criarGrupo(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    public List<Grupo> listarGrupos() {
        return grupoRepository.findAll();
    }

    public Grupo buscarGrupoPorId(Long id) {
        return grupoRepository.findById(id).orElseThrow(() -> new RuntimeException("Grupo não encontrado"));
    }

    public Grupo atualizarGrupo(Long id, Grupo grupoAtualizado) {
        Grupo grupo = buscarGrupoPorId(id);
        grupo.setNome(grupoAtualizado.getNome());
        return grupoRepository.save(grupo);
    }

    public void deletarGrupo(Long id) {
        grupoRepository.deleteById(id);
    }

    public void convidarUsuario(Long grupoId, Long usuarioId) {
        Grupo grupo = buscarGrupoPorId(grupoId);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        GrupoUsuario grupoUsuario = new GrupoUsuario();
        grupoUsuario.setGrupo(grupo);
        grupoUsuario.setUsuario(usuario);
        grupoUsuario.setStatus(StatusConvite.PENDENTE);

        grupoUsuarioRepository.save(grupoUsuario);
    }

    public void aceitarConvite(Long grupoUsuarioId) {
        GrupoUsuario grupoUsuario = grupoUsuarioRepository.findById(grupoUsuarioId)
                .orElseThrow(() -> new RuntimeException("Convite não encontrado"));

        grupoUsuario.setStatus(StatusConvite.ACEITO);
        grupoUsuarioRepository.save(grupoUsuario);
    }

    public void recusarConvite(Long grupoUsuarioId) {
        GrupoUsuario grupoUsuario = grupoUsuarioRepository.findById(grupoUsuarioId)
                .orElseThrow(() -> new RuntimeException("Convite não encontrado"));

        grupoUsuario.setStatus(StatusConvite.RECUSADO);
        grupoUsuarioRepository.save(grupoUsuario);
    }
    
    public void adicionarUsuarioAoGrupo(Long grupoId, Long usuarioId) {
    convidarUsuario(grupoId, usuarioId);
    }
}
