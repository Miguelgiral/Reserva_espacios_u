package co.edu.autonoma.reservasespaciosapi.dto;

import java.time.LocalDateTime;

import co.edu.autonoma.reservasespaciosapi.domain.EstadoReserva;

public record ReservaResponse(
        Long id,
        Long usuarioId,
        String nombreUsuario,
        Long espacioId,
        String nombreEspacio,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        Integer cantidadAsistentes,
        EstadoReserva estado
) { }