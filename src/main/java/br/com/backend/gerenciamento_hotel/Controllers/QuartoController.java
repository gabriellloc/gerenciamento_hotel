package br.com.backend.gerenciamento_hotel.Controllers;
import br.com.backend.gerenciamento_hotel.DTOs.Request.QuartoRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.QuartoResponseDTO;
import br.com.backend.gerenciamento_hotel.Enums.TiposDeQuartos;
import br.com.backend.gerenciamento_hotel.Services.QuartoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/quartos")
public class QuartoController {
    @Autowired private QuartoService service;

    @PostMapping
    public ResponseEntity<QuartoResponseDTO> criar(@Valid @RequestBody QuartoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuartoResponseDTO> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<QuartoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/andar/{andarId}")
    public ResponseEntity<List<QuartoResponseDTO>> listarPorAndar(@PathVariable UUID andarId) {
        return ResponseEntity.ok(service.listarPorAndar(andarId));
    }
    
    @GetMapping("/disponiveis")
    public ResponseEntity<List<QuartoResponseDTO>> buscarDisponiveis(
            @RequestParam(required = false) UUID torreId,
            @RequestParam(required = false) TiposDeQuartos tipo,
            @RequestParam(required = false) Integer capacidadeMinima,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkin,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkout) {
        return ResponseEntity.ok(service.buscarQuartosDisponiveis(torreId, tipo, capacidadeMinima, checkin, checkout));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuartoResponseDTO> atualizar(@PathVariable UUID id, @Valid @RequestBody QuartoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}