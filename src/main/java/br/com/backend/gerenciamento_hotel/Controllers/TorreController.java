package br.com.backend.gerenciamento_hotel.Controllers;
import br.com.backend.gerenciamento_hotel.DTOs.Request.TorreRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.TorreResponseDTO;
import br.com.backend.gerenciamento_hotel.Services.TorreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/torres")
public class TorreController {
    @Autowired private TorreService service;

    @PostMapping
    public ResponseEntity<TorreResponseDTO> criar(@Valid @RequestBody TorreRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TorreResponseDTO> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<TorreResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TorreResponseDTO> atualizar(@PathVariable UUID id, @Valid @RequestBody TorreRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}