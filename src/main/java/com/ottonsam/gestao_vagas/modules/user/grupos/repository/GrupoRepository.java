package com.ottonsam.gestao_vagas.modules.user.grupos.repository;

import com.ottonsam.gestao_vagas.modules.user.grupos.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
}
