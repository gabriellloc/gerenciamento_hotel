package br.com.backend.gerenciamento_hotel.Services;
import br.com.backend.gerenciamento_hotel.Models.OrdemDeManutencao;
import br.com.backend.gerenciamento_hotel.Models.Quarto;
import br.com.backend.gerenciamento_hotel.DTOs.Request.OrdemDeManutencaoRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.OrdemDeManutencaoResponseDTO;
import br.com.backend.gerenciamento_hotel.Enums.StatusManutencao;
import br.com.backend.gerenciamento_hotel.Enums.StatusDosQuartos;
import br.com.backend.gerenciamento_hotel.Enums.CargoDosFuncionarios;
import br.com.backend.gerenciamento_hotel.Enums.StatusDaTarefa;
import br.com.backend.gerenciamento_hotel.Exceptions.BusinessException;
import br.com.backend.gerenciamento_hotel.Repositories.OrdemDeManutencaoRepository;
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
public class OrdemDeManutencaoService {
    @Autowired private OrdemDeManutencaoRepository repository;
    @Autowired private TarefaDeLimpezaRepository tarefaDeLimpezaRepository;
    @Autowired private QuartoService quartoService;
    @Autowired private FuncionarioService funcionarioService;

    public OrdemDeManutencao buscarEntidade(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ordem de manutenção não encontrada"));
    }

    public OrdemDeManutencaoResponseDTO buscarPorId(UUID id) {
        return toResponseDTO(buscarEntidade(id));
    }

    public List<OrdemDeManutencaoResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<OrdemDeManutencaoResponseDTO> listarPorQuarto(UUID quartoId) {
        return repository.findByQuarto_Id(quartoId).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<OrdemDeManutencaoResponseDTO> listarPorFuncionario(UUID funcionarioId) {
        return repository.findByTecnico_Id(funcionarioId).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<OrdemDeManutencaoResponseDTO> listarPorStatus(StatusManutencao status) {
        return repository.findByStatus(status).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public OrdemDeManutencaoResponseDTO abrirOrdem(OrdemDeManutencaoRequestDTO dto) {
        Quarto quarto = quartoService.buscarEntidade(dto.getQuartoId());
        OrdemDeManutencao ordem = new OrdemDeManutencao();
        ordem.setQuarto(quarto);
        ordem.setDescricaoProblema(dto.getDescricaoProblema());
        ordem.setPrioridade(dto.getPrioridade());
        ordem.setStatus(StatusManutencao.ABERTA);
        ordem.setDataDeAbertura(LocalDateTime.now());
        quartoService.atualizarStatus(quarto.getId(), StatusDosQuartos.manutencao);
        return toResponseDTO(repository.save(ordem));
    }

    @Transactional
    public OrdemDeManutencaoResponseDTO atribuirTecnico(UUID ordemId, UUID funcionarioId) {
        OrdemDeManutencao ordem = buscarEntidade(ordemId);
        var func = funcionarioService.buscarEntidade(funcionarioId);
        if (func.getCargo() != CargoDosFuncionarios.manutencao) {
            throw new BusinessException("Funcionário não é do cargo de MANUTENCAO");
        }
        ordem.setTecnico(func);
        ordem.setStatus(StatusManutencao.EM_ANDAMENTO);
        return toResponseDTO(repository.save(ordem));
    }

    @Transactional
    public OrdemDeManutencaoResponseDTO concluirOrdem(UUID ordemId) {
        OrdemDeManutencao ordem = buscarEntidade(ordemId);
        ordem.setDataDeConclusao(LocalDateTime.now());
        ordem.setStatus(StatusManutencao.CONCLUIDA);
        repository.save(ordem);

        var tarefas = tarefaDeLimpezaRepository.findByQuarto_Id(ordem.getQuarto().getId());
        boolean hasLimpezaPendente = tarefas.stream().anyMatch(t -> t.getStatus() != StatusDaTarefa.concluida);

        if (hasLimpezaPendente) {
            quartoService.atualizarStatus(ordem.getQuarto().getId(), StatusDosQuartos.limpeza);
        } else {
            quartoService.atualizarStatus(ordem.getQuarto().getId(), StatusDosQuartos.disponivel);
        }
        return toResponseDTO(ordem);
    }

    public OrdemDeManutencaoResponseDTO toResponseDTO(OrdemDeManutencao entity) {
        OrdemDeManutencaoResponseDTO dto = new OrdemDeManutencaoResponseDTO();
        dto.setId(entity.getId());
        dto.setDescricaoProblema(entity.getDescricaoProblema());
        dto.setDataDeAbertura(entity.getDataDeAbertura());
        dto.setDataDeConclusao(entity.getDataDeConclusao());
        dto.setStatus(entity.getStatus());
        dto.setPrioridade(entity.getPrioridade());
        if(entity.getQuarto() != null) {
            dto.setQuartoId(entity.getQuarto().getId());
            dto.setQuartoNumero(entity.getQuarto().getNumero());
        }
        if(entity.getTecnico() != null) {
            dto.setTecnicoId(entity.getTecnico().getId());
            dto.setTecnicoNome(entity.getTecnico().getNome());
        }
        return dto;
    }
}