package com.ottonsam.revezai.modules.user.grupos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ottonsam.revezai.modules.user.grupos.model.GrupoUsuario;

public interface GrupoUsuarioRepository extends JpaRepository<GrupoUsuario, Long> {
}
