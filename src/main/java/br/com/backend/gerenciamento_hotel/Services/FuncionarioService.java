
package br.com.backend.gerenciamento_hotel.Services;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.Funcionario;
import br.com.backend.gerenciamento_hotel.Models.Hotel;
import br.com.backend.gerenciamento_hotel.Enums.CargoDosFuncionarios;
import br.com.backend.gerenciamento_hotel.Repositories.FuncionarioRepository;
import br.com.backend.gerenciamento_hotel.Repositories.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {
    @Autowired private FuncionarioRepository repository;
    @Autowired private HotelRepository hotelRepository;

    public Funcionario criar(Funcionario funcionario) {
        if(funcionario.getHotel() != null && funcionario.getHotel().getId() != null) {
            Hotel hotel = hotelRepository.findById(funcionario.getHotel().getId())
                .orElseThrow(() -> new EntityNotFoundException("Hotel não encontrado"));
            funcionario.setHotel(hotel);
        }
        return repository.save(funcionario);
    }

    public Funcionario buscarPorId(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Funcionario não encontrado"));
    }

    public List<Funcionario> listarTodos() {
        return repository.findAll();
    }
    
    public List<Funcionario> listarPorCargo(CargoDosFuncionarios cargo) {
        return repository.findByCargo(cargo);
    }
    
    public List<Funcionario> listarPorHotel(UUID hotelId) {
        return repository.findByHotelId(hotelId);
    }

    public Funcionario atualizar(UUID id, Funcionario entity) {
        buscarPorId(id);
        entity.setId(id);
        return repository.save(entity);
    }

    public void deletar(UUID id) {
        repository.delete(buscarPorId(id));
    }
}
