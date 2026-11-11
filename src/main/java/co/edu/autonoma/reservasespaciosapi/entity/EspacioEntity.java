package co.edu.autonoma.reservasespaciosapi.entity;

package co.edu.autonoma.reservasespaciosapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @Column(name = "ubicacion", nullable = false, length = 180)
    private String ubicacion;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    public EspacioEntity(String nombre, Integer capacidad, String ubicacion, Boolean activo) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.activo = activo != null ? activo : true;
    }

    public Boolean getDisponible() {
        return activo;
    }

    public void setDisponible(Boolean disponible) {
        this.activo = disponible;
    }
}
