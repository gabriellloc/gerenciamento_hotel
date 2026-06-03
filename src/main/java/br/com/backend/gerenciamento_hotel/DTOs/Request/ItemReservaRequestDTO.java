package br.com.backend.gerenciamento_hotel.DTOs.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class ItemReservaRequestDTO {
    @NotNull @Positive private BigDecimal valorDiaria;
    private String observacoes;
    @NotNull private UUID reservaId;
    @NotNull private UUID quartoId;
}