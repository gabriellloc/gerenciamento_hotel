
package br.com.backend.gerenciamento_hotel.Services;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.Reserva;
import br.com.backend.gerenciamento_hotel.Models.Quarto;
import br.com.backend.gerenciamento_hotel.Models.Hospede;
import br.com.backend.gerenciamento_hotel.Models.ItemReserva;
import br.com.backend.gerenciamento_hotel.Enums.StatusDeReservas;
import br.com.backend.gerenciamento_hotel.Enums.StatusDosQuartos;
import br.com.backend.gerenciamento_hotel.Repositories.ReservaRepository;
import br.com.backend.gerenciamento_hotel.Repositories.ItemReservaRepository;
import br.com.backend.gerenciamento_hotel.DTOs.Request.ReservaRequestDTO;
import br.com.backend.gerenciamento_hotel.Exceptions.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService {

    @Autowired private ReservaRepository reservaRepository;
    @Autowired private ItemReservaRepository itemReservaRepository;
    @Autowired private QuartoService quartoService;
    @Autowired private HospedeService hospedeService;
    @Autowired private TarefaDeLimpezaService tarefaDeLimpezaService;

    @Transactional
    public Reserva criarReserva(ReservaRequestDTO dto) {
        Hospede hospede = hospedeService.buscarPorId(dto.getHospedeId());
        
        List<Quarto> quartos = new ArrayList<>();
        for (UUID qId : dto.getQuartoIds()) {
            Quarto q = quartoService.buscarPorId(qId);
            if (q.getStatus() != StatusDosQuartos.disponivel) {
                throw new BusinessException("Quarto " + q.getNumero() + " indisponível no momento.");
            }
            quartos.add(q);
        }

        if (reservaRepository.existeReservaAtivaConflitante(dto.getQuartoIds(), dto.getDataCheckinPrevista(), dto.getDataCheckoutPrevista())) {
            throw new BusinessException("Existem quartos já reservados para o período informado.");
        }

        Reserva reserva = new Reserva();
        reserva.setHospede(hospede);
        reserva.setDiaEHoraDaReserva(dto.getDataCheckinPrevista().atStartOfDay());
        reserva.setDataHora(dto.getDataCheckoutPrevista().atStartOfDay());
        reserva.setQuantidadeDeHospedes(dto.getQuantidadeHospedes());
        reserva.setStatus(StatusDeReservas.ativa);

        reserva = reservaRepository.save(reserva);

        for (Quarto q : quartos) {
            ItemReserva ir = new ItemReserva();
            ir.setReserva(reserva);
            ir.setQuarto(q);
            ir.setValorDiaria(q.getValorDaDiaria());
            itemReservaRepository.save(ir);
        }

        return reserva;
    }

    @Transactional
    public void realizarCheckin(UUID reservaId) {
        Reserva reserva = buscarPorId(reservaId);
        if (reserva.getStatus() != StatusDeReservas.ativa) {
            throw new BusinessException("Reserva não está ATIVA");
        }

        //reserva.setDataCheckinReal(LocalDateTime.now());
        
        List<ItemReserva> itens = itemReservaRepository.findAll().stream().filter(ir -> ir.getReserva().getId().equals(reservaId)).toList();
        for (ItemReserva ir : itens) {
            quartoService.atualizarStatus(ir.getQuarto().getId(), StatusDosQuartos.ocupado);
        }
        reservaRepository.save(reserva);
    }

    @Transactional
    public void realizarCheckout(UUID reservaId) {
        Reserva reserva = buscarPorId(reservaId);
        if (reserva.getStatus() != StatusDeReservas.ativa) {
            throw new BusinessException("Reserva não está ATIVA");
        }

        //reserva.setDataCheckoutReal(LocalDateTime.now());
        reserva.setStatus(StatusDeReservas.concluida);
        
        List<ItemReserva> itens = itemReservaRepository.findAll().stream().filter(ir -> ir.getReserva().getId().equals(reservaId)).toList();
        for (ItemReserva ir : itens) {
            quartoService.atualizarStatus(ir.getQuarto().getId(), StatusDosQuartos.limpeza);
            tarefaDeLimpezaService.abrirTarefaParaQuarto(ir.getQuarto().getId());
        }
        reservaRepository.save(reserva);
    }

    @Transactional
    public void cancelarReserva(UUID reservaId) {
        Reserva reserva = buscarPorId(reservaId);
        if (reserva.getStatus() != StatusDeReservas.ativa) {
            throw new BusinessException("Apenas reservas ATIVAS podem ser canceladas.");
        }
        reserva.setStatus(StatusDeReservas.cancelada);
        reservaRepository.save(reserva);
    }

    public Reserva buscarPorId(UUID id) {
        return reservaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));
    }

    public List<Reserva> listarPorHospede(UUID hospedeId) {
        return reservaRepository.findByHospedeId(hospedeId);
    }
}
