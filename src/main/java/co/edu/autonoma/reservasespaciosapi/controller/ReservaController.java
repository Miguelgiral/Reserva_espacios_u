package co.edu.autonoma.reservasespaciosapi.controller;
import co.edu.autonoma.reservasespaciosapi.entity.ReservaEntity;
import co.edu.autonoma.reservasespaciosapi.service.ReservaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/reservas")
public class ReservaController{
    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService){
        this.reservaService = reservaService;
    }
    @GetMapping
    public List<ReservaEntity> obtenerTodas(){
        return reservaService.obtenerTodas();
    }
    @PostMapping
    public ReservaEntity CrearReserva(@RequestBody ReservaEntity reserva){
        return reservaService.guardarReserva(reserva);
    }
}
