package co.edu.autonoma.reservasespaciosapi.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.autonoma.reservasespaciosapi.domain.TipoEspacio;
import co.edu.autonoma.reservasespaciosapi.dto.DisponibilidadResponse;
import co.edu.autonoma.reservasespaciosapi.dto.EspacioRequest;
import co.edu.autonoma.reservasespaciosapi.dto.EspacioResponse;
import co.edu.autonoma.reservasespaciosapi.service.EspacioService;

@RestController
@RequestMapping("/api/espacios")
public class EspacioController {

    private final EspacioService espacioService;

    public EspacioController(EspacioService espacioService) {
        this.espacioService = espacioService;
    }

    @PostMapping
    public ResponseEntity<EspacioResponse> crearEspacio(@RequestBody EspacioRequest request) {
        EspacioResponse nuevo = espacioService.crearEspacio(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @GetMapping
    public List<EspacioResponse> listarEspacios(
            @RequestParam(required = false) TipoEspacio tipo,
            @RequestParam(required = false) Integer capacidad,
            @RequestParam(required = false) Boolean activo) {
        return espacioService.listarEspacios(tipo, capacidad, activo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspacioResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(espacioService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspacioResponse> actualizarEspacio(
            @PathVariable Long id,
            @RequestBody EspacioRequest request) {
        return ResponseEntity.ok(espacioService.actualizarEspacio(id, request));
    }

    @GetMapping("/{id}/disponibilidad")
    public ResponseEntity<DisponibilidadResponse> consultarDisponibilidad(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(espacioService.consultarDisponibilidad(id, inicio, fin));
    }
}