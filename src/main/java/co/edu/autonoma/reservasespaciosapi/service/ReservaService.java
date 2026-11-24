package co.edu.autonoma.reservasespaciosapi.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.autonoma.reservasespaciosapi.domain.EstadoReserva;
import co.edu.autonoma.reservasespaciosapi.dto.ReservaRequest;
import co.edu.autonoma.reservasespaciosapi.dto.ReservaResponse;
import co.edu.autonoma.reservasespaciosapi.entity.EspacioEntity;
import co.edu.autonoma.reservasespaciosapi.entity.ReservaEntity;
import co.edu.autonoma.reservasespaciosapi.entity.RestriccionEntity;
import co.edu.autonoma.reservasespaciosapi.entity.UsuarioEntity;
import co.edu.autonoma.reservasespaciosapi.repository.EspacioRepository;
import co.edu.autonoma.reservasespaciosapi.repository.ReservaRepository;
import co.edu.autonoma.reservasespaciosapi.repository.RestriccionRepository;
import co.edu.autonoma.reservasespaciosapi.repository.UsuarioRepository;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspacioRepository espacioRepository;
    private final RestriccionRepository restriccionRepository;

    public ReservaService(ReservaRepository reservaRepository,
                          UsuarioRepository usuarioRepository,
                          EspacioRepository espacioRepository,
                          RestriccionRepository restriccionRepository) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.espacioRepository = espacioRepository;
        this.restriccionRepository = restriccionRepository;
    }

    public ReservaResponse crearReserva(ReservaRequest request) {
        // RN-04: La fecha final debe ser posterior a la fecha inicial
        if (request.fechaFin() == null || request.fechaInicio() == null ||
                !request.fechaFin().isAfter(request.fechaInicio())) {
            throw new IllegalArgumentException("La fecha final debe ser posterior a la fecha inicial (RN-04).");
        }

        // Buscar usuario
        UsuarioEntity usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + request.usuarioId()));

        // Buscar espacio
        EspacioEntity espacio = espacioRepository.findById(request.espacioId())
                .orElseThrow(() -> new IllegalArgumentException("Espacio no encontrado con id: " + request.espacioId()));

        // RN-03: No se puede reservar un espacio inactivo
        if (Boolean.FALSE.equals(espacio.getActivo())) {
            throw new IllegalStateException("No se puede reservar un espacio inactivo (RN-03).");
        }

        // RN-02: La cantidad prevista de asistentes no puede superar la capacidad del espacio
        if (request.cantidadAsistentes() == null || request.cantidadAsistentes() <= 0) {
            throw new IllegalArgumentException("La cantidad de asistentes debe ser mayor a 0 (RN-02).");
        }
        if (request.cantidadAsistentes() > espacio.getCapacidad()) {
            throw new IllegalArgumentException("La cantidad de asistentes (" + request.cantidadAsistentes() +
                    ") supera la capacidad máxima del espacio (" + espacio.getCapacidad() + ") (RN-02).");
        }

        // RN-03: No se puede reservar una franja bloqueada por restricciones
        List<RestriccionEntity> restriccionesSolapadas = restriccionRepository
                .findByEspacioIdAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                        espacio.getId(), request.fechaFin(), request.fechaInicio());
        if (!restriccionesSolapadas.isEmpty()) {
            throw new IllegalStateException("El espacio se encuentra bloqueado por una restricción en esa franja horaria (RN-03).");
        }

        // RN-01: Una reserva no puede solaparse con otra reserva activa del mismo espacio
        List<ReservaEntity> reservasSolapadas = reservaRepository.buscarSolapamientos(
                espacio.getId(), request.fechaInicio(), request.fechaFin(), EstadoReserva.CANCELADA);
        if (!reservasSolapadas.isEmpty()) {
            throw new IllegalStateException("El espacio ya cuenta con una reserva activa en esa franja horaria (RN-01).");
        }

        // Crear la reserva con estado CONFIRMADA
        ReservaEntity nuevaReserva = new ReservaEntity(
                request.fechaInicio(),
                request.fechaFin(),
                request.cantidadAsistentes(),
                EstadoReserva.CONFIRMADA,
                usuario,
                espacio
        );

        return mapearAResponse(reservaRepository.save(nuevaReserva));
    }

    public List<ReservaResponse> listarTodas() {
        return reservaRepository.findAll()
                .stream()
                .map(this::mapearAResponse)
                .toList();
    }

    public ReservaResponse obtenerPorId(Long id) {
        ReservaEntity reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada con id: " + id));
        return mapearAResponse(reserva);
    }

    public ReservaResponse cancelarReserva(Long id) {
        ReservaEntity reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada con id: " + id));

        // RN-05: Una reserva solo puede cancelarse antes de su inicio
        if (LocalDateTime.now().isAfter(reserva.getFechaInicio())) {
            throw new IllegalStateException("No es posible cancelar la reserva: ya ha iniciado o ha pasado su fecha de inicio (RN-05).");
        }

        if (reserva.getEstado() == EstadoReserva.CANCELADA) {
            throw new IllegalStateException("La reserva ya se encuentra cancelada.");
        }

        reserva.setEstado(EstadoReserva.CANCELADA);
        return mapearAResponse(reservaRepository.save(reserva));
    }

    private ReservaResponse mapearAResponse(ReservaEntity entity) {
        return new ReservaResponse(
                entity.getId(),
                entity.getUsuario().getId(),
                entity.getUsuario().getNombre(),
                entity.getEspacio().getId(),
                entity.getEspacio().getNombre(),
                entity.getFechaInicio(),
                entity.getFechaFin(),
                entity.getCantidadAsistentes(),
                entity.getEstado()
        );
    }
}

