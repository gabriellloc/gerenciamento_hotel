package br.com.backend.gerenciamento_hotel.Controllers;

import br.com.backend.gerenciamento_hotel.DTOs.Request.HospedeRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.HospedeResponseDTO;
import br.com.backend.gerenciamento_hotel.Services.HospedeService;
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
@Tag(name = "Hóspedes", description = "Endpoints responsáveis pelo gerenciamento dos hóspedes do hotel, permitindo cadastrar, consultar, atualizar e remover hóspedes.")
@RequestMapping("/api/hospedes")
public class HospedeController {
    @Autowired
    private HospedeService service;

    @PostMapping
    @Operation(summary = "Criar um hóspede", description = "Endpoint para criar um novo hóspede no hotel. Recebe os dados do hóspede no corpo da requisição e retorna os detalhes do hóspede criado.", tags = {
            "Hóspedes" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Hóspede criado com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
            })
    public ResponseEntity<HospedeResponseDTO> criar(@Valid @RequestBody HospedeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um hóspede por ID", description = "Endpoint para buscar um hóspede específico pelo seu ID.", tags = {
            "Hóspedes" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Hóspede encontrado com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Hóspede não encontrado")
            })
    public ResponseEntity<HospedeResponseDTO> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os hóspedes", description = "Endpoint para listar todos os hóspedes do hotel.", tags = {
            "Hóspedes" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Hóspedes listados com sucesso")
            })
    public ResponseEntity<List<HospedeResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um hóspede por ID", description = "Endpoint para atualizar os dados de um hóspede específico pelo seu ID.", tags = {
            "Hóspedes" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Hóspede atualizado com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Hóspede não encontrado")
            })
    public ResponseEntity<HospedeResponseDTO> atualizar(@PathVariable UUID id,
            @Valid @RequestBody HospedeRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover um hóspede por ID", description = "Endpoint para remover um hóspede específico pelo seu ID.", tags = {
            "Hóspedes" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Hóspede removido com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Hóspede não encontrado")
            })
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}