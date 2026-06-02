package br.com.backend.gerenciamento_hotel.Services;
import br.com.backend.gerenciamento_hotel.Models.Andar;
import br.com.backend.gerenciamento_hotel.DTOs.Request.AndarRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.AndarResponseDTO;
import br.com.backend.gerenciamento_hotel.Repositories.AndarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AndarService {
    @Autowired private AndarRepository repository;
    @Autowired private TorreService torreService;

    public AndarResponseDTO criar(AndarRequestDTO dto) {
        Andar entity = new Andar();
        entity.setNumero(dto.getNumero());
        entity.setTorre(torreService.buscarEntidade(dto.getTorreId()));
        return toResponseDTO(repository.save(entity));
    }

    public AndarResponseDTO buscarPorId(UUID id) {
        return toResponseDTO(buscarEntidade(id));
    }

    public Andar buscarEntidade(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Andar não encontrado"));
    }

    public List<AndarResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public AndarResponseDTO atualizar(UUID id, AndarRequestDTO dto) {
        Andar entity = buscarEntidade(id);
        entity.setNumero(dto.getNumero());
        entity.setTorre(torreService.buscarEntidade(dto.getTorreId()));
        return toResponseDTO(repository.save(entity));
    }

    public void deletar(UUID id) {
        repository.delete(buscarEntidade(id));
    }

    public AndarResponseDTO toResponseDTO(Andar entity) {
        AndarResponseDTO dto = new AndarResponseDTO();
        dto.setId(entity.getId());
        dto.setNumero(entity.getNumero());
        if(entity.getTorre() != null) {
            dto.setTorreId(entity.getTorre().getId());
            dto.setTorreNome(entity.getTorre().getNome());
        }
        return dto;
    }
}