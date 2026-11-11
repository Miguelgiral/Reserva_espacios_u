package co.edu.autonoma.reservasespaciosapi.dto;

import co.edu.autonoma.reservasespaciosapi.domain.RolUsuario;

public record UsuarioResponse(
        Long id,
        String nombre,
        String correoInstitucional,
        RolUsuario rol,
        Boolean activo
) { }
