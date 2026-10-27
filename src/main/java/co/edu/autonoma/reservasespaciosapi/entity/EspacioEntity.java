package co.edu.autonoma.reservasespaciosapi.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "espacios")
public class EspacioEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120)
    private String nombre;
    @Column(nullable = false, length = 180)
    private String ubicacion;
    @Column(nullable = false)
    private Integer capacidad;
    @Column(nullable = false)
    private Boolean disponible;
    protected EspacioEntity() { }
}
