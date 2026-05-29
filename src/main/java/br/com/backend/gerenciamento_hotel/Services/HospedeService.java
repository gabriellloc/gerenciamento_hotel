
package br.com.backend.gerenciamento_hotel.Services;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.Hospede;
import br.com.backend.gerenciamento_hotel.Repositories.HospedeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospedeService {

    @Autowired
    private HospedeRepository repository;

    public Hospede criar(Hospede entity) {
        return repository.save(entity);
    }

    public Hospede buscarPorId(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hospede não encontrado(a)"));
    }

    public List<Hospede> listarTodos() {
        return repository.findAll();
    }

    public Hospede atualizar(UUID id, Hospede entity) {
        buscarPorId(id); // Valida se existe
        entity.setId(id);
        return repository.save(entity);
    }

    public void deletar(UUID id) {
        Hospede entity = buscarPorId(id);
        repository.delete(entity);
    }
}
