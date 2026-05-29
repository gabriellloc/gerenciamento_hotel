
package br.com.backend.gerenciamento_hotel.Controllers;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.Reserva;
import br.com.backend.gerenciamento_hotel.DTOs.Request.ReservaRequestDTO;
import br.com.backend.gerenciamento_hotel.Services.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    @Autowired private ReservaService service;

    @PostMapping
    public ResponseEntity<Reserva> criar(@Valid @RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarReserva(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/hospede/{hospedeId}")
    public ResponseEntity<List<Reserva>> listarPorHospede(@PathVariable UUID hospedeId) {
        return ResponseEntity.ok(service.listarPorHospede(hospedeId));
    }

    @PostMapping("/{id}/checkin")
    public ResponseEntity<Void> checkin(@PathVariable UUID id) {
        service.realizarCheckin(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<Void> checkout(@PathVariable UUID id) {
        service.realizarCheckout(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable UUID id) {
        service.cancelarReserva(id);
        return ResponseEntity.ok().build();
    }
}
