package br.com.backend.gerenciamento_hotel.DTOs.Response;

import java.util.UUID;
import lombok.Data;

@Data
public class AndarResponseDTO {
    private UUID id;
    private Integer numero;
    private UUID torreId;
    private String torreNome;
}