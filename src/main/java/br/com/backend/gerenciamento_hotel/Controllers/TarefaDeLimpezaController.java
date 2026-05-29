
package br.com.backend.gerenciamento_hotel.Controllers;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.TarefaDeLimpeza;
import br.com.backend.gerenciamento_hotel.Enums.StatusDaTarefa;
import br.com.backend.gerenciamento_hotel.Services.TarefaDeLimpezaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas-limpeza")
public class TarefaDeLimpezaController {
    @Autowired private TarefaDeLimpezaService service;

    @PostMapping("/quarto/{quartoId}")
    public ResponseEntity<TarefaDeLimpeza> abrirParaQuarto(@PathVariable UUID quartoId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.abrirTarefaParaQuarto(quartoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaDeLimpeza> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/quarto/{quartoId}")
    public ResponseEntity<List<TarefaDeLimpeza>> listarPorQuarto(@PathVariable UUID quartoId) {
        return ResponseEntity.ok(service.listarPorQuarto(quartoId));
    }

    @GetMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<List<TarefaDeLimpeza>> listarPorFuncionario(@PathVariable UUID funcionarioId) {
        return ResponseEntity.ok(service.listarPorFuncionario(funcionarioId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TarefaDeLimpeza>> listarPorStatus(@PathVariable StatusDaTarefa status) {
        return ResponseEntity.ok(service.listarPorStatus(status));
    }

    @PatchMapping("/{id}/atribuir/{funcionarioId}")
    public ResponseEntity<Void> atribuirFuncionario(@PathVariable UUID id, @PathVariable UUID funcionarioId) {
        service.atribuirFuncionario(id, funcionarioId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<Void> concluirTarefa(@PathVariable UUID id) {
        service.concluirTarefa(id);
        return ResponseEntity.ok().build();
    }
}
