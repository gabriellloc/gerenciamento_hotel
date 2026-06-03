package br.com.backend.gerenciamento_hotel.Models;
import java.util.UUID;
import java.time.LocalDate;

import java.time.LocalDateTime;


import org.hibernate.annotations.CreationTimestamp;

import br.com.backend.gerenciamento_hotel.Enums.PrioridadeManutencao;
import br.com.backend.gerenciamento_hotel.Enums.StatusManutencao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ordens_manutencao")
@Data
public class OrdemDeManutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "descricao_problema")
    private String descricaoProblema;

    @CreationTimestamp
    @Column(name = "data_de_abertura", updatable = false)
    private LocalDateTime dataDeAbertura;

    @Column(name = "data_de_conclusao")
    private LocalDateTime dataDeConclusao;

    @Enumerated(EnumType.STRING)
    private StatusManutencao status;

    @Enumerated(EnumType.STRING)
    private PrioridadeManutencao prioridade;

    @ManyToOne
    @JoinColumn(name = "quarto_id")
    private Quarto quarto;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Funcionario tecnico;
}
