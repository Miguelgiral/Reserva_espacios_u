package co.edu.autonoma.reservasespaciosapi.dto;

public record EspacioResponse(
        Long id,
        String nombre,
        String ubicacion,
        Integer capacidad,
        Boolean disponible
) { }
