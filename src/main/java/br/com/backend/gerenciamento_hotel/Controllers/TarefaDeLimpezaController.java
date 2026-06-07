package br.com.backend.gerenciamento_hotel.Controllers;

import br.com.backend.gerenciamento_hotel.DTOs.Response.TarefaDeLimpezaResponseDTO;
import br.com.backend.gerenciamento_hotel.Enums.StatusDaTarefa;
import br.com.backend.gerenciamento_hotel.Services.TarefaDeLimpezaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tarefas-limpeza")
@Tag(name = "Tarefas de Limpeza", description = "Endpoints para gerenciamento de tarefas de limpeza dos quartos")
public class TarefaDeLimpezaController {
    @Autowired
    private TarefaDeLimpezaService service;

    @PostMapping("/quarto/{quartoId}")
    @Operation(summary = "Abrir nova tarefa de limpeza para um quarto", description = "Cria uma nova tarefa de limpeza associada a um quarto específico. O status inicial da tarefa será 'Pendente'.", tags = {
            "Tarefas de Limpeza" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Tarefa de limpeza criada com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Quarto não encontrado")
            }, parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "quartoId", description = "ID do quarto para o qual a tarefa de limpeza será criada", required = true)
            }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nenhum corpo de requisição é necessário para esta operação", required = false))
    public ResponseEntity<TarefaDeLimpezaResponseDTO> abrirTarefa(@PathVariable UUID quartoId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.abrirTarefaParaQuarto(quartoId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tarefa de limpeza por ID", description = "Retorna os detalhes de uma tarefa de limpeza específica com base no seu ID.", tags = {
            "Tarefas de Limpeza" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Tarefa de limpeza encontrada"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Tarefa de limpeza não encontrada")
            }, parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID da tarefa de limpeza a ser buscada", required = true)
            })
    public ResponseEntity<TarefaDeLimpezaResponseDTO> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas as tarefas de limpeza", description = "Retorna uma lista com todas as tarefas de limpeza do sistema.", tags = {
            "Tarefas de Limpeza" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Tarefas de limpeza encontradas")
            })
    public ResponseEntity<List<TarefaDeLimpezaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/quarto/{quartoId}")
    @Operation(summary = "Listar tarefas de limpeza por quarto", description = "Retorna uma lista com todas as tarefas de limpeza associadas a um quarto específico.", tags = {
            "Tarefas de Limpeza" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Tarefas de limpeza encontradas")
            }, parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "quartoId", description = "ID do quarto para o qual as tarefas de limpeza serão listadas", required = true)
            })
    public ResponseEntity<List<TarefaDeLimpezaResponseDTO>> listarPorQuarto(@PathVariable UUID quartoId) {
        return ResponseEntity.ok(service.listarPorQuarto(quartoId));
    }

    @GetMapping("/funcionario/{funcionarioId}")
    @Operation(summary = "Listar tarefas de limpeza por funcionário", description = "Retorna uma lista com todas as tarefas de limpeza associadas a um funcionário específico.", tags = {
            "Tarefas de Limpeza" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Tarefas de limpeza encontradas")
            }, parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "funcionarioId", description = "ID do funcionário para o qual as tarefas de limpeza serão listadas", required = true)
            })
    public ResponseEntity<List<TarefaDeLimpezaResponseDTO>> listarPorFuncionario(@PathVariable UUID funcionarioId) {
        return ResponseEntity.ok(service.listarPorFuncionario(funcionarioId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar tarefas de limpeza por status", description = "Retorna uma lista com todas as tarefas de limpeza associadas a um status específico.", tags = {
            "Tarefas de Limpeza" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Tarefas de limpeza encontradas")
            }, parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "status", description = "Status das tarefas de limpeza a serem listadas", required = true)
            })
    public ResponseEntity<List<TarefaDeLimpezaResponseDTO>> listarPorStatus(@PathVariable StatusDaTarefa status) {
        return ResponseEntity.ok(service.listarPorStatus(status));
    }

    @PatchMapping("/{id}/atribuir/{funcionarioId}")
    @Operation(summary = "Atribuir funcionário a tarefa de limpeza", description = "Atribui um funcionário específico a uma tarefa de limpeza, alterando o status da tarefa para 'Em Andamento'.", tags = {
            "Tarefas de Limpeza" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Funcionário atribuído à tarefa de limpeza com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Tarefa de limpeza ou funcionário não encontrado")
            }, parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID da tarefa de limpeza à qual o funcionário será atribuído", required = true),
                    @io.swagger.v3.oas.annotations.Parameter(name = "funcionarioId", description = "ID do funcionário a ser atribuído à tarefa de limpeza", required = true)
            })
    public ResponseEntity<TarefaDeLimpezaResponseDTO> atribuirFuncionario(@PathVariable UUID id,
            @PathVariable UUID funcionarioId) {
        return ResponseEntity.ok(service.atribuirFuncionario(id, funcionarioId));
    }

    @PatchMapping("/{id}/concluir")
    @Operation(summary = "Concluir tarefa de limpeza", description = "Marca uma tarefa de limpeza específica como concluída, alterando seu status para 'Concluída'.", tags = {
            "Tarefas de Limpeza" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Tarefa de limpeza concluída com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Tarefa de limpeza não encontrada")
            }, parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID da tarefa de limpeza a ser concluída", required = true)
            })
    public ResponseEntity<TarefaDeLimpezaResponseDTO> concluirTarefa(@PathVariable UUID id) {
        return ResponseEntity.ok(service.concluirTarefa(id));
    }
}