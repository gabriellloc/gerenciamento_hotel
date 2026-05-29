
package br.com.backend.gerenciamento_hotel.Services;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.Quarto;
import br.com.backend.gerenciamento_hotel.Enums.StatusDosQuartos;
import br.com.backend.gerenciamento_hotel.Enums.TiposDeQuartos;
import br.com.backend.gerenciamento_hotel.Repositories.QuartoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuartoService {

    @Autowired
    private QuartoRepository quartoRepository;

    public Quarto criar(Quarto quarto) {
        return quartoRepository.save(quarto);
    }

    public Quarto buscarPorId(UUID id) {
        return quartoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Quarto não encontrado"));
    }

    public List<Quarto> listarPorAndar(UUID andarId) {
        return quartoRepository.findByAndar_Id(andarId);
    }

    public Quarto atualizar(UUID id, Quarto entity) {
        buscarPorId(id);
        entity.setId(id);
        return quartoRepository.save(entity);
    }

    public void deletar(UUID id) {
        quartoRepository.delete(buscarPorId(id));
    }

    public List<Quarto> buscarQuartosDisponiveis(UUID torreId, TiposDeQuartos tipo, Integer capacidadeMinima, LocalDate dataCheckin, LocalDate dataCheckout) {
        java.time.LocalDateTime checkinDateTime = dataCheckin.atStartOfDay();
        java.time.LocalDateTime checkoutDateTime = dataCheckout.atTime(23, 59, 59);
        List<Quarto> disponiveis = quartoRepository.findDisponiveisNoPeriodo(checkinDateTime, checkoutDateTime);

        if (torreId != null) {
            disponiveis = disponiveis.stream()
                .filter(q -> q.getAndar() != null && q.getAndar().getTorre() != null && q.getAndar().getTorre().getId().equals(torreId))
                .collect(Collectors.toList());
        }
        if (tipo != null) {
            disponiveis = disponiveis.stream()
                .filter(q -> q.getTipo() == tipo)
                .collect(Collectors.toList());
        }
        if (capacidadeMinima != null) {
            disponiveis = disponiveis.stream()
                .filter(q -> q.getCapacidade() != null && q.getCapacidade() >= capacidadeMinima)
                .collect(Collectors.toList());
        }
        return disponiveis;
    }

    @Transactional
    public void atualizarStatus(UUID quartoId, StatusDosQuartos novoStatus) {
        Quarto quarto = buscarPorId(quartoId);
        quarto.setStatus(novoStatus);
        quartoRepository.save(quarto);
    }
}
