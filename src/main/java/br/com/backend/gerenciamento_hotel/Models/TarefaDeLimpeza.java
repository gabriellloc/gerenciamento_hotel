package br.com.backend.gerenciamento_hotel.Models;
import java.util.UUID;
import java.time.LocalDate;

import java.time.LocalDateTime;


import org.hibernate.annotations.CreationTimestamp;

import br.com.backend.gerenciamento_hotel.Enums.StatusDaTarefa;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tarefa_de_limpeza")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaDeLimpeza {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "data_de_abertura", nullable=false, updatable=false)
    @CreationTimestamp
    private LocalDateTime dataDeAbertura;

    @Column(name = "data_de_conclusao")
    private LocalDateTime dataDeConclusao;

    @Enumerated(EnumType.STRING)
    private StatusDaTarefa status;

    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "quarto_id")
    private Quarto quarto;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;
}
