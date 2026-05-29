
package br.com.backend.gerenciamento_hotel.Repositories;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.Reserva;
import br.com.backend.gerenciamento_hotel.Enums.StatusDeReservas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, UUID> {
    List<Reserva> findByHospedeId(UUID hospedeId);
    List<Reserva> findByStatus(StatusDeReservas status);

    @Query("SELECT COUNT(r) > 0 FROM Reserva r JOIN ItemReserva ir ON ir.reserva.id = r.id " +
           "WHERE ir.quarto.id IN :quartoIds AND r.status = 'ativa' " +
           "AND (r.diaEHoraDaReserva <= :checkout AND r.DataHora >= :checkin)")
    boolean existeReservaAtivaConflitante(@Param("quartoIds") List<UUID> quartoIds, 
                                          @Param("checkin") LocalDate checkin, 
                                          @Param("checkout") LocalDate checkout);
}
