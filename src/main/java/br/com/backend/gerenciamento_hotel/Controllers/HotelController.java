package br.com.backend.gerenciamento_hotel.Controllers;
import br.com.backend.gerenciamento_hotel.DTOs.Request.HotelRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.HotelResponseDTO;
import br.com.backend.gerenciamento_hotel.Services.HotelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/hoteis")
public class HotelController {
    @Autowired private HotelService service;

    @PostMapping
    public ResponseEntity<HotelResponseDTO> criar(@Valid @RequestBody HotelRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponseDTO> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<HotelResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelResponseDTO> atualizar(@PathVariable UUID id, @Valid @RequestBody HotelRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}