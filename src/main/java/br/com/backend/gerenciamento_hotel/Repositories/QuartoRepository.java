package br.com.backend.gerenciamento_hotel.Repositories;

import br.com.backend.gerenciamento_hotel.Enums.StatusDeReservas;
import br.com.backend.gerenciamento_hotel.Enums.StatusDosQuartos;
import br.com.backend.gerenciamento_hotel.Enums.TiposDeQuartos;
import br.com.backend.gerenciamento_hotel.Models.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, UUID> {

    List<Quarto> findByStatus(StatusDosQuartos status);

    @Query("SELECT q FROM Quarto q WHERE q.andar.torre.id = :torreId")
    List<Quarto> findByTorreId(@Param("torreId") UUID torreId);

    List<Quarto> findByAndar_Id(UUID andarId);

    List<Quarto> findByTipo(TiposDeQuartos tipo);

    @Query("SELECT q FROM Quarto q WHERE q.capacidade >= :capacidade")
    List<Quarto> findByCapacidadeMinima(@Param("capacidade") Integer capacidade);

    @Query("""
           SELECT q
           FROM Quarto q
           WHERE q.status = br.com.backend.gerenciamento_hotel.Enums.StatusDosQuartos.disponivel
             AND q.id NOT IN (
                 SELECT ir.quarto.id
                 FROM ItemReserva ir
                 WHERE ir.reserva.status = br.com.backend.gerenciamento_hotel.Enums.StatusDeReservas.ativa
                   AND (
                         ir.reserva.diaEHoraDaReserva < :checkout
                     AND ir.reserva.DataHora        > :checkin
                   )
           )
           """)
    List<Quarto> findDisponiveisNoPeriodo(@Param("checkin") LocalDateTime checkin,
                                          @Param("checkout") LocalDateTime checkout);
}