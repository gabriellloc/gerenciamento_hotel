package br.com.backend.gerenciamento_hotel.Controllers;

import br.com.backend.gerenciamento_hotel.DTOs.Request.HotelRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.HotelResponseDTO;
import br.com.backend.gerenciamento_hotel.Services.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/hoteis")
@Tag(name = "Hotéis", description = "Endpoints responsáveis pelo gerenciamento dos hotéis, permitindo cadastrar, consultar, atualizar e remover hotéis.")
public class HotelController {
    @Autowired
    private HotelService service;

    @PostMapping
    @Operation(summary = "Criar um hotel", description = "Endpoint para criar um novo hotel no sistema. Recebe os dados do hotel no corpo da requisição e retorna os detalhes do hotel criado.", tags = {
            "Hotéis" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Hotel criado com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
            })
    public ResponseEntity<HotelResponseDTO> criar(@Valid @RequestBody HotelRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um hotel por ID", description = "Endpoint para buscar um hotel específico pelo seu ID.", tags = {
            "Hotéis" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Hotel encontrado com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Hotel não encontrado")
            })
    public ResponseEntity<HotelResponseDTO> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os hotéis", description = "Endpoint para listar todos os hotéis do sistema.", tags = {
            "Hotéis" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Hotéis listados com sucesso")
            })
    public ResponseEntity<List<HotelResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um hotel por ID", description = "Endpoint para atualizar os dados de um hotel específico pelo seu ID.", tags = {
            "Hotéis" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Hotel atualizado com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Hotel não encontrado")
            })
    public ResponseEntity<HotelResponseDTO> atualizar(@PathVariable UUID id, @Valid @RequestBody HotelRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover um hotel por ID", description = "Endpoint para remover um hotel específico pelo seu ID.", tags = {
            "Hotéis" }, responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Hotel removido com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Hotel não encontrado")
            })
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}