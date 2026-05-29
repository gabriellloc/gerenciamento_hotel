
package br.com.backend.gerenciamento_hotel.Controllers;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.Torre;
import br.com.backend.gerenciamento_hotel.Services.TorreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/torres")
public class TorreController {
    @Autowired private TorreService service;

    @PostMapping
    public ResponseEntity<Torre> criar(@RequestBody Torre torre) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(torre));
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Torre>> listarPorHotel(@PathVariable UUID hotelId) {
        return ResponseEntity.ok(service.listarPorHotel(hotelId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Torre> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Torre> atualizar(@PathVariable UUID id, @RequestBody Torre torre) {
        return ResponseEntity.ok(service.atualizar(id, torre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
