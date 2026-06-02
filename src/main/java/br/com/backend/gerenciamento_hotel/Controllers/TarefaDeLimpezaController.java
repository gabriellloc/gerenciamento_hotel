package br.com.backend.gerenciamento_hotel.Controllers;
import br.com.backend.gerenciamento_hotel.DTOs.Response.TarefaDeLimpezaResponseDTO;
import br.com.backend.gerenciamento_hotel.Enums.StatusDaTarefa;
import br.com.backend.gerenciamento_hotel.Services.TarefaDeLimpezaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tarefas-limpeza")
public class TarefaDeLimpezaController {
    @Autowired private TarefaDeLimpezaService service;

    @PostMapping("/quarto/{quartoId}")
    public ResponseEntity<TarefaDeLimpezaResponseDTO> abrirTarefa(@PathVariable UUID quartoId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.abrirTarefaParaQuarto(quartoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaDeLimpezaResponseDTO> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<TarefaDeLimpezaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/quarto/{quartoId}")
    public ResponseEntity<List<TarefaDeLimpezaResponseDTO>> listarPorQuarto(@PathVariable UUID quartoId) {
        return ResponseEntity.ok(service.listarPorQuarto(quartoId));
    }

    @GetMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<List<TarefaDeLimpezaResponseDTO>> listarPorFuncionario(@PathVariable UUID funcionarioId) {
        return ResponseEntity.ok(service.listarPorFuncionario(funcionarioId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TarefaDeLimpezaResponseDTO>> listarPorStatus(@PathVariable StatusDaTarefa status) {
        return ResponseEntity.ok(service.listarPorStatus(status));
    }

    @PatchMapping("/{id}/atribuir/{funcionarioId}")
    public ResponseEntity<TarefaDeLimpezaResponseDTO> atribuirFuncionario(@PathVariable UUID id, @PathVariable UUID funcionarioId) {
        return ResponseEntity.ok(service.atribuirFuncionario(id, funcionarioId));
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<TarefaDeLimpezaResponseDTO> concluirTarefa(@PathVariable UUID id) {
        return ResponseEntity.ok(service.concluirTarefa(id));
    }
}