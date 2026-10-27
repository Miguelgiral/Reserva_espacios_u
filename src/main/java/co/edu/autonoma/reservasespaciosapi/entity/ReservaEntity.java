package co.edu.autonoma.reservasespaciosapi.entity;
import java.time.LocalDateTime;
import co.edu.autonoma.reservasespaciosapi.domain.EstadoReserva;
import jakarta.persistence.*;
@Entity
@Table(name = "reservas")
public class ReservaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;
    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime fechaFin;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private EstadoReserva estado;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "espacio_id", nullable = false)
    private EspacioEntity espacio;
    protected ReservaEntity() { }
}
