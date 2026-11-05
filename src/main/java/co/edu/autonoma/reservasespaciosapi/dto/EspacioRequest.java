package co.edu.autonoma.reservasespaciosapi.dto;

public record EspacioRequest(
        String nombre,
        String ubicacion,
        Integer capacidad,
        Boolean disponible
) { }
