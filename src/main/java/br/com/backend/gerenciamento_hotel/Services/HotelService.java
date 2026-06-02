package br.com.backend.gerenciamento_hotel.Services;
import br.com.backend.gerenciamento_hotel.Models.Hotel;
import br.com.backend.gerenciamento_hotel.DTOs.Request.HotelRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.HotelResponseDTO;
import br.com.backend.gerenciamento_hotel.Repositories.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HotelService {
    @Autowired private HotelRepository repository;

    public HotelResponseDTO criar(HotelRequestDTO dto) {
        Hotel entity = new Hotel();
        entity.setNome(dto.getNome());
        entity.setLocalizacao(dto.getLocalizacao());
        entity.setDescricao(dto.getDescricao());
        return toResponseDTO(repository.save(entity));
    }

    public HotelResponseDTO buscarPorId(UUID id) {
        return toResponseDTO(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel não encontrado")));
    }
    
    public Hotel buscarEntidade(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel não encontrado"));
    }

    public List<HotelResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public HotelResponseDTO atualizar(UUID id, HotelRequestDTO dto) {
        Hotel entity = buscarEntidade(id);
        entity.setNome(dto.getNome());
        entity.setLocalizacao(dto.getLocalizacao());
        entity.setDescricao(dto.getDescricao());
        return toResponseDTO(repository.save(entity));
    }

    public void deletar(UUID id) {
        repository.delete(buscarEntidade(id));
    }

    public HotelResponseDTO toResponseDTO(Hotel entity) {
        HotelResponseDTO dto = new HotelResponseDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setLocalizacao(entity.getLocalizacao());
        dto.setDescricao(entity.getDescricao());
        return dto;
    }
}