
package br.com.backend.gerenciamento_hotel.Services;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.Hotel;
import br.com.backend.gerenciamento_hotel.Repositories.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository repository;

    public Hotel criar(Hotel entity) {
        return repository.save(entity);
    }

    public Hotel buscarPorId(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel não encontrado(a)"));
    }

    public List<Hotel> listarTodos() {
        return repository.findAll();
    }

    public Hotel atualizar(UUID id, Hotel entity) {
        buscarPorId(id); // Valida se existe
        entity.setId(id);
        return repository.save(entity);
    }

    public void deletar(UUID id) {
        Hotel entity = buscarPorId(id);
        repository.delete(entity);
    }
}
