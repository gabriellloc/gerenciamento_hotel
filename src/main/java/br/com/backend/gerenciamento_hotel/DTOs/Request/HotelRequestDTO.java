package br.com.backend.gerenciamento_hotel.DTOs.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HotelRequestDTO {
    @NotBlank private String nome;
    @NotBlank private String localizacao;
    private String descricao;
}