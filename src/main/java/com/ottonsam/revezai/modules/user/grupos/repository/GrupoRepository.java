package com.ottonsam.revezai.modules.user.grupos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ottonsam.revezai.modules.user.grupos.model.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
}
