package co.edu.autonoma.reservasespaciosapi.entity;

import co.edu.autonoma.reservasespaciosapi.domain.TipoEspacio;
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
@Table(name = "espacios")
@Getter
@Setter
@NoArgsConstructor
public class EspacioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 50)
    private TipoEspacio tipo;

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @Column(name = "ubicacion", nullable = false, length = 180)
    private String ubicacion;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    public EspacioEntity(String nombre, TipoEspacio tipo, Integer capacidad, String ubicacion, Boolean activo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.activo = activo != null ? activo : true;
    }

    // Métodos alias para compatibilidad con código anterior
    public Boolean getDisponible() {
        return activo;
    }

    public void setDisponible(Boolean disponible) {
        this.activo = disponible;
    }
}