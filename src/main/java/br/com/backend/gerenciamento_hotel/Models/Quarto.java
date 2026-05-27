package br.com.backend.gerenciamento_hotel.Models;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.backend.gerenciamento_hotel.Enums.StatusDosQuartos;
import br.com.backend.gerenciamento_hotel.Enums.TiposDeQuartos;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quartos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quarto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer numero;

    @Enumerated(EnumType.STRING)
    private TiposDeQuartos tipo;

    private Integer capacidade;

    @Column(name = "valor_da_diaria")
    private BigDecimal valorDaDiaria;

    @Enumerated(EnumType.STRING)
    private StatusDosQuartos status;

    @ManyToOne
    @JoinColumn(name = "andar_id")
    private Andar andar;

    @OneToMany(mappedBy = "quarto")
    private List<ItemReserva> itensReserva;
}
