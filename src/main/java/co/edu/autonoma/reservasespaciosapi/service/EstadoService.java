package co.edu.autonoma.reservasespaciosapi.service;

import org.springframework.stereotype.Service;
import co.edu.autonoma.reservasespaciosapi.dto.EstadoResponse;

@Service
public class EstadoService {
    public EstadoResponse consultarEstado() {
        return new EstadoResponse(
                "reservas-espacios-api",
                "disponible"
        );
    }
}
