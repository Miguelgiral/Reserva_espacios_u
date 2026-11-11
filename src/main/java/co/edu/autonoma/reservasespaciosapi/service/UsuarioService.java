package co.edu.autonoma.reservasespaciosapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.autonoma.reservasespaciosapi.dto.UsuarioRequest;
import co.edu.autonoma.reservasespaciosapi.dto.UsuarioResponse;
import co.edu.autonoma.reservasespaciosapi.entity.UsuarioEntity;
import co.edu.autonoma.reservasespaciosapi.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponse registrarUsuario(UsuarioRequest request) {
        if (usuarioRepository.existsByCorreoInstitucional(request.correoInstitucional())) {
            throw new IllegalArgumentException("Ya existe un usuario con el correo institucional: " + request.correoInstitucional());
        }

        UsuarioEntity usuario = new UsuarioEntity(
                request.nombre(),
                request.correoInstitucional(),
                request.contrasena(),
                request.rol()
        );

        UsuarioEntity guardado = usuarioRepository.save(usuario);
        return mapearAResponse(guardado);
    }

    public List<UsuarioResponse> obtenerTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::mapearAResponse)
                .toList();
    }

    public UsuarioResponse obtenerPorId(Long id) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el usuario con id: " + id));
        return mapearAResponse(usuario);
    }

    private UsuarioResponse mapearAResponse(UsuarioEntity entity) {
        return new UsuarioResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getCorreoInstitucional(),
                entity.getRol(),
                entity.getActivo()
        );
    }
}

