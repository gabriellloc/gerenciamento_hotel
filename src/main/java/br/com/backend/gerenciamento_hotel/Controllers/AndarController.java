package br.com.backend.gerenciamento_hotel.Controllers;

import br.com.backend.gerenciamento_hotel.DTOs.Request.AndarRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.AndarResponseDTO;
import br.com.backend.gerenciamento_hotel.Services.AndarService;
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
@RequestMapping("/api/andares")
@Tag(name = "Andares", description = "Endpoints responsáveis pelo gerenciamento dos andares do hotel, permitindo cadastrar, consultar, atualizar e remover andares.")
public class AndarController {
    @Autowired
    private AndarService service;

    @PostMapping
    @Operation(summary = "Criar um andar", description = "Endpoint para criar um novo andar no hotel. Recebe os dados do andar no corpo da requisição e retorna os detalhes do andar criado.", tags = {
            "Andares" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Andar criado com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
            })
    public ResponseEntity<AndarResponseDTO> criar(@Valid @RequestBody AndarRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um andar por ID", description = "Endpoint para buscar um andar específico pelo seu ID.", tags = {
            "Andares" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Andar encontrado com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Andar não encontrado")
            })
    public ResponseEntity<AndarResponseDTO> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os andares", description = "Endpoint para listar todos os andares do hotel.", tags = {
            "Andares" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Andares listados com sucesso")
            })
    public ResponseEntity<List<AndarResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um andar por ID", description = "Endpoint para atualizar os dados de um andar específico pelo seu ID.", tags = {
            "Andares" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Andar atualizado com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Andar não encontrado")
            })
    public ResponseEntity<AndarResponseDTO> atualizar(@PathVariable UUID id, @Valid @RequestBody AndarRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover um andar por ID", description = "Endpoint para remover um andar específico pelo seu ID.", tags = {
            "Andares" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Andar removido com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Andar não encontrado")
            })
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}