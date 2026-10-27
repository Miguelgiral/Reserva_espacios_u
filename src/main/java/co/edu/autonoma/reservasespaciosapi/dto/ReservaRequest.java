package co.edu.autonoma.reservasespaciosapi.dto;

import java.time.LocalDateTime;
import co.edu.autonoma.reservasespaciosapi.domain.EstadoReserva;

public record ReservaRequest(
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        EstadoReserva estado,
        Long usuarioId,
        Long espacioId
) { }
