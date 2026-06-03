package br.com.backend.gerenciamento_hotel.Models;
import java.util.UUID;
import java.time.LocalDate;

import java.util.List;


import jakarta.persistence.Entity;
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
@Table(name = "andares")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Andar {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer numero;

    @ManyToOne
    @JoinColumn(name = "torre_id")
    private Torre torre;

    @OneToMany(mappedBy = "andar")
    private List<Quarto> quartos;
}
