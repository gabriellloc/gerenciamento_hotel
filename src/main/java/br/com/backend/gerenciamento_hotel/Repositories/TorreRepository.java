
package br.com.backend.gerenciamento_hotel.Repositories;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Models.Torre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TorreRepository extends JpaRepository<Torre, UUID> {
    List<Torre> findByHotelId(UUID hotelId);
}
