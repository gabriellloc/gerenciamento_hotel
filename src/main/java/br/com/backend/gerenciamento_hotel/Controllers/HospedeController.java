
package br.com.backend.gerenciamento_hotel.Controllers;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.Hospede;
import br.com.backend.gerenciamento_hotel.Services.HospedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospedes")
public class HospedeController {
    @Autowired private HospedeService service;

    @PostMapping
    public ResponseEntity<Hospede> criar(@RequestBody Hospede hospede) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(hospede));
    }

    @GetMapping
    public ResponseEntity<List<Hospede>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hospede> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hospede> atualizar(@PathVariable UUID id, @RequestBody Hospede hospede) {
        return ResponseEntity.ok(service.atualizar(id, hospede));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
