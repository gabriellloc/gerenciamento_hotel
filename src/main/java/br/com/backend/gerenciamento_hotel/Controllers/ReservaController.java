package br.com.backend.gerenciamento_hotel.Controllers;

import br.com.backend.gerenciamento_hotel.DTOs.Request.ReservaRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.ReservaResponseDTO;
import br.com.backend.gerenciamento_hotel.Services.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservas")
@Tag(name = "Reserva", description = "Endpoints para gerenciamento de reservas")
public class ReservaController {
    @Autowired
    private ReservaService service;

    @PostMapping
    @Operation(summary = "Criar nova reserva", description = "Cria uma nova reserva no sistema.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados para criar uma nova reserva", required = true))
    public ResponseEntity<ReservaResponseDTO> criar(@Valid @RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarReserva(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar reserva por ID", description = "Retorna os detalhes de uma reserva específica pelo seu ID.", parameters = @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID da reserva", required = true))
    public ResponseEntity<ReservaResponseDTO> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas as reservas", description = "Retorna uma lista com todas as reservas do sistema.")
    public ResponseEntity<List<ReservaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/hospede/{hospedeId}")
    @Operation(summary = "Listar reservas por hóspede", description = "Retorna uma lista com todas as reservas associadas a um hóspede específico.", parameters = @io.swagger.v3.oas.annotations.Parameter(name = "hospedeId", description = "ID do hóspede", required = true))
    public ResponseEntity<List<ReservaResponseDTO>> listarPorHospede(@PathVariable UUID hospedeId) {
        return ResponseEntity.ok(service.listarPorHospede(hospedeId));
    }

    @PostMapping("/{id}/checkin")
    @Operation(summary = "Realizar check-in", description = "Realiza o check-in de uma reserva específica, atualizando seu status para 'Em Andamento'.", parameters = @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID da reserva", required = true))
    public ResponseEntity<ReservaResponseDTO> checkin(@PathVariable UUID id) {
        return ResponseEntity.ok(service.realizarCheckin(id));
    }

    @PostMapping("/{id}/checkout")
    @Operation(summary = "Realizar check-out", description = "Realiza o check-out de uma reserva específica, atualizando seu status para 'Concluída'.", parameters = @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID da reserva", required = true))
    public ResponseEntity<ReservaResponseDTO> checkout(@PathVariable UUID id) {
        return ResponseEntity.ok(service.realizarCheckout(id));
    }

    @PostMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar reserva", description = "Cancela uma reserva específica, atualizando seu status para 'Cancelada'.", parameters = @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID da reserva", required = true))
    public ResponseEntity<ReservaResponseDTO> cancelar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.cancelarReserva(id));
    }
}