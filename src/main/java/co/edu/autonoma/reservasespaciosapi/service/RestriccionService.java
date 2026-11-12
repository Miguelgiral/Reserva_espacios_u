package co.edu.autonoma.reservasespaciosapi.service;

import co.edu.autonoma.reservasespaciosapi.dto.RestriccionRequest;
import co.edu.autonoma.reservasespaciosapi.dto.RestriccionResponse;
import co.edu.autonoma.reservasespaciosapi.entity.EspacioEntity;
import co.edu.autonoma.reservasespaciosapi.entity.RestriccionEntity;
import co.edu.autonoma.reservasespaciosapi.repository.EspacioRepository;
import co.edu.autonoma.reservasespaciosapi.repository.RestriccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestriccionService {

    private final RestriccionRepository restriccionRepository;
    private final EspacioRepository espacioRepository;

    public RestriccionService(RestriccionRepository restriccionRepository,
                              EspacioRepository espacioRepository) {
        this.restriccionRepository = restriccionRepository;
        this.espacioRepository = espacioRepository;
    }

    public RestriccionResponse crearRestriccion(RestriccionRequest request) {
        EspacioEntity espacio = espacioRepository.findById(request.getEspacioId())
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado con id: " + request.getEspacioId()));

        RestriccionEntity restriccion = new RestriccionEntity();
        restriccion.setEspacio(espacio);
        restriccion.setFechaInicio(request.getFechaInicio());
        restriccion.setFechiaFin(request.getFechaFin());
        restriccion.setMotivo(request.getMotivo());

        RestriccionEntity guardada = restriccionRepository.save(restriccion);
        return mapearRespuesta(guardada);
    }

    public void eliminarRestriccion(Long id) {
        if (!restriccionRepository.existsById(id)) {
            throw new RuntimeException("Restricción no encontrada con id: " + id);
        }
        restriccionRepository.deleteById(id);
    }

    private RestriccionResponse mapearRespuesta(RestriccionEntity entity) {
        RestriccionResponse response = new RestriccionResponse();
        response.setId(entity.getId());
        response.setEspacioId(entity.getEspacio().getId());
        response.setNombreEspacio(entity.getEspacio().getNombre());
        response.setFechaInicio(entity.getFechaInicio());
        response.setFechaFin(entity.getFechiaFin());
        response.setMotivo(entity.getMotivo());
        return response;
    }
}
