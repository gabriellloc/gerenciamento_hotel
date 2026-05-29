
package br.com.backend.gerenciamento_hotel.Repositories;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.TarefaDeLimpeza;
import br.com.backend.gerenciamento_hotel.Enums.StatusDaTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaDeLimpezaRepository extends JpaRepository<TarefaDeLimpeza, UUID> {
    List<TarefaDeLimpeza> findByQuarto_Id(UUID quartoId);
    List<TarefaDeLimpeza> findByFuncionario_Id(UUID funcionarioId);
    List<TarefaDeLimpeza> findByStatus(StatusDaTarefa status);
}
