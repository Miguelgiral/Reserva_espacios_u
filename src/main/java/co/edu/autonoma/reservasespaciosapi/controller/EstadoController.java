package co.edu.autonoma.reservasespaciosapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.autonoma.reservasespaciosapi.dto.EstadoResponse;
import co.edu.autonoma.reservasespaciosapi.service.EstadoService;

@RestController
@RequestMapping("/api/estado")
public class EstadoController {
    private final EstadoService estadoService;

    public EstadoController(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    @GetMapping
    public EstadoResponse consultarEstado() {
        return estadoService.consultarEstado();
    }
}