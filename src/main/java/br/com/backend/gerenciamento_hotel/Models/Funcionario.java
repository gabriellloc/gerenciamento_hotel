package br.com.backend.gerenciamento_hotel.Models;
import java.util.UUID;
import java.time.LocalDate;

import java.util.List;


import br.com.backend.gerenciamento_hotel.Enums.CargoDosFuncionarios;
import br.com.backend.gerenciamento_hotel.Enums.TurnoDosFuncionarios;
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
@Table(name = "Funcionarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private CargoDosFuncionarios cargo;

    @Enumerated(EnumType.STRING)
    private TurnoDosFuncionarios turno;

    private String telefone;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @OneToMany(mappedBy = "funcionario")
    private List<TarefaDeLimpeza> tarefasDeLimpeza;

    @OneToMany(mappedBy = "tecnico")
    private List<OrdemDeManutencao> ordensDeManutencao;
}
