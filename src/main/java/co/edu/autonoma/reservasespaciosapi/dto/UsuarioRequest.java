package co.edu.autonoma.reservasespaciosapi.dto;

public record UsuarioRequest(
        String nombre,
        String correoInstitucional,
        String contrasena
) { }
