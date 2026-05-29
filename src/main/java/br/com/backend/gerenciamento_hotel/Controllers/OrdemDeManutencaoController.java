
package br.com.backend.gerenciamento_hotel.Controllers;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.OrdemDeManutencao;
import br.com.backend.gerenciamento_hotel.DTOs.Request.OrdemDeManutencaoRequestDTO;
import br.com.backend.gerenciamento_hotel.Enums.StatusManutencao;
import br.com.backend.gerenciamento_hotel.Services.OrdemDeManutencaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordens-manutencao")
public class OrdemDeManutencaoController {
    @Autowired private OrdemDeManutencaoService service;

    @PostMapping
    public ResponseEntity<OrdemDeManutencao> abrirOrdem(
            @Valid @RequestBody OrdemDeManutencaoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.abrirOrdem(dto.getQuartoId(), dto.getDescricaoProblema(), dto.getPrioridade()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemDeManutencao> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/quarto/{quartoId}")
    public ResponseEntity<List<OrdemDeManutencao>> listarPorQuarto(@PathVariable UUID quartoId) {
        return ResponseEntity.ok(service.listarPorQuarto(quartoId));
    }

    @GetMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<List<OrdemDeManutencao>> listarPorFuncionario(@PathVariable UUID funcionarioId) {
        return ResponseEntity.ok(service.listarPorFuncionario(funcionarioId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrdemDeManutencao>> listarPorStatus(@PathVariable StatusManutencao status) {
        return ResponseEntity.ok(service.listarPorStatus(status));
    }

    @PatchMapping("/{id}/atribuir/{funcionarioId}")
    public ResponseEntity<Void> atribuirFuncionario(@PathVariable UUID id, @PathVariable UUID funcionarioId) {
        service.atribuirTecnico(id, funcionarioId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<Void> concluirOrdem(@PathVariable UUID id) {
        service.concluirOrdem(id);
        return ResponseEntity.ok().build();
    }
}
