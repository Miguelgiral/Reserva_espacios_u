package co.edu.autonoma.reservasespaciosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.autonoma.reservasespaciosapi.entity.EspacioEntity;

@Repository
public interface EspacioRepository extends JpaRepository<EspacioEntity, Long> {
}
