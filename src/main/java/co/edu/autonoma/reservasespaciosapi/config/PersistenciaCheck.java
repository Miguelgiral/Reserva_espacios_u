package co.edu.autonoma.reservasespaciosapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import co.edu.autonoma.reservasespaciosapi.repository.ReservaRepository;

@Component
public class PersistenciaCheck implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(PersistenciaCheck.class);

    private final ReservaRepository reservaRepository;

    public PersistenciaCheck(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Override
    public void run(String... args) {
        log.info("Persistencia PostgreSQL disponible. Total de reservas: {}", reservaRepository.count());
    }
}
