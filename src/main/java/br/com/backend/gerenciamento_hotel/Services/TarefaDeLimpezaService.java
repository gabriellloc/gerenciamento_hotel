package br.com.backend.gerenciamento_hotel.Services;
import br.com.backend.gerenciamento_hotel.Models.TarefaDeLimpeza;
import br.com.backend.gerenciamento_hotel.Models.Quarto;
import br.com.backend.gerenciamento_hotel.DTOs.Request.TarefaDeLimpezaRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.TarefaDeLimpezaResponseDTO;
import br.com.backend.gerenciamento_hotel.Enums.StatusDaTarefa;
import br.com.backend.gerenciamento_hotel.Enums.StatusDosQuartos;
import br.com.backend.gerenciamento_hotel.Enums.CargoDosFuncionarios;
import br.com.backend.gerenciamento_hotel.Exceptions.BusinessException;
import br.com.backend.gerenciamento_hotel.Repositories.TarefaDeLimpezaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TarefaDeLimpezaService {
    @Autowired private TarefaDeLimpezaRepository repository;
    @Autowired private QuartoService quartoService;
    @Autowired private FuncionarioService funcionarioService;

    public TarefaDeLimpeza buscarEntidade(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tarefa de limpeza não encontrada"));
    }

    public TarefaDeLimpezaResponseDTO buscarPorId(UUID id) {
        return toResponseDTO(buscarEntidade(id));
    }

    public List<TarefaDeLimpezaResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
    
    public List<TarefaDeLimpezaResponseDTO> listarPorQuarto(UUID quartoId) {
        return repository.findByQuarto_Id(quartoId).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<TarefaDeLimpezaResponseDTO> listarPorFuncionario(UUID funcionarioId) {
        return repository.findByFuncionario_Id(funcionarioId).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<TarefaDeLimpezaResponseDTO> listarPorStatus(StatusDaTarefa status) {
        return repository.findByStatus(status).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public TarefaDeLimpezaResponseDTO abrirTarefaParaQuarto(UUID quartoId) {
        Quarto quarto = quartoService.buscarEntidade(quartoId);
        if (quarto.getStatus() != StatusDosQuartos.limpeza) {
            throw new BusinessException("Quarto não está no status de LIMPEZA.");
        }
        TarefaDeLimpeza tarefa = new TarefaDeLimpeza();
        tarefa.setQuarto(quarto);
        tarefa.setStatus(StatusDaTarefa.pendente);
        tarefa.setDataDeAbertura(LocalDateTime.now());
        return toResponseDTO(repository.save(tarefa));
    }

    @Transactional
    public TarefaDeLimpezaResponseDTO atribuirFuncionario(UUID tarefaId, UUID funcionarioId) {
        TarefaDeLimpeza tarefa = buscarEntidade(tarefaId);
        var funcionario = funcionarioService.buscarEntidade(funcionarioId);
        if (funcionario.getCargo() != CargoDosFuncionarios.limpeza) {
            throw new BusinessException("Funcionário deve ter o cargo de LIMPEZA");
        }
        tarefa.setFuncionario(funcionario);
        tarefa.setStatus(StatusDaTarefa.em_andamento);
        return toResponseDTO(repository.save(tarefa));
    }

    @Transactional
    public TarefaDeLimpezaResponseDTO concluirTarefa(UUID tarefaId) {
        TarefaDeLimpeza tarefa = buscarEntidade(tarefaId);
        tarefa.setDataDeConclusao(LocalDateTime.now());
        tarefa.setStatus(StatusDaTarefa.concluida);
        repository.save(tarefa);
        quartoService.atualizarStatus(tarefa.getQuarto().getId(), StatusDosQuartos.disponivel);
        return toResponseDTO(tarefa);
    }

    public TarefaDeLimpezaResponseDTO toResponseDTO(TarefaDeLimpeza entity) {
        TarefaDeLimpezaResponseDTO dto = new TarefaDeLimpezaResponseDTO();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setDataDeAbertura(entity.getDataDeAbertura());
        dto.setDataDeConclusao(entity.getDataDeConclusao());
        dto.setObservacoes(entity.getObservacoes());
        if(entity.getQuarto() != null) {
            dto.setQuartoId(entity.getQuarto().getId());
            dto.setQuartoNumero(entity.getQuarto().getNumero());
        }
        if(entity.getFuncionario() != null) {
            dto.setFuncionarioId(entity.getFuncionario().getId());
            dto.setFuncionarioNome(entity.getFuncionario().getNome());
        }
        return dto;
    }
}