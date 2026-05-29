
package br.com.backend.gerenciamento_hotel.Controllers;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.Andar;
import br.com.backend.gerenciamento_hotel.Services.AndarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/andares")
public class AndarController {
    @Autowired private AndarService service;

    @PostMapping
    public ResponseEntity<Andar> criar(@RequestBody Andar andar) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(andar));
    }

    @GetMapping("/torre/{torreId}")
    public ResponseEntity<List<Andar>> listarPorTorre(@PathVariable UUID torreId) {
        return ResponseEntity.ok(service.listarPorTorre(torreId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
