package co.edu.autonoma.reservasespaciosapi.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import co.edu.autonoma.reservasespaciosapi.domain.EstadoReserva;
import co.edu.autonoma.reservasespaciosapi.dto.ReservaRequest;
import co.edu.autonoma.reservasespaciosapi.dto.ReservaResponse;
import co.edu.autonoma.reservasespaciosapi.entity.EspacioEntity;
import co.edu.autonoma.reservasespaciosapi.entity.ReservaEntity;
import co.edu.autonoma.reservasespaciosapi.entity.UsuarioEntity;
import co.edu.autonoma.reservasespaciosapi.repository.EspacioRepository;
import co.edu.autonoma.reservasespaciosapi.repository.ReservaRepository;
import co.edu.autonoma.reservasespaciosapi.repository.RestriccionRepository;
import co.edu.autonoma.reservasespaciosapi.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private EspacioRepository espacioRepository;
    @Mock
    private RestriccionRepository restriccionRepository;

    @InjectMocks
    private ReservaService reservaService;

    // -----------------------------------------------------------------
    // Helper objects
    // -----------------------------------------------------------------
    private UsuarioEntity usuario() {
        UsuarioEntity u = new UsuarioEntity();
        u.setId(1L);
        u.setNombre("Juan");
        return u;
    }

    private EspacioEntity espacio(boolean activo, int capacidad) {
        EspacioEntity e = new EspacioEntity();
        e.setId(10L);
        e.setNombre("Aula 1");
        e.setCapacidad(capacidad);
        e.setActivo(activo);
        return e;
    }

    private ReservaEntity reserva(LocalDateTime inicio, LocalDateTime fin) {
        ReservaEntity r = new ReservaEntity(inicio, fin, 5, EstadoReserva.CONFIRMADA, usuario(), espacio(true, 20));
        r.setId(100L);
        return r;
    }

    // -----------------------------------------------------------------
    // 1. crearReserva – happy path
    // -----------------------------------------------------------------
    @Test
    void crearReserva_happyPath_savesAndReturnsResponse() {
        // arrange
        LocalDateTime ini = LocalDateTime.now().plusDays(1);
        LocalDateTime fin = ini.plusHours(2);
        ReservaRequest req = new ReservaRequest(usuario().getId(), espacio(true, 20).getId(), ini, fin, 5);

        when(usuarioRepository.findById(usuario().getId())).thenReturn(Optional.of(usuario()));
        when(espacioRepository.findById(espacio(true, 20).getId())).thenReturn(Optional.of(espacio(true, 20)));
        when(restriccionRepository.findByEspacioIdAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                anyLong(), any(), any())).thenReturn(Collections.emptyList());
        when(reservaRepository.buscarSolapamientos(anyLong(), any(), any(), eq(EstadoReserva.CANCELADA)))
                .thenReturn(Collections.emptyList());
        when(reservaRepository.save(any(ReservaEntity.class))).thenAnswer(i -> i.getArgument(0));

        // act
        ReservaResponse resp = reservaService.crearReserva(req);

        // assert
        assertThat(resp.estado()).isEqualTo(EstadoReserva.CONFIRMADA);
        verify(reservaRepository).save(any(ReservaEntity.class));
    }

    // -----------------------------------------------------------------
    // 2. crearReserva – fecha fin anterior a inicio
    // -----------------------------------------------------------------
    @Test
    void crearReserva_fechaFinAntesDeInicio_throwsIllegalArgumentException() {
        LocalDateTime ini = LocalDateTime.now().plusDays(1);
        LocalDateTime fin = ini.minusHours(1);
        ReservaRequest req = new ReservaRequest(1L, 10L, ini, fin, 5);

        assertThrows(IllegalArgumentException.class,
                () -> reservaService.crearReserva(req),
                "La fecha final debe ser posterior a la fecha inicial (RN-04).");
    }

    // -----------------------------------------------------------------
    // 3. crearReserva – espacio inactivo
    // -----------------------------------------------------------------
    @Test
    void crearReserva_espacioInactivo_throwsIllegalStateException() {
        LocalDateTime ini = LocalDateTime.now().plusDays(1);
        LocalDateTime fin = ini.plusHours(2);
        ReservaRequest req = new ReservaRequest(usuario().getId(), 10L, ini, fin, 5);

        when(usuarioRepository.findById(usuario().getId())).thenReturn(Optional.of(usuario()));
        when(espacioRepository.findById(10L)).thenReturn(Optional.of(espacio(false, 20)));

        assertThrows(IllegalStateException.class,
                () -> reservaService.crearReserva(req),
                "No se puede reservar un espacio inactivo (RN-03).");
    }

    // -----------------------------------------------------------------
    // 4. crearReserva – solapamiento con reserva existente
    // -----------------------------------------------------------------
    @Test
    void crearReserva_solapamientoExistente_throwsIllegalStateException() {
        LocalDateTime ini = LocalDateTime.now().plusDays(1);
        LocalDateTime fin = ini.plusHours(2);
        ReservaRequest req = new ReservaRequest(usuario().getId(), 10L, ini, fin, 5);

        when(usuarioRepository.findById(usuario().getId())).thenReturn(Optional.of(usuario()));
        when(espacioRepository.findById(10L)).thenReturn(Optional.of(espacio(true, 20)));
        when(restriccionRepository.findByEspacioIdAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                anyLong(), any(), any())).thenReturn(Collections.emptyList());
        when(reservaRepository.buscarSolapamientos(eq(10L), any(), any(), eq(EstadoReserva.CANCELADA)))
                .thenReturn(List.of(reserva(ini.minusHours(1), ini.plusHours(1))));

        assertThrows(IllegalStateException.class,
                () -> reservaService.crearReserva(req),
                "El espacio ya cuenta con una reserva activa en esa franja horaria (RN-01).");
    }

    // -----------------------------------------------------------------
    // 5. obtenerPorId – éxito
    // -----------------------------------------------------------------
    @Test
    void obtenerPorId_existente_devuelveRespuesta() {
        ReservaEntity entity = reserva(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(2));
        when(reservaRepository.findById(entity.getId())).thenReturn(Optional.of(entity));

        ReservaResponse resp = reservaService.obtenerPorId(entity.getId());

        assertThat(resp.id()).isEqualTo(entity.getId());
        assertThat(resp.estado()).isEqualTo(EstadoReserva.CONFIRMADA);
    }

    // -----------------------------------------------------------------
    // 6. obtenerPorId – no encontrado
    // -----------------------------------------------------------------
    @Test
    void obtenerPorId_noExiste_lanzaIllegalArgumentException() {
        when(reservaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> reservaService.obtenerPorId(999L),
                "Reserva no encontrada con id: 999");
    }

    // -----------------------------------------------------------------
    // 7. cancelarReserva – antes del inicio (éxito) + verify
    // -----------------------------------------------------------------
    @Test
    void cancelarReserva_beforeStart_successAndVerifySave() {
        LocalDateTime ini = LocalDateTime.now().plusHours(5);
        LocalDateTime fin = ini.plusHours(2);
        ReservaEntity entidad = reserva(ini, fin);
        entidad.setEstado(EstadoReserva.CONFIRMADA);

        when(reservaRepository.findById(entidad.getId())).thenReturn(Optional.of(entidad));
        when(reservaRepository.save(any(ReservaEntity.class))).thenAnswer(i -> i.getArgument(0));

        ReservaResponse resp = reservaService.cancelarReserva(entidad.getId());

        assertThat(resp.estado()).isEqualTo(EstadoReserva.CANCELADA);
        verify(reservaRepository).save(entidad);
    }

    // -----------------------------------------------------------------
    // 8. cancelarReserva – después del inicio (error)
    // -----------------------------------------------------------------
    @Test
    void cancelarReserva_afterStart_throwsIllegalStateException() {
        LocalDateTime ini = LocalDateTime.now().minusHours(1);
        LocalDateTime fin = ini.plusHours(2);
        ReservaEntity entidad = reserva(ini, fin);
        entidad.setEstado(EstadoReserva.CONFIRMADA);

        when(reservaRepository.findById(entidad.getId())).thenReturn(Optional.of(entidad));

        assertThrows(IllegalStateException.class,
                () -> reservaService.cancelarReserva(entidad.getId()),
                "No es posible cancelar la reserva: ya ha iniciado o ha pasado su fecha de inicio (RN-05).");
    }

    // -----------------------------------------------------------------
    // 9. cancelarReserva – ya cancelada (error)
    // -----------------------------------------------------------------
    @Test
    void cancelarReserva_alreadyCancelled_throwsIllegalStateException() {
        LocalDateTime ini = LocalDateTime.now().plusHours(5);
        LocalDateTime fin = ini.plusHours(2);
        ReservaEntity entidad = reserva(ini, fin);
        entidad.setEstado(EstadoReserva.CANCELADA);

        when(reservaRepository.findById(entidad.getId())).thenReturn(Optional.of(entidad));

        assertThrows(IllegalStateException.class,
                () -> reservaService.cancelarReserva(entidad.getId()),
                "La reserva ya se encuentra cancelada.");
    }
}
