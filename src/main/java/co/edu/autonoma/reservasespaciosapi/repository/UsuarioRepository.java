package co.edu.autonoma.reservasespaciosapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.autonoma.reservasespaciosapi.entity.UsuarioEntity;
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> { }
