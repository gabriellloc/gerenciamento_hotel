package br.com.backend.gerenciamento_hotel.Models;
import java.util.UUID;
import java.time.LocalDate;

import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hospedes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hospede {
    

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    private String CPF;
    
    private String telefone;

    private String email;

    @Column(name = "data_de_nascimento")
    private LocalDate dataDeNascimento;

    @OneToMany(mappedBy = "hospede")
    private List<Reserva> reservas;

}
