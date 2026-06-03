package br.com.backend.gerenciamento_hotel.Services;
import br.com.backend.gerenciamento_hotel.Models.Reserva;
import br.com.backend.gerenciamento_hotel.Models.Quarto;
import br.com.backend.gerenciamento_hotel.Models.ItemReserva;
import br.com.backend.gerenciamento_hotel.DTOs.Request.ReservaRequestDTO;
import br.com.backend.gerenciamento_hotel.DTOs.Response.ReservaResponseDTO;
import br.com.backend.gerenciamento_hotel.Enums.StatusDeReservas;
import br.com.backend.gerenciamento_hotel.Enums.StatusDosQuartos;
import br.com.backend.gerenciamento_hotel.Exceptions.BusinessException;
import br.com.backend.gerenciamento_hotel.Repositories.ReservaRepository;
import br.com.backend.gerenciamento_hotel.Repositories.ItemReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservaService {
    @Autowired private ReservaRepository repository;
    @Autowired private ItemReservaRepository itemReservaRepository;
    @Autowired private QuartoService quartoService;
    @Autowired private HospedeService hospedeService;
    @Autowired private TarefaDeLimpezaService tarefaDeLimpezaService;

    public Reserva buscarEntidade(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));
    }

    public ReservaResponseDTO buscarPorId(UUID id) {
        return toResponseDTO(buscarEntidade(id));
    }

    public List<ReservaResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<ReservaResponseDTO> listarPorHospede(UUID hospedeId) {
        return repository.findByHospedeId(hospedeId).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public ReservaResponseDTO criarReserva(ReservaRequestDTO dto) {
        var hospede = hospedeService.buscarEntidade(dto.getHospedeId());
        
        List<Quarto> quartos = new ArrayList<>();
        for (UUID qId : dto.getQuartoIds()) {
            Quarto q = quartoService.buscarEntidade(qId);
            if (q.getStatus() != StatusDosQuartos.disponivel) {
                throw new BusinessException("Quarto " + q.getNumero() + " indisponível no momento.");
            }
            quartos.add(q);
        }

        if (repository.existeReservaAtivaConflitante(dto.getQuartoIds(), dto.getDiaEHoraDaReserva(), dto.getDataHora())) {
            throw new BusinessException("Existem quartos já reservados para o período informado.");
        }

        Reserva reserva = new Reserva();
        reserva.setHospede(hospede);
        reserva.setDiaEHoraDaReserva(dto.getDiaEHoraDaReserva());
        reserva.setDataHora(dto.getDataHora());
        reserva.setQuantidadeDeHospedes(dto.getQuantidadeDeHospedes());
        reserva.setStatus(StatusDeReservas.ativa);
        reserva = repository.save(reserva);

        for (Quarto q : quartos) {
            ItemReserva ir = new ItemReserva();
            ir.setReserva(reserva);
            ir.setQuarto(q);
            ir.setValorDiaria(q.getValorDaDiaria());
            itemReservaRepository.save(ir);
        }

        return toResponseDTO(reserva);
    }

    @Transactional
    public ReservaResponseDTO realizarCheckin(UUID reservaId) {
        Reserva reserva = buscarEntidade(reservaId);
        if (reserva.getStatus() != StatusDeReservas.ativa) {
            throw new BusinessException("Reserva não está ATIVA");
        }
        var itens = itemReservaRepository.findAll().stream().filter(ir -> ir.getReserva().getId().equals(reservaId)).toList();
        for (ItemReserva ir : itens) {
            quartoService.atualizarStatus(ir.getQuarto().getId(), StatusDosQuartos.ocupado);
        }
        return toResponseDTO(repository.save(reserva));
    }

    @Transactional
    public ReservaResponseDTO realizarCheckout(UUID reservaId) {
        Reserva reserva = buscarEntidade(reservaId);
        if (reserva.getStatus() != StatusDeReservas.ativa) {
            throw new BusinessException("Reserva não está ATIVA");
        }
        reserva.setStatus(StatusDeReservas.concluida);
        var itens = itemReservaRepository.findAll().stream().filter(ir -> ir.getReserva().getId().equals(reservaId)).toList();
        for (ItemReserva ir : itens) {
            quartoService.atualizarStatus(ir.getQuarto().getId(), StatusDosQuartos.limpeza);
            tarefaDeLimpezaService.abrirTarefaParaQuarto(ir.getQuarto().getId());
        }
        return toResponseDTO(repository.save(reserva));
    }

    @Transactional
    public ReservaResponseDTO cancelarReserva(UUID reservaId) {
        Reserva reserva = buscarEntidade(reservaId);
        if (reserva.getStatus() != StatusDeReservas.ativa) {
            throw new BusinessException("Apenas reservas ATIVAS podem ser canceladas.");
        }
        reserva.setStatus(StatusDeReservas.cancelada);
        return toResponseDTO(repository.save(reserva));
    }

    public ReservaResponseDTO toResponseDTO(Reserva entity) {
        ReservaResponseDTO dto = new ReservaResponseDTO();
        dto.setId(entity.getId());
        dto.setDiaEHoraDaReserva(entity.getDiaEHoraDaReserva());
        dto.setDataHora(entity.getDataHora());
        dto.setQuantidadeDeHospedes(entity.getQuantidadeDeHospedes());
        dto.setStatus(entity.getStatus());
        if(entity.getHospede() != null) {
            dto.setHospedeId(entity.getHospede().getId());
            dto.setHospedeNome(entity.getHospede().getNome());
        }
        return dto;
    }
}