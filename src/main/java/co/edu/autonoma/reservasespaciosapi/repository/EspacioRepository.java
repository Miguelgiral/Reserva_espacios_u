package co.edu.autonoma.reservasespaciosapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.autonoma.reservasespaciosapi.entity.EspacioEntity;
public interface EspacioRepository extends JpaRepository<EspacioEntity, Long> { }
