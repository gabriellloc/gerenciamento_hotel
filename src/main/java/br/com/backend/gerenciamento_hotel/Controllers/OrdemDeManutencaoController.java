package br.com.backend.gerenciamento_hotel.Controllers;
import br.com.backend.gerenciamento_hotel.DTOs.Request.OrdemDeManutencaoRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.OrdemDeManutencaoResponseDTO;
import br.com.backend.gerenciamento_hotel.Enums.StatusManutencao;
import br.com.backend.gerenciamento_hotel.Services.OrdemDeManutencaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ordens-manutencao")
public class OrdemDeManutencaoController {
    @Autowired private OrdemDeManutencaoService service;

    @PostMapping
    public ResponseEntity<OrdemDeManutencaoResponseDTO> abrirOrdem(@Valid @RequestBody OrdemDeManutencaoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.abrirOrdem(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemDeManutencaoResponseDTO> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<OrdemDeManutencaoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/quarto/{quartoId}")
    public ResponseEntity<List<OrdemDeManutencaoResponseDTO>> listarPorQuarto(@PathVariable UUID quartoId) {
        return ResponseEntity.ok(service.listarPorQuarto(quartoId));
    }

    @GetMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<List<OrdemDeManutencaoResponseDTO>> listarPorFuncionario(@PathVariable UUID funcionarioId) {
        return ResponseEntity.ok(service.listarPorFuncionario(funcionarioId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrdemDeManutencaoResponseDTO>> listarPorStatus(@PathVariable StatusManutencao status) {
        return ResponseEntity.ok(service.listarPorStatus(status));
    }

    @PatchMapping("/{id}/atribuir/{funcionarioId}")
    public ResponseEntity<OrdemDeManutencaoResponseDTO> atribuirFuncionario(@PathVariable UUID id, @PathVariable UUID funcionarioId) {
        return ResponseEntity.ok(service.atribuirTecnico(id, funcionarioId));
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<OrdemDeManutencaoResponseDTO> concluirOrdem(@PathVariable UUID id) {
        return ResponseEntity.ok(service.concluirOrdem(id));
    }
}