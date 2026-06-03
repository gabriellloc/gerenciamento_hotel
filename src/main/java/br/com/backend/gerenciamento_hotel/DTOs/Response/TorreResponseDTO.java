package br.com.backend.gerenciamento_hotel.DTOs.Response;

import java.util.UUID;
import lombok.Data;

@Data
public class TorreResponseDTO {
    private UUID id;
    private String nome;
    private String descricao;
    private UUID hotelId;
    private String hotelNome;
}