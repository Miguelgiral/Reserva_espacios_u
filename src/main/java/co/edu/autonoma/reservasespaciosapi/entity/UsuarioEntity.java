package co.edu.autonoma.reservasespaciosapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "correo_institucional", nullable = false, unique = true, length = 180)
    private String correoInstitucional;

    @Column(name = "contrasena", length = 255)
    private String contrasena;

    @Column(name = "rol", length = 50)
    private String rol;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    /**
     * Constructor vacío requerido por JPA.
     */
    protected UsuarioEntity() {
    }

    /**
     * Constructor compatible con DTOs de autenticación y registro.
     */
    public UsuarioEntity(String nombre, String correoInstitucional, String contrasena) {
        this.nombre = nombre;
        this.correoInstitucional = correoInstitucional;
        this.contrasena = contrasena;
        this.activo = true;
    }

    /**
     * Constructor completo.
     */
    public UsuarioEntity(String nombre, String correoInstitucional, String contrasena, String rol, Boolean activo) {
        this.nombre = nombre;
        this.correoInstitucional = correoInstitucional;
        this.contrasena = contrasena;
        this.rol = rol;
        this.activo = activo;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    // Alias para compatibilidad con código que use correo
    public String getCorreo() {
        return correoInstitucional;
    }

    public void setCorreo(String correo) {
        this.correoInstitucional = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
