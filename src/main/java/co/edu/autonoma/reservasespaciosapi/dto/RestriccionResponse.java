package co.edu.autonoma.reservasespaciosapi.dto;

import java.time.LocalDateTime;

public class RestriccionResponse {

    private Long id;
    private Long espacioId;
    private String nombreEspacio;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String motivo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEspacioId() { return espacioId; }
    public void setEspacioId(Long espacioId) { this.espacioId = espacioId; }

    public String getNombreEspacio() { return nombreEspacio; }
    public void setNombreEspacio(String nombreEspacio) { this.nombreEspacio = nombreEspacio; }

    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
