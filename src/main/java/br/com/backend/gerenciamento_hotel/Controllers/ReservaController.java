package br.com.backend.gerenciamento_hotel.Controllers;
import br.com.backend.gerenciamento_hotel.DTOs.Request.ReservaRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.ReservaResponseDTO;
import br.com.backend.gerenciamento_hotel.Services.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    @Autowired private ReservaService service;

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> criar(@Valid @RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarReserva(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/hospede/{hospedeId}")
    public ResponseEntity<List<ReservaResponseDTO>> listarPorHospede(@PathVariable UUID hospedeId) {
        return ResponseEntity.ok(service.listarPorHospede(hospedeId));
    }

    @PostMapping("/{id}/checkin")
    public ResponseEntity<ReservaResponseDTO> checkin(@PathVariable UUID id) {
        return ResponseEntity.ok(service.realizarCheckin(id));
    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<ReservaResponseDTO> checkout(@PathVariable UUID id) {
        return ResponseEntity.ok(service.realizarCheckout(id));
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<ReservaResponseDTO> cancelar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.cancelarReserva(id));
    }
}