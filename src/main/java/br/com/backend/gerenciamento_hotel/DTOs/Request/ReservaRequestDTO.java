
package br.com.backend.gerenciamento_hotel.DTOs.Request;
import java.util.UUID;
import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public class ReservaRequestDTO {
    @NotNull
    private UUID hospedeId;
    @NotEmpty
    private List<UUID> quartoIds;
    @NotNull @FutureOrPresent
    private LocalDate dataCheckinPrevista;
    @NotNull @FutureOrPresent
    private LocalDate dataCheckoutPrevista;
    @NotNull @Positive
    private Integer quantidadeHospedes;
    
    // Getters and Setters
    public UUID getHospedeId() { return hospedeId; }
    public void setHospedeId(UUID hospedeId) { this.hospedeId = hospedeId; }
    public List<UUID> getQuartoIds() { return quartoIds; }
    public void setQuartoIds(List<UUID> quartoIds) { this.quartoIds = quartoIds; }
    public LocalDate getDataCheckinPrevista() { return dataCheckinPrevista; }
    public void setDataCheckinPrevista(LocalDate dataCheckinPrevista) { this.dataCheckinPrevista = dataCheckinPrevista; }
    public LocalDate getDataCheckoutPrevista() { return dataCheckoutPrevista; }
    public void setDataCheckoutPrevista(LocalDate dataCheckoutPrevista) { this.dataCheckoutPrevista = dataCheckoutPrevista; }
    public Integer getQuantidadeHospedes() { return quantidadeHospedes; }
    public void setQuantidadeHospedes(Integer quantidadeHospedes) { this.quantidadeHospedes = quantidadeHospedes; }
}
