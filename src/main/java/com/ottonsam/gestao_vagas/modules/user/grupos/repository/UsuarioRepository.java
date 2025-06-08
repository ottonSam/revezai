package com.ottonsam.gestao_vagas.modules.user.grupos.repository;

import com.ottonsam.gestao_vagas.modules.user.grupos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
