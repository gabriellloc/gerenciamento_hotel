package br.com.backend.gerenciamento_hotel.DTOs.Response;

import java.util.UUID;
import lombok.Data;

@Data
public class HotelResponseDTO {
    private UUID id;
    private String nome;
    private String localizacao;
    private String descricao;
}