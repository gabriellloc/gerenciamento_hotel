package br.com.backend.gerenciamento_hotel.DTOs.Request;

import br.com.backend.gerenciamento_hotel.Enums.StatusDosQuartos;
import br.com.backend.gerenciamento_hotel.Enums.TiposDeQuartos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class QuartoRequestDTO {
    @NotNull private Integer numero;
    @NotNull private TiposDeQuartos tipo;
    @NotNull @Positive private Integer capacidade;
    @NotNull @Positive private BigDecimal valorDaDiaria;
    @NotNull private StatusDosQuartos status;
    @NotNull private UUID andarId;
}