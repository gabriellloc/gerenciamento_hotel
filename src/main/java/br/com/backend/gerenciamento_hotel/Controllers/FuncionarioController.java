package br.com.backend.gerenciamento_hotel.Controllers;

import br.com.backend.gerenciamento_hotel.DTOs.Request.FuncionarioRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.FuncionarioResponseDTO;
import br.com.backend.gerenciamento_hotel.Services.FuncionarioService;
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
@Tag(name = "Funcionários", description = "Endpoints responsáveis pelo gerenciamento dos funcionários do hotel, permitindo cadastrar, consultar, atualizar e remover funcionários.")
@RequestMapping("/api/funcionarios")
public class FuncionarioController {
    @Autowired
    private FuncionarioService service;

    @PostMapping
    @Operation(summary = "Criar um funcionário", description = "Endpoint para criar um novo funcionário no hotel. Recebe os dados do funcionário no corpo da requisição e retorna os detalhes do funcionário criado.", tags = {
            "Funcionários" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Funcionário criado com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
            })
    public ResponseEntity<FuncionarioResponseDTO> criar(@Valid @RequestBody FuncionarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um funcionário por ID", description = "Endpoint para buscar um funcionário específico pelo seu ID.", tags = {
            "Funcionários" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Funcionário encontrado com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
            })
    public ResponseEntity<FuncionarioResponseDTO> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os funcionários", description = "Endpoint para listar todos os funcionários do hotel.", tags = {
            "Funcionários" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Funcionários listados com sucesso")
            })
    public ResponseEntity<List<FuncionarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um funcionário por ID", description = "Endpoint para atualizar os dados de um funcionário específico pelo seu ID.", tags = {
            "Funcionários" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
            })
    public ResponseEntity<FuncionarioResponseDTO> atualizar(@PathVariable UUID id,
            @Valid @RequestBody FuncionarioRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover um funcionário por ID", description = "Endpoint para remover um funcionário específico pelo seu ID.", tags = {
            "Funcionários" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Funcionário removido com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
            })
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}