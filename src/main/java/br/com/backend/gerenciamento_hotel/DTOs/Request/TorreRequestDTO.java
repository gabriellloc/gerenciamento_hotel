package br.com.backend.gerenciamento_hotel.DTOs.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class TorreRequestDTO {
    @NotBlank private String nome;
    private String descricao;
    @NotNull private UUID hotelId;
}