package br.com.backend.gerenciamento_hotel.Controllers;
import br.com.backend.gerenciamento_hotel.DTOs.Request.ItemReservaRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.ItemReservaResponseDTO;
import br.com.backend.gerenciamento_hotel.Services.ItemReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/itens-reserva")
public class ItemReservaController {
    @Autowired private ItemReservaService service;

    @PostMapping
    public ResponseEntity<ItemReservaResponseDTO> criar(@Valid @RequestBody ItemReservaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }
    
    @GetMapping
    public ResponseEntity<List<ItemReservaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
}