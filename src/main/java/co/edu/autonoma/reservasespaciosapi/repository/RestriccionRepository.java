package co.edu.autonoma.reservasespaciosapi.repository;

import co.edu.autonoma.reservasespaciosapi.entity.RestriccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RestriccionRepository extends JpaRepository<RestriccionEntity, Long> {

    List<RestriccionEntity> findByEspacioIdAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
            Long espacioId,
            LocalDateTime fechaFin,
            LocalDateTime fechaInicio
    );
}