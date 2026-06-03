package br.com.backend.gerenciamento_hotel.Services;
import br.com.backend.gerenciamento_hotel.Models.ItemReserva;
import br.com.backend.gerenciamento_hotel.DTOs.Request.ItemReservaRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.ItemReservaResponseDTO;
import br.com.backend.gerenciamento_hotel.Repositories.ItemReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemReservaService {
    @Autowired private ItemReservaRepository repository;
    @Lazy @Autowired private ReservaService reservaService;
    @Autowired private QuartoService quartoService;

    public ItemReservaResponseDTO criar(ItemReservaRequestDTO dto) {
        ItemReserva entity = new ItemReserva();
        entity.setValorDiaria(dto.getValorDiaria());
        entity.setObservacoes(dto.getObservacoes());
        entity.setQuarto(quartoService.buscarEntidade(dto.getQuartoId()));
        // Note: Reserva linking logically goes here, assuming lazy is OK
        entity.setReserva(reservaService.buscarEntidade(dto.getReservaId()));
        return toResponseDTO(repository.save(entity));
    }

    public ItemReserva buscarEntidade(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item não encontrado"));
    }

    public List<ItemReservaResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public ItemReservaResponseDTO toResponseDTO(ItemReserva entity) {
        ItemReservaResponseDTO dto = new ItemReservaResponseDTO();
        dto.setId(entity.getId());
        dto.setValorDiaria(entity.getValorDiaria());
        dto.setObservacoes(entity.getObservacoes());
        if(entity.getQuarto() != null) {
            dto.setQuartoId(entity.getQuarto().getId());
            dto.setQuartoNumero(entity.getQuarto().getNumero());
        }
        if(entity.getReserva() != null) {
            dto.setReservaId(entity.getReserva().getId());
        }
        return dto;
    }
}