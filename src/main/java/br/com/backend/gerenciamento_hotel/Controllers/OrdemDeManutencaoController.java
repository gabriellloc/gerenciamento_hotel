package br.com.backend.gerenciamento_hotel.Controllers;

import br.com.backend.gerenciamento_hotel.DTOs.Request.OrdemDeManutencaoRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.OrdemDeManutencaoResponseDTO;
import br.com.backend.gerenciamento_hotel.Enums.StatusManutencao;
import br.com.backend.gerenciamento_hotel.Services.OrdemDeManutencaoService;
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
@RequestMapping("/api/ordens-manutencao")
@Tag(name = "Ordem de Manutenção", description = "Endpoints para gerenciamento de ordens de manutenção")
public class OrdemDeManutencaoController {
    @Autowired
    private OrdemDeManutencaoService service;

    @PostMapping
    @Operation(summary = "Abrir nova ordem de manutenção", description = "Cria uma nova ordem de manutenção para um quarto específico.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados para criar uma nova ordem de manutenção", required = true))
    public ResponseEntity<OrdemDeManutencaoResponseDTO> abrirOrdem(
            @Valid @RequestBody OrdemDeManutencaoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.abrirOrdem(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ordem de manutenção por ID", description = "Retorna os detalhes de uma ordem de manutenção específica pelo seu ID.", parameters = @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID da ordem de manutenção", required = true))
    public ResponseEntity<OrdemDeManutencaoResponseDTO> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas as ordens de manutenção", description = "Retorna uma lista com todas as ordens de manutenção do sistema.", parameters = @io.swagger.v3.oas.annotations.Parameter(name = "status", description = "Filtrar por status da ordem de manutenção", required = false))
    public ResponseEntity<List<OrdemDeManutencaoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/quarto/{quartoId}")
    @Operation(summary = "Listar ordens de manutenção por quarto", description = "Retorna uma lista com todas as ordens de manutenção associadas a um quarto específico.", parameters = @io.swagger.v3.oas.annotations.Parameter(name = "quartoId", description = "ID do quarto", required = true))
    public ResponseEntity<List<OrdemDeManutencaoResponseDTO>> listarPorQuarto(@PathVariable UUID quartoId) {
        return ResponseEntity.ok(service.listarPorQuarto(quartoId));
    }

    @GetMapping("/funcionario/{funcionarioId}")
    @Operation(summary = "Listar ordens de manutenção por funcionário", description = "Retorna uma lista com todas as ordens de manutenção atribuídas a um funcionário específico.", parameters = @io.swagger.v3.oas.annotations.Parameter(name = "funcionarioId", description = "ID do funcionário", required = true))
    public ResponseEntity<List<OrdemDeManutencaoResponseDTO>> listarPorFuncionario(@PathVariable UUID funcionarioId) {
        return ResponseEntity.ok(service.listarPorFuncionario(funcionarioId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar ordens de manutenção por status", description = "Retorna uma lista com todas as ordens de manutenção com um determinado status.", parameters = @io.swagger.v3.oas.annotations.Parameter(name = "status", description = "Status da ordem de manutenção", required = true))
    public ResponseEntity<List<OrdemDeManutencaoResponseDTO>> listarPorStatus(@PathVariable StatusManutencao status) {
        return ResponseEntity.ok(service.listarPorStatus(status));
    }

    @PatchMapping("/{id}/atribuir/{funcionarioId}")
    @Operation(summary = "Atribuir funcionário à ordem de manutenção", description = "Atribui um funcionário a uma ordem de manutenção específica.", parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID da ordem de manutenção", required = true),
            @io.swagger.v3.oas.annotations.Parameter(name = "funcionarioId", description = "ID do funcionário", required = true)
    })
    public ResponseEntity<OrdemDeManutencaoResponseDTO> atribuirFuncionario(@PathVariable UUID id,
            @PathVariable UUID funcionarioId) {
        return ResponseEntity.ok(service.atribuirTecnico(id, funcionarioId));
    }

    @PatchMapping("/{id}/concluir")
    @Operation(summary = "Concluir ordem de manutenção", description = "Conclui uma ordem de manutenção específica.", parameters = @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID da ordem de manutenção", required = true))
    public ResponseEntity<OrdemDeManutencaoResponseDTO> concluirOrdem(@PathVariable UUID id) {
        return ResponseEntity.ok(service.concluirOrdem(id));
    }
}