package co.edu.autonoma.reservasespaciosapi.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.autonoma.reservasespaciosapi.domain.TipoEspacio;
import co.edu.autonoma.reservasespaciosapi.entity.EspacioEntity;

@Repository
public interface EspacioRepository extends JpaRepository<EspacioEntity, Long> {
    @Query("SELECT e FROM EspacioEntity e WHERE " +
            "(:tipo IS NULL OR e.tipo = :tipo) AND " +
            "(:capacidadMinima IS NULL OR e.capacidad >= :capacidadMinima) AND " +
            "(:activo IS NULL OR e.activo = :activo)")
    List<EspacioEntity> filtrarEspacios(
            @Param("tipo") TipoEspacio tipo,
            @Param("capacidadMinima") Integer capacidadMinima,
            @Param("activo") Boolean activo
    );
}
