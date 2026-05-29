
package br.com.backend.gerenciamento_hotel.Services;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.OrdemDeManutencao;
import br.com.backend.gerenciamento_hotel.Models.Quarto;
import br.com.backend.gerenciamento_hotel.Models.Funcionario;
import br.com.backend.gerenciamento_hotel.Models.TarefaDeLimpeza;
import br.com.backend.gerenciamento_hotel.Enums.StatusManutencao;
import br.com.backend.gerenciamento_hotel.Enums.PrioridadeManutencao;
import br.com.backend.gerenciamento_hotel.Enums.StatusDosQuartos;
import br.com.backend.gerenciamento_hotel.Enums.StatusDaTarefa;
import br.com.backend.gerenciamento_hotel.Enums.CargoDosFuncionarios;
import br.com.backend.gerenciamento_hotel.Repositories.OrdemDeManutencaoRepository;
import br.com.backend.gerenciamento_hotel.Repositories.TarefaDeLimpezaRepository;
import br.com.backend.gerenciamento_hotel.Exceptions.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdemDeManutencaoService {

    @Autowired private OrdemDeManutencaoRepository repository;
    @Autowired private TarefaDeLimpezaRepository tarefaDeLimpezaRepository;
    @Autowired private QuartoService quartoService;
    @Autowired private FuncionarioService funcionarioService;

    @Transactional
    public OrdemDeManutencao abrirOrdem(UUID quartoId, String descricao, PrioridadeManutencao prioridade) {
        Quarto quarto = quartoService.buscarPorId(quartoId);
        
        OrdemDeManutencao ordem = new OrdemDeManutencao();
        ordem.setQuarto(quarto);
        ordem.setDescricaoProblema(descricao);
        ordem.setPrioridade(prioridade);
        ordem.setStatus(StatusManutencao.ABERTA);
        ordem.setDataDeAbertura(LocalDateTime.now());
        
        quartoService.atualizarStatus(quartoId, StatusDosQuartos.manutencao);
        return repository.save(ordem);
    }

    @Transactional
    public void atribuirTecnico(UUID ordemId, UUID funcionarioId) {
        OrdemDeManutencao ordem = buscarPorId(ordemId);
        Funcionario func = funcionarioService.buscarPorId(funcionarioId);

        if (func.getCargo() != CargoDosFuncionarios.manutencao) {
            throw new BusinessException("Funcionário não é do cargo de MANUTENCAO");
        }

        ordem.setTecnico(func);
        ordem.setStatus(StatusManutencao.EM_ANDAMENTO);
        repository.save(ordem);
    }

    @Transactional
    public void concluirOrdem(UUID ordemId) {
        OrdemDeManutencao ordem = buscarPorId(ordemId);
        ordem.setDataDeConclusao(LocalDateTime.now());
        ordem.setStatus(StatusManutencao.CONCLUIDA);
        repository.save(ordem);

        List<TarefaDeLimpeza> tarefas = tarefaDeLimpezaRepository.findByQuarto_Id(ordem.getQuarto().getId());
        boolean hasLimpezaPendente = tarefas.stream().anyMatch(t -> t.getStatus() != StatusDaTarefa.concluida);

        if (hasLimpezaPendente) {
            quartoService.atualizarStatus(ordem.getQuarto().getId(), StatusDosQuartos.limpeza);
        } else {
            quartoService.atualizarStatus(ordem.getQuarto().getId(), StatusDosQuartos.disponivel);
        }
    }

    public OrdemDeManutencao buscarPorId(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ordem de manutenção não encontrada"));
    }
    
    public List<OrdemDeManutencao> listarPorQuarto(UUID quartoId) {
        return repository.findByQuarto_Id(quartoId);
    }
    
    public List<OrdemDeManutencao> listarPorFuncionario(UUID funcionarioId) {
        return repository.findByTecnico_Id(funcionarioId);
    }
    
    public List<OrdemDeManutencao> listarPorStatus(StatusManutencao status) {
        return repository.findByStatus(status);
    }
}
