package co.edu.autonoma.reservasespaciosapi.dto;
import co.edu.autonoma.reservasespaciosapi.domain.TipoEspacio;
public record EspacioRequest(
        String nombre,
        TipoEspacio tipo,
        Integer capacidad,
        String ubicacion,
        Boolean activo
) { }