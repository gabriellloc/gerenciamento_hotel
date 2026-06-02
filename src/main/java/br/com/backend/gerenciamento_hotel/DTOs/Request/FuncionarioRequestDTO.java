package br.com.backend.gerenciamento_hotel.DTOs.Request;

import br.com.backend.gerenciamento_hotel.Enums.CargoDosFuncionarios;
import br.com.backend.gerenciamento_hotel.Enums.TurnoDosFuncionarios;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class FuncionarioRequestDTO {
    @NotBlank private String nome;
    @NotNull private CargoDosFuncionarios cargo;
    @NotNull private TurnoDosFuncionarios turno;
    @NotBlank private String telefone;
    @NotNull private UUID hotelId;
}