package br.com.backend.gerenciamento_hotel.Services;
import br.com.backend.gerenciamento_hotel.Models.Hospede;
import br.com.backend.gerenciamento_hotel.DTOs.Request.HospedeRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.HospedeResponseDTO;
import br.com.backend.gerenciamento_hotel.Repositories.HospedeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HospedeService {
    @Autowired private HospedeRepository repository;

    public HospedeResponseDTO criar(HospedeRequestDTO dto) {
        Hospede entity = new Hospede();
        entity.setNome(dto.getNome());
        entity.setCPF(dto.getDocumento());
        entity.setTelefone(dto.getTelefone());
        entity.setEmail(dto.getEmail());
        return toResponseDTO(repository.save(entity));
    }

    public HospedeResponseDTO buscarPorId(UUID id) {
        return toResponseDTO(buscarEntidade(id));
    }

    public Hospede buscarEntidade(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hóspede não encontrado"));
    }

    public List<HospedeResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public HospedeResponseDTO atualizar(UUID id, HospedeRequestDTO dto) {
        Hospede entity = buscarEntidade(id);
        entity.setNome(dto.getNome());
        entity.setCPF(dto.getDocumento());
        entity.setTelefone(dto.getTelefone());
        entity.setEmail(dto.getEmail());
        return toResponseDTO(repository.save(entity));
    }

    public void deletar(UUID id) {
        repository.delete(buscarEntidade(id));
    }

    public HospedeResponseDTO toResponseDTO(Hospede entity) {
        HospedeResponseDTO dto = new HospedeResponseDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setDocumento(entity.getCPF());
        dto.setTelefone(entity.getTelefone());
        dto.setEmail(entity.getEmail());
        return dto;
    }
}