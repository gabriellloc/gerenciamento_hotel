package br.com.backend.gerenciamento_hotel.DTOs.Response;

import br.com.backend.gerenciamento_hotel.Enums.CargoDosFuncionarios;
import br.com.backend.gerenciamento_hotel.Enums.TurnoDosFuncionarios;
import java.util.UUID;
import lombok.Data;

@Data
public class FuncionarioResponseDTO {
    private UUID id;
    private String nome;
    private CargoDosFuncionarios cargo;
    private TurnoDosFuncionarios turno;
    private String telefone;
    private UUID hotelId;
    private String hotelNome;
}