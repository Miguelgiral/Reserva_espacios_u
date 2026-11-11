package co.edu.autonoma.reservasespaciosapi.dto;
import co.edu.autonoma.reservasespaciosapi.domain.TipoEspacio;
public record EspacioResponse(
        Long id,
        String nombre,
        TipoEspacio tipo,
        Integer capacidad,
        String ubicacion,
        Boolean activo
) { }