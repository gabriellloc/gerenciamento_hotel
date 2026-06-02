package br.com.backend.gerenciamento_hotel.DTOs.Response;

import br.com.backend.gerenciamento_hotel.Enums.PrioridadeManutencao;
import br.com.backend.gerenciamento_hotel.Enums.StatusManutencao;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class OrdemDeManutencaoResponseDTO {
    private UUID id;
    private String descricaoProblema;
    private LocalDateTime dataDeAbertura;
    private LocalDateTime dataDeConclusao;
    private StatusManutencao status;
    private PrioridadeManutencao prioridade;
    private UUID quartoId;
    private Integer quartoNumero;
    private UUID tecnicoId;
    private String tecnicoNome;
}