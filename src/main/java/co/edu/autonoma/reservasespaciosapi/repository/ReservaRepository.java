package co.edu.autonoma.reservasespaciosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.autonoma.reservasespaciosapi.entity.ReservaEntity;

public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {
}
