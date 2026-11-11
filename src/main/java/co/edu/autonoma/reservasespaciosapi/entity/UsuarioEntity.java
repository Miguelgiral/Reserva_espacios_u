package co.edu.autonoma.reservasespaciosapi.entity;

import co.edu.autonoma.reservasespaciosapi.domain.RolUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "correo_institucional", nullable = false, unique = true, length = 180)
    private String correoInstitucional;

    @Column(name = "contrasena", nullable = false, length = 255)
    private String contrasena;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false, length = 50)
    private RolUsuario rol;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    public UsuarioEntity(String nombre, String correoInstitucional, String contrasena, RolUsuario rol) {
        this.nombre = nombre;
        this.correoInstitucional = correoInstitucional;
        this.contrasena = contrasena;
        this.rol = rol;
        this.activo = true;
    }

    // Alias para compatibilidad con código que use correo
    public String getCorreo() {
        return correoInstitucional;
    }

    public void setCorreo(String correo) {
        this.correoInstitucional = correo;
    }
}
