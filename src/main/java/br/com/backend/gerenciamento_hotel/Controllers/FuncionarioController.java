
package br.com.backend.gerenciamento_hotel.Controllers;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.Funcionario;
import br.com.backend.gerenciamento_hotel.Enums.CargoDosFuncionarios;
import br.com.backend.gerenciamento_hotel.Services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {
    @Autowired private FuncionarioService service;

    @PostMapping
    public ResponseEntity<Funcionario> criar(@RequestBody Funcionario funcionario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(funcionario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Funcionario>> listarPorHotel(@PathVariable UUID hotelId) {
        return ResponseEntity.ok(service.listarPorHotel(hotelId));
    }
    
    @GetMapping("/cargo/{cargo}")
    public ResponseEntity<List<Funcionario>> listarPorCargo(@PathVariable CargoDosFuncionarios cargo) {
        return ResponseEntity.ok(service.listarPorCargo(cargo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizar(@PathVariable UUID id, @RequestBody Funcionario funcionario) {
        return ResponseEntity.ok(service.atualizar(id, funcionario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
