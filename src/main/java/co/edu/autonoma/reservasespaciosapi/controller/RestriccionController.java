package co.edu.autonoma.reservasespaciosapi.controller;

import co.edu.autonoma.reservasespaciosapi.dto.RestriccionRequest;
import co.edu.autonoma.reservasespaciosapi.dto.RestriccionResponse;
import co.edu.autonoma.reservasespaciosapi.service.RestriccionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restricciones")
public class RestriccionController {

    private final RestriccionService restriccionService;

    public RestriccionController(RestriccionService restriccionService) {
        this.restriccionService = restriccionService;
    }

    @PostMapping
    public ResponseEntity<RestriccionResponse> crearRestriccion(@RequestBody RestriccionRequest request) {
        RestriccionResponse response = restriccionService.crearRestriccion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRestriccion(@PathVariable Long id) {
        restriccionService.eliminarRestriccion(id);
        return ResponseEntity.noContent().build();
    }
}