package co.edu.autonoma.reservasespaciosapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.autonoma.reservasespaciosapi.entity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    boolean existsByCorreoInstitucional(String correoInstitucional);
    Optional<UsuarioEntity> findByCorreoInstitucional(String correoInstitucional);
}