package br.com.backend.gerenciamento_hotel.Controllers;

import br.com.backend.gerenciamento_hotel.DTOs.Request.TorreRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.TorreResponseDTO;
import br.com.backend.gerenciamento_hotel.Services.TorreService;
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
@RequestMapping("/api/torres")
@Tag(name = "Torres", description = "Endpoints para gerenciamento das torres do hotel")
public class TorreController {
    @Autowired
    private TorreService service;

    @PostMapping
    @Operation(summary = "Criar nova torre", description = "Cria uma nova torre no sistema com base nos dados fornecidos. O nome da torre deve ser único.", tags = {
            "Torres" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Torre criada com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos ou nome da torre já existente")
            }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados necessários para criar uma nova torre", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = TorreRequestDTO.class))))
    public ResponseEntity<TorreResponseDTO> criar(@Valid @RequestBody TorreRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar torre por ID", description = "Retorna os detalhes de uma torre específica com base no seu ID.", tags = {
            "Torres" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Torre encontrada"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Torre não encontrada")
            }, parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID da torre a ser buscada", required = true)
            })
    public ResponseEntity<TorreResponseDTO> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas as torres", description = "Retorna uma lista com todas as torres do sistema.", tags = {
            "Torres" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Torres encontradas")
            }, parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "page", description = "Número da página para paginação (opcional)", required = false),
                    @io.swagger.v3.oas.annotations.Parameter(name = "size", description = "Tamanho da página para paginação (opcional)", required = false)
            }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nenhum corpo de requisição é necessário para esta operação", required = false))
    public ResponseEntity<List<TorreResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar torre", description = "Atualiza os dados de uma torre existente com base no seu ID. O nome da torre deve ser único.", tags = {
            "Torres" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Torre atualizada com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos ou nome da torre já existente"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Torre não encontrada")
            }, parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID da torre a ser atualizada", required = true)
            }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados necessários para atualizar a torre", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = TorreRequestDTO.class))))
    public ResponseEntity<TorreResponseDTO> atualizar(@PathVariable UUID id, @Valid @RequestBody TorreRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar torre", description = "Remove uma torre do sistema com base no seu ID.", tags = {
            "Torres" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Torre deletada com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Torre não encontrada")
            }, parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID da torre a ser deletada", required = true)
            })
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}