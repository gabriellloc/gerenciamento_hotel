package br.com.backend.gerenciamento_hotel.DTOs.Response;

import br.com.backend.gerenciamento_hotel.Enums.StatusDosQuartos;
import br.com.backend.gerenciamento_hotel.Enums.TiposDeQuartos;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class QuartoResponseDTO {
    private UUID id;
    private Integer numero;
    private TiposDeQuartos tipo;
    private Integer capacidade;
    private BigDecimal valorDaDiaria;
    private StatusDosQuartos status;
    private UUID andarId;
    private Integer andarNumero;
}