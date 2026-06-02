package br.com.backend.gerenciamento_hotel.DTOs.Request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class AndarRequestDTO {
    @NotNull private Integer numero;
    @NotNull private UUID torreId;
}