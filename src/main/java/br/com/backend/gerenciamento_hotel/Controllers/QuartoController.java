package br.com.backend.gerenciamento_hotel.Controllers;

import br.com.backend.gerenciamento_hotel.DTOs.Request.QuartoRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.QuartoResponseDTO;
import br.com.backend.gerenciamento_hotel.Enums.TiposDeQuartos;
import br.com.backend.gerenciamento_hotel.Services.QuartoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Quarto", description = "Endpoints para gerenciamento de quartos")
public class QuartoController {
    @Autowired
    private QuartoService service;

    @PostMapping
    @Operation(summary = "Criar novo quarto", description = "Cria um novo quarto no sistema.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados para criar um novo quarto", required = true))
    public ResponseEntity<QuartoResponseDTO> criar(@Valid @RequestBody QuartoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar quarto por ID", description = "Retorna os detalhes de um quarto específico pelo seu ID.", parameters = @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID do quarto", required = true))
    public ResponseEntity<QuartoResponseDTO> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os quartos", description = "Retorna uma lista com todos os quartos do sistema.")
    public ResponseEntity<List<QuartoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/andar/{andarId}")
    @Operation(summary = "Listar quartos por andar", description = "Retorna uma lista com todos os quartos associados a um andar específico.", parameters = @io.swagger.v3.oas.annotations.Parameter(name = "andarId", description = "ID do andar", required = true))
    public ResponseEntity<List<QuartoResponseDTO>> listarPorAndar(@PathVariable UUID andarId) {
        return ResponseEntity.ok(service.listarPorAndar(andarId));
    }

    @GetMapping("/disponiveis")
    @Operation(summary = "Buscar quartos disponíveis", description = "Retorna uma lista de quartos disponíveis para reserva com base nos critérios de busca fornecidos.", parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "torreId", description = "ID da torre para filtrar os quartos", required = false),
            @io.swagger.v3.oas.annotations.Parameter(name = "tipo", description = "Tipo do quarto para filtrar os quartos", required = false),
            @io.swagger.v3.oas.annotations.Parameter(name = "capacidadeMinima", description = "Capacidade mínima do quarto para filtrar os quartos", required = false),
            @io.swagger.v3.oas.annotations.Parameter(name = "checkin", description = "Data de check-in para verificar a disponibilidade dos quartos (formato: YYYY-MM-DD)", required = true),
            @io.swagger.v3.oas.annotations.Parameter(name = "checkout", description = "Data de check-out para verificar a disponibilidade dos quartos (formato: YYYY-MM-DD)", required = true)
    })
    public ResponseEntity<List<QuartoResponseDTO>> buscarDisponiveis(
            @RequestParam(required = false) UUID torreId,
            @RequestParam(required = false) TiposDeQuartos tipo,
            @RequestParam(required = false) Integer capacidadeMinima,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkin,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkout) {
        return ResponseEntity.ok(service.buscarQuartosDisponiveis(torreId, tipo, capacidadeMinima, checkin, checkout));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar quarto", description = "Atualiza os detalhes de um quarto específico.", parameters = @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID do quarto a ser atualizado", required = true), requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados para atualizar o quarto", required = true))
    public ResponseEntity<QuartoResponseDTO> atualizar(@PathVariable UUID id,
            @Valid @RequestBody QuartoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar quarto", description = "Remove um quarto específico do sistema.", parameters = @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID do quarto a ser deletado", required = true))
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}