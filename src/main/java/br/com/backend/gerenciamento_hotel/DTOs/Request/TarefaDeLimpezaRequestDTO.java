package br.com.backend.gerenciamento_hotel.DTOs.Request;

import br.com.backend.gerenciamento_hotel.Enums.StatusDaTarefa;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class TarefaDeLimpezaRequestDTO {
    @NotNull private StatusDaTarefa status;
    private String observacoes;
    @NotNull private UUID quartoId;
    private UUID funcionarioId;
}