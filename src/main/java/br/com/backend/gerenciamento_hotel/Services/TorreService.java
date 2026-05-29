
package br.com.backend.gerenciamento_hotel.Services;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.Hotel;
import br.com.backend.gerenciamento_hotel.Models.Torre;
import br.com.backend.gerenciamento_hotel.Repositories.HotelRepository;
import br.com.backend.gerenciamento_hotel.Repositories.TorreRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TorreService {
    @Autowired private TorreRepository repository;
    @Autowired private HotelRepository hotelRepository;

    public Torre criar(Torre torre) {
        if(torre.getHotel() == null || torre.getHotel().getId() == null) {
            throw new IllegalArgumentException("Hotel é obrigatório.");
        }
        Hotel hotel = hotelRepository.findById(torre.getHotel().getId())
            .orElseThrow(() -> new EntityNotFoundException("Hotel não encontrado."));
        torre.setHotel(hotel);
        return repository.save(torre);
    }

    public Torre buscarPorId(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Torre não encontrada"));
    }

    public List<Torre> listarPorHotel(UUID hotelId) {
        return repository.findByHotelId(hotelId);
    }

    public Torre atualizar(UUID id, Torre entity) {
        Torre existing = buscarPorId(id);
        entity.setId(existing.getId());
        if(entity.getHotel() == null) entity.setHotel(existing.getHotel());
        return repository.save(entity);
    }

    public void deletar(UUID id) {
        repository.delete(buscarPorId(id));
    }
}
