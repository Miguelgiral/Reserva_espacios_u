package co.edu.autonoma.reservasespaciosapi.dto;

import co.edu.autonoma.reservasespaciosapi.domain.RolUsuario;

public record UsuarioRequest(
        String nombre,
        String correoInstitucional,
        String contrasena,
        RolUsuario rol
) { }
