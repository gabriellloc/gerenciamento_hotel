package br.com.backend.gerenciamento_hotel.DTOs.Response;

import java.util.UUID;
import lombok.Data;

@Data
public class HospedeResponseDTO {
    private UUID id;
    private String nome;
    private String documento;
    private String telefone;
    private String email;
}