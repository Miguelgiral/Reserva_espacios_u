package co.edu.autonoma.reservasespaciosapi.service;

import co.edu.autonoma.reservasespaciosapi.entity.ReservaEntity;
import co.edu.autonoma.reservasespaciosapi.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

///
@Service
public class ReservaService{
    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository){
        this.reservaRepository = reservaRepository;
    }
    public List<ReservaEntity>obtenerTodas(){
        return reservaRepository.findAll();
    }
    public ReservaEntity guardarReserva(ReservaEntity reserva){
        return reservaRepository.save(reserva);
    }
}

