package br.com.backend.gerenciamento_hotel.DTOs.Response;

import br.com.backend.gerenciamento_hotel.Enums.StatusDaTarefa;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class TarefaDeLimpezaResponseDTO {
    private UUID id;
    private StatusDaTarefa status;
    private LocalDateTime dataDeAbertura;
    private LocalDateTime dataDeConclusao;
    private String observacoes;
    private UUID quartoId;
    private Integer quartoNumero;
    private UUID funcionarioId;
    private String funcionarioNome;
}