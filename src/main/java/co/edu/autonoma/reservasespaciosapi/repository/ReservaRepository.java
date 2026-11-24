package co.edu.autonoma.reservasespaciosapi.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.autonoma.reservasespaciosapi.domain.EstadoReserva;
import co.edu.autonoma.reservasespaciosapi.entity.ReservaEntity;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {

    @Query("""
        SELECT r FROM ReservaEntity r
        WHERE r.espacio.id = :espacioId
          AND r.estado <> :estadoCancelado
          AND r.fechaInicio < :fin
          AND r.fechaFin > :inicio
    """)
    List<ReservaEntity> buscarSolapamientos(
            @Param("espacioId") Long espacioId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin,
            @Param("estadoCancelado") EstadoReserva estadoCancelado
    );

    List<ReservaEntity> findByEspacioId(Long espacioId);

    List<ReservaEntity> findByUsuarioId(Long usuarioId);
}
