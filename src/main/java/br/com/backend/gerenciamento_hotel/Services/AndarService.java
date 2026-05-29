
package br.com.backend.gerenciamento_hotel.Services;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.Andar;
import br.com.backend.gerenciamento_hotel.Models.Torre;
import br.com.backend.gerenciamento_hotel.Repositories.AndarRepository;
import br.com.backend.gerenciamento_hotel.Repositories.TorreRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AndarService {
    @Autowired private AndarRepository repository;
    @Autowired private TorreRepository torreRepository;

    public Andar criar(Andar andar) {
        if(andar.getTorre() == null || andar.getTorre().getId() == null) {
            throw new IllegalArgumentException("Torre é obrigatória.");
        }
        Torre torre = torreRepository.findById(andar.getTorre().getId())
            .orElseThrow(() -> new EntityNotFoundException("Torre não encontrada."));
        andar.setTorre(torre);
        return repository.save(andar);
    }

    public Andar buscarPorId(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Andar não encontrado"));
    }

    public List<Andar> listarPorTorre(UUID torreId) {
        return repository.findByTorreId(torreId);
    }

    public void deletar(UUID id) {
        repository.delete(buscarPorId(id));
    }
}
