package co.edu.autonoma.reservasespaciosapi.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.autonoma.reservasespaciosapi.domain.TipoEspacio;
import co.edu.autonoma.reservasespaciosapi.dto.DisponibilidadResponse;
import co.edu.autonoma.reservasespaciosapi.dto.EspacioRequest;
import co.edu.autonoma.reservasespaciosapi.dto.EspacioResponse;
import co.edu.autonoma.reservasespaciosapi.entity.EspacioEntity;
import co.edu.autonoma.reservasespaciosapi.repository.EspacioRepository;

@Service
public class EspacioService {

    private final EspacioRepository espacioRepository;

    public EspacioService(EspacioRepository espacioRepository) {
        this.espacioRepository = espacioRepository;
    }

    public EspacioResponse crearEspacio(EspacioRequest request) {
        if (request.capacidad() == null || request.capacidad() <= 0) {
            throw new IllegalArgumentException("La capacidad del espacio debe ser mayor a 0.");
        }

        EspacioEntity nuevo = new EspacioEntity(
                request.nombre(),
                request.tipo(),
                request.capacidad(),
                request.ubicacion(),
                request.activo()
        );

        return mapearAResponse(espacioRepository.save(nuevo));
    }

    public List<EspacioResponse> listarEspacios(TipoEspacio tipo, Integer capacidadMinima, Boolean activo) {
        return espacioRepository.filtrarEspacios(tipo, capacidadMinima, activo)
                .stream()
                .map(this::mapearAResponse)
                .toList();
    }

    public EspacioResponse obtenerPorId(Long id) {
        EspacioEntity espacio = espacioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el espacio con id: " + id));
        return mapearAResponse(espacio);
    }

    public EspacioResponse actualizarEspacio(Long id, EspacioRequest request) {
        EspacioEntity espacio = espacioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el espacio con id: " + id));

        if (request.capacidad() == null || request.capacidad() <= 0) {
            throw new IllegalArgumentException("La capacidad del espacio debe ser mayor a 0.");
        }

        espacio.setNombre(request.nombre());
        espacio.setTipo(request.tipo());
        espacio.setCapacidad(request.capacidad());
        espacio.setUbicacion(request.ubicacion());
        if (request.activo() != null) {
            espacio.setActivo(request.activo());
        }

        return mapearAResponse(espacioRepository.save(espacio));
    }

    public DisponibilidadResponse consultarDisponibilidad(Long id, LocalDateTime inicio, LocalDateTime fin) {
        EspacioEntity espacio = espacioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el espacio con id: " + id));

        if (fin.isBefore(inicio) || fin.isEqual(inicio)) {
            throw new IllegalArgumentException("La fecha final debe ser posterior a la fecha inicial (RN-04).");
        }

        if (!espacio.getActivo()) {
            return new DisponibilidadResponse(id, espacio.getNombre(), inicio, fin, false, "El espacio se encuentra inactivo para reservas (RN-03).");
        }

        return new DisponibilidadResponse(id, espacio.getNombre(), inicio, fin, true, "Espacio disponible para reservar.");
    }

    private EspacioResponse mapearAResponse(EspacioEntity entity) {
        return new EspacioResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getTipo(),
                entity.getCapacidad(),
                entity.getUbicacion(),
                entity.getActivo()
        );
    }
}
