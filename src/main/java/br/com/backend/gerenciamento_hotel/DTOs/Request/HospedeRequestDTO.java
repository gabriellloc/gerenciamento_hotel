package br.com.backend.gerenciamento_hotel.DTOs.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HospedeRequestDTO {
    @NotBlank private String nome;
    @NotBlank private String documento;
    @NotBlank private String telefone;
    @NotBlank @Email private String email;
}