
package br.com.backend.gerenciamento_hotel.DTOs.Request;
import java.util.UUID;
import java.time.LocalDate;

import br.com.backend.gerenciamento_hotel.Enums.PrioridadeManutencao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrdemDeManutencaoRequestDTO {
    
    @NotNull
    private UUID quartoId;
    
    @NotBlank
    private String descricaoProblema;
    
    @NotNull
    private PrioridadeManutencao prioridade;
    
    public UUID getQuartoId() { return quartoId; }
    public void setQuartoId(UUID quartoId) { this.quartoId = quartoId; }
    
    public String getDescricaoProblema() { return descricaoProblema; }
    public void setDescricaoProblema(String descricaoProblema) { this.descricaoProblema = descricaoProblema; }
    
    public PrioridadeManutencao getPrioridade() { return prioridade; }
    public void setPrioridade(PrioridadeManutencao prioridade) { this.prioridade = prioridade; }
}
