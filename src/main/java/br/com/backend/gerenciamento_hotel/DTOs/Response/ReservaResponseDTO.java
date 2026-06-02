package br.com.backend.gerenciamento_hotel.DTOs.Response;

import br.com.backend.gerenciamento_hotel.Enums.StatusDeReservas;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class ReservaResponseDTO {
    private UUID id;
    private LocalDateTime diaEHoraDaReserva;
    private LocalDateTime dataHora;
    private Integer quantidadeDeHospedes;
    private StatusDeReservas status;
    private UUID hospedeId;
    private String hospedeNome;
}