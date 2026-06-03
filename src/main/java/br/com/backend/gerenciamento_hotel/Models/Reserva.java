package br.com.backend.gerenciamento_hotel.Models;
import java.util.UUID;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;


import br.com.backend.gerenciamento_hotel.Enums.StatusDeReservas;
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
@Table(name = "Reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "dia_e_hora_da_reserva")
    private LocalDateTime diaEHoraDaReserva;

    @Column(name = "data_e_hora")
    private LocalDateTime DataHora;

    @Column(name = "quantidade_de_hospedes")
    private Integer quantidadeDeHospedes;

    @Enumerated(EnumType.STRING)
    private StatusDeReservas status;

    @ManyToOne
    @JoinColumn(name = "hospede_id")
    private Hospede hospede;

    @OneToMany(mappedBy = "reserva")
    private List<ItemReserva> itens;
}
