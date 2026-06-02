package br.com.backend.gerenciamento_hotel.Services;
import br.com.backend.gerenciamento_hotel.Models.Torre;
import br.com.backend.gerenciamento_hotel.Models.Hotel;
import br.com.backend.gerenciamento_hotel.DTOs.Request.TorreRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.TorreResponseDTO;
import br.com.backend.gerenciamento_hotel.Repositories.TorreRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TorreService {
    @Autowired private TorreRepository repository;
    @Autowired private HotelService hotelService;

    public TorreResponseDTO criar(TorreRequestDTO dto) {
        Torre entity = new Torre();
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setHotel(hotelService.buscarEntidade(dto.getHotelId()));
        return toResponseDTO(repository.save(entity));
    }

    public TorreResponseDTO buscarPorId(UUID id) {
        return toResponseDTO(buscarEntidade(id));
    }

    public Torre buscarEntidade(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Torre não encontrada"));
    }

    public List<TorreResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public TorreResponseDTO atualizar(UUID id, TorreRequestDTO dto) {
        Torre entity = buscarEntidade(id);
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setHotel(hotelService.buscarEntidade(dto.getHotelId()));
        return toResponseDTO(repository.save(entity));
    }

    public void deletar(UUID id) {
        repository.delete(buscarEntidade(id));
    }

    public TorreResponseDTO toResponseDTO(Torre entity) {
        TorreResponseDTO dto = new TorreResponseDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setDescricao(entity.getDescricao());
        if(entity.getHotel() != null) {
            dto.setHotelId(entity.getHotel().getId());
            dto.setHotelNome(entity.getHotel().getNome());
        }
        return dto;
    }
}