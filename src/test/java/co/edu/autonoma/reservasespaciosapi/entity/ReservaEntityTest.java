package co.edu.autonoma.reservasespaciosapi.entity;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import co.edu.autonoma.reservasespaciosapi.domain.EstadoReserva;

class ReservaEntityTest {

    @Test
    void constructor_conDatosValidos_conservaDatosDeLaReserva() {
        LocalDateTime ini = LocalDateTime.now().plusDays(1);
        LocalDateTime fin = ini.plusHours(2);
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(1L);
        EspacioEntity espacio = new EspacioEntity();
        espacio.setId(10L);

        ReservaEntity reserva = new ReservaEntity(ini, fin, 4, EstadoReserva.CONFIRMADA, usuario, espacio);
        reserva.setId(100L);

        assertThat(reserva.getId()).isEqualTo(100L);
        assertThat(reserva.getUsuario()).isSameAs(usuario);
        assertThat(reserva.getEspacio()).isSameAs(espacio);
        assertThat(reserva.getCantidadAsistentes()).isEqualTo(4);
        assertThat(reserva.getEstado()).isEqualTo(EstadoReserva.CONFIRMADA);
        assertThat(reserva.getFechaInicio()).isEqualTo(ini);
        assertThat(reserva.getFechaFin()).isEqualTo(fin);
    }
}
