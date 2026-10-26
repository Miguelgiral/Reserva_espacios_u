package co.edu.autonoma.reservasespaciosapi.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "usuarios")
public class UsuarioEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120)
    private String nombre;
    @Column(nullable = false, unique = true, length = 180)
    private String correoInstitucional;
    @Column(nullable = false, length = 255)
    private String contrasena;
    protected UsuarioEntity() { }
}
