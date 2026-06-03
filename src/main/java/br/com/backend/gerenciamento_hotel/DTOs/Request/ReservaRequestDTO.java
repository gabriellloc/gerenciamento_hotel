package br.com.backend.gerenciamento_hotel.DTOs.Request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class ReservaRequestDTO {
    @NotNull private UUID hospedeId;
    @NotEmpty private List<UUID> quartoIds;
    @NotNull @FutureOrPresent private LocalDateTime diaEHoraDaReserva;
    @NotNull @FutureOrPresent private LocalDateTime dataHora;
    @NotNull @Positive private Integer quantidadeDeHospedes;
}