package br.com.backend.gerenciamento_hotel.Controllers;

import br.com.backend.gerenciamento_hotel.DTOs.Request.ItemReservaRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.ItemReservaResponseDTO;
import br.com.backend.gerenciamento_hotel.Services.ItemReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/itens-reserva")
@Tag(name = "Itens de Reserva", description = "Endpoints responsáveis pelo gerenciamento dos itens de reserva, permitindo cadastrar e listar os itens relacionados às reservas do hotel.")
public class ItemReservaController {
    @Autowired
    private ItemReservaService service;

    @PostMapping
    @Operation(summary = "Criar um item de reserva", description = "Endpoint para criar um novo item de reserva no sistema. Recebe os dados do item de reserva no corpo da requisição e retorna os detalhes do item criado.", tags = {
            "Itens de Reserva" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Item de reserva criado com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
            })
    public ResponseEntity<ItemReservaResponseDTO> criar(@Valid @RequestBody ItemReservaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todos os itens de reserva", description = "Endpoint para listar todos os itens de reserva do sistema.", tags = {
            "Itens de Reserva" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Itens de reserva listados com sucesso")
            })
    public ResponseEntity<List<ItemReservaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
}