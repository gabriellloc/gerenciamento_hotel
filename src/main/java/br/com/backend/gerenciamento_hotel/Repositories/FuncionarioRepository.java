
package br.com.backend.gerenciamento_hotel.Repositories;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.Funcionario;
import br.com.backend.gerenciamento_hotel.Enums.CargoDosFuncionarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {
    List<Funcionario> findByCargo(CargoDosFuncionarios cargo);
    List<Funcionario> findByHotelId(UUID hotelId);
}
