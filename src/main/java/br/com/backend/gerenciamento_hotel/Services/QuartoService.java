package br.com.backend.gerenciamento_hotel.Services;
import br.com.backend.gerenciamento_hotel.Models.Quarto;
import br.com.backend.gerenciamento_hotel.DTOs.Request.QuartoRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.QuartoResponseDTO;
import br.com.backend.gerenciamento_hotel.Enums.StatusDosQuartos;
import br.com.backend.gerenciamento_hotel.Enums.TiposDeQuartos;
import br.com.backend.gerenciamento_hotel.Repositories.QuartoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuartoService {
    @Autowired private QuartoRepository repository;
    @Autowired private AndarService andarService;

    public QuartoResponseDTO criar(QuartoRequestDTO dto) {
        Quarto entity = new Quarto();
        entity.setNumero(dto.getNumero());
        entity.setTipo(dto.getTipo());
        entity.setCapacidade(dto.getCapacidade());
        entity.setValorDaDiaria(dto.getValorDaDiaria());
        entity.setStatus(dto.getStatus());
        entity.setAndar(andarService.buscarEntidade(dto.getAndarId()));
        return toResponseDTO(repository.save(entity));
    }

    public QuartoResponseDTO buscarPorId(UUID id) {
        return toResponseDTO(buscarEntidade(id));
    }

    public Quarto buscarEntidade(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Quarto não encontrado"));
    }

    public List<QuartoResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<QuartoResponseDTO> listarPorAndar(UUID andarId) {
        return repository.findByAndar_Id(andarId).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public QuartoResponseDTO atualizar(UUID id, QuartoRequestDTO dto) {
        Quarto entity = buscarEntidade(id);
        entity.setNumero(dto.getNumero());
        entity.setTipo(dto.getTipo());
        entity.setCapacidade(dto.getCapacidade());
        entity.setValorDaDiaria(dto.getValorDaDiaria());
        entity.setStatus(dto.getStatus());
        entity.setAndar(andarService.buscarEntidade(dto.getAndarId()));
        return toResponseDTO(repository.save(entity));
    }

    public void deletar(UUID id) {
        repository.delete(buscarEntidade(id));
    }

    public List<QuartoResponseDTO> buscarQuartosDisponiveis(UUID torreId, TiposDeQuartos tipo, Integer capacidadeMinima, LocalDate dataCheckin, LocalDate dataCheckout) {
        java.time.LocalDateTime checkinDateTime = dataCheckin.atStartOfDay();
        java.time.LocalDateTime checkoutDateTime = dataCheckout.atTime(23, 59, 59);
        List<Quarto> disponiveis = repository.findDisponiveisNoPeriodo(checkinDateTime, checkoutDateTime);

        if (torreId != null) {
            disponiveis = disponiveis.stream()
                .filter(q -> q.getAndar() != null && q.getAndar().getTorre() != null && q.getAndar().getTorre().getId().equals(torreId))
                .collect(Collectors.toList());
        }
        if (tipo != null) {
            disponiveis = disponiveis.stream().filter(q -> q.getTipo() == tipo).collect(Collectors.toList());
        }
        if (capacidadeMinima != null) {
            disponiveis = disponiveis.stream().filter(q -> q.getCapacidade() != null && q.getCapacidade() >= capacidadeMinima).collect(Collectors.toList());
        }
        return disponiveis.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public void atualizarStatus(UUID id, StatusDosQuartos status) {
        Quarto q = buscarEntidade(id);
        q.setStatus(status);
        repository.save(q);
    }

    public QuartoResponseDTO toResponseDTO(Quarto entity) {
        QuartoResponseDTO dto = new QuartoResponseDTO();
        dto.setId(entity.getId());
        dto.setNumero(entity.getNumero());
        dto.setTipo(entity.getTipo());
        dto.setCapacidade(entity.getCapacidade());
        dto.setValorDaDiaria(entity.getValorDaDiaria());
        dto.setStatus(entity.getStatus());
        if(entity.getAndar() != null) {
            dto.setAndarId(entity.getAndar().getId());
            dto.setAndarNumero(entity.getAndar().getNumero());
        }
        return dto;
    }
}