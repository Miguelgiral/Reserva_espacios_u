package co.edu.autonoma.reservasespaciosapi.dto;

import java.time.LocalDateTime;
import co.edu.autonoma.reservasespaciosapi.domain.EstadoReserva;

public record ReservaRequest(
        Long usuarioId,
        Long espacioId,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        Integer cantidadAsistentes
) { }
