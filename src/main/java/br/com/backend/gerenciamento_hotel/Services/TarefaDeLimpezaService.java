
package br.com.backend.gerenciamento_hotel.Services;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.TarefaDeLimpeza;
import br.com.backend.gerenciamento_hotel.Models.Quarto;
import br.com.backend.gerenciamento_hotel.Models.Funcionario;
import br.com.backend.gerenciamento_hotel.Enums.StatusDaTarefa;
import br.com.backend.gerenciamento_hotel.Enums.StatusDosQuartos;
import br.com.backend.gerenciamento_hotel.Enums.CargoDosFuncionarios;
import br.com.backend.gerenciamento_hotel.Repositories.TarefaDeLimpezaRepository;
import br.com.backend.gerenciamento_hotel.Exceptions.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TarefaDeLimpezaService {

    @Autowired private TarefaDeLimpezaRepository repository;
    @Autowired private QuartoService quartoService;
    @Autowired private FuncionarioService funcionarioService;

    @Transactional
    public TarefaDeLimpeza abrirTarefaParaQuarto(UUID quartoId) {
        Quarto quarto = quartoService.buscarPorId(quartoId);
        if (quarto.getStatus() != StatusDosQuartos.limpeza) {
            throw new BusinessException("Quarto não está no status de LIMPEZA.");
        }

        TarefaDeLimpeza tarefa = new TarefaDeLimpeza();
        tarefa.setQuarto(quarto);
        tarefa.setStatus(StatusDaTarefa.pendente);
        tarefa.setDataDeAbertura(LocalDateTime.now());
        
        return repository.save(tarefa);
    }

    @Transactional
    public void atribuirFuncionario(UUID tarefaId, UUID funcionarioId) {
        TarefaDeLimpeza tarefa = buscarPorId(tarefaId);
        Funcionario funcionario = funcionarioService.buscarPorId(funcionarioId);

        if (funcionario.getCargo() != CargoDosFuncionarios.limpeza) {
            throw new BusinessException("Funcionário deve ter o cargo de LIMPEZA");
        }

        tarefa.setFuncionario(funcionario);
        tarefa.setStatus(StatusDaTarefa.em_andamento);
        repository.save(tarefa);
    }

    @Transactional
    public void concluirTarefa(UUID tarefaId) {
        TarefaDeLimpeza tarefa = buscarPorId(tarefaId);
        tarefa.setDataDeConclusao(LocalDateTime.now());
        tarefa.setStatus(StatusDaTarefa.concluida);
        repository.save(tarefa);

        quartoService.atualizarStatus(tarefa.getQuarto().getId(), StatusDosQuartos.disponivel);
    }

    public TarefaDeLimpeza buscarPorId(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tarefa de limpeza não encontrada"));
    }
    
    public List<TarefaDeLimpeza> listarPorQuarto(UUID quartoId) {
        return repository.findByQuarto_Id(quartoId);
    }
    
    public List<TarefaDeLimpeza> listarPorFuncionario(UUID funcionarioId) {
        return repository.findByFuncionario_Id(funcionarioId);
    }
    
    public List<TarefaDeLimpeza> listarPorStatus(StatusDaTarefa status) {
        return repository.findByStatus(status);
    }
}
