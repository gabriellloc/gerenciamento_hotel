package br.com.backend.gerenciamento_hotel.DTOs.Request;

import br.com.backend.gerenciamento_hotel.Enums.PrioridadeManutencao;
import br.com.backend.gerenciamento_hotel.Enums.StatusManutencao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class OrdemDeManutencaoRequestDTO {
    @NotBlank private String descricaoProblema;
    @NotNull private StatusManutencao status;
    @NotNull private PrioridadeManutencao prioridade;
    @NotNull private UUID quartoId;
    private UUID tecnicoId;
}