
package br.com.backend.gerenciamento_hotel.Controllers;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.Hotel;
import br.com.backend.gerenciamento_hotel.Services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hoteis")
public class HotelController {
    @Autowired private HotelService service;

    @PostMapping
    public ResponseEntity<Hotel> criar(@RequestBody Hotel hotel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(hotel));
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> atualizar(@PathVariable UUID id, @RequestBody Hotel hotel) {
        return ResponseEntity.ok(service.atualizar(id, hotel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
