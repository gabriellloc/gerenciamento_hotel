
package br.com.backend.gerenciamento_hotel.Controllers;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.Quarto;
import br.com.backend.gerenciamento_hotel.Enums.StatusDosQuartos;
import br.com.backend.gerenciamento_hotel.Enums.TiposDeQuartos;
import br.com.backend.gerenciamento_hotel.Services.QuartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quartos")
public class QuartoController {
    @Autowired private QuartoService service;

    @PostMapping
    public ResponseEntity<Quarto> criar(@RequestBody Quarto quarto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(quarto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quarto> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/andar/{andarId}")
    public ResponseEntity<List<Quarto>> listarPorAndar(@PathVariable UUID andarId) {
        return ResponseEntity.ok(service.listarPorAndar(andarId));
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<Quarto>> buscarDisponiveis(
            @RequestParam(required = false) UUID torreId,
            @RequestParam(required = false) TiposDeQuartos tipo,
            @RequestParam(required = false) Integer capacidadeMinima,
            @RequestParam LocalDate checkin,
            @RequestParam LocalDate checkout) {
        return ResponseEntity.ok(service.buscarQuartosDisponiveis(torreId, tipo, capacidadeMinima, checkin, checkout));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatus(@PathVariable UUID id, @RequestParam StatusDosQuartos status) {
        service.atualizarStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quarto> atualizar(@PathVariable UUID id, @RequestBody Quarto quarto) {
        return ResponseEntity.ok(service.atualizar(id, quarto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
