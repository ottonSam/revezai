package com.ottonsam.gestao_vagas.modules.user.grupos.repository;

import com.ottonsam.gestao_vagas.modules.user.grupos.model.GrupoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoUsuarioRepository extends JpaRepository<GrupoUsuario, Long> {
}
