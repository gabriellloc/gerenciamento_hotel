package br.com.backend.gerenciamento_hotel.DTOs.Response;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class ItemReservaResponseDTO {
    private UUID id;
    private BigDecimal valorDiaria;
    private String observacoes;
    private UUID reservaId;
    private UUID quartoId;
    private Integer quartoNumero;
}