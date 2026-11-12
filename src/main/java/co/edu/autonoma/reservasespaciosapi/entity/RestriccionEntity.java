package co.edu.autonoma.reservasespaciosapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "restricciones")
@Getter
@Setter
@NoArgsConstructor

public class RestriccionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "espacio_id", nullable = false)
    private EspacioEntity espacio;

    @Column(nullable = false)
    private LocalDateTime fechaInicio;

    @Column(nullable = false)
    private LocalDateTime fechiaFin;

    @Column(nullable = false)
    private String motivo;
}
