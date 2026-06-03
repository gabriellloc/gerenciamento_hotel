package br.com.backend.gerenciamento_hotel.Services;
import br.com.backend.gerenciamento_hotel.Models.Funcionario;
import br.com.backend.gerenciamento_hotel.DTOs.Request.FuncionarioRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.FuncionarioResponseDTO;
import br.com.backend.gerenciamento_hotel.Repositories.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {
    @Autowired private FuncionarioRepository repository;
    @Autowired private HotelService hotelService;

    public FuncionarioResponseDTO criar(FuncionarioRequestDTO dto) {
        Funcionario entity = new Funcionario();
        entity.setNome(dto.getNome());
        entity.setCargo(dto.getCargo());
        entity.setTurno(dto.getTurno());
        entity.setTelefone(dto.getTelefone());
        entity.setHotel(hotelService.buscarEntidade(dto.getHotelId()));
        return toResponseDTO(repository.save(entity));
    }

    public FuncionarioResponseDTO buscarPorId(UUID id) {
        return toResponseDTO(buscarEntidade(id));
    }

    public Funcionario buscarEntidade(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));
    }

    public List<FuncionarioResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public FuncionarioResponseDTO atualizar(UUID id, FuncionarioRequestDTO dto) {
        Funcionario entity = buscarEntidade(id);
        entity.setNome(dto.getNome());
        entity.setCargo(dto.getCargo());
        entity.setTurno(dto.getTurno());
        entity.setTelefone(dto.getTelefone());
        entity.setHotel(hotelService.buscarEntidade(dto.getHotelId()));
        return toResponseDTO(repository.save(entity));
    }

    public void deletar(UUID id) {
        repository.delete(buscarEntidade(id));
    }

    public FuncionarioResponseDTO toResponseDTO(Funcionario entity) {
        FuncionarioResponseDTO dto = new FuncionarioResponseDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setCargo(entity.getCargo());
        dto.setTurno(entity.getTurno());
        dto.setTelefone(entity.getTelefone());
        if(entity.getHotel() != null) {
            dto.setHotelId(entity.getHotel().getId());
            dto.setHotelNome(entity.getHotel().getNome());
        }
        return dto;
    }
}