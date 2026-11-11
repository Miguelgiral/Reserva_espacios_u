package co.edu.autonoma.reservasespaciosapi.dto;
import java.time.LocalDateTime;
public record DisponibilidadResponse(
        Long espacioId,
        String nombreEspacio,
        LocalDateTime inicio,
        LocalDateTime fin,
        boolean disponible,
        String mensaje
) { }


