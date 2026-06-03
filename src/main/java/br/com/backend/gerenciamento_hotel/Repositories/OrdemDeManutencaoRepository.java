package br.com.backend.gerenciamento_hotel.Repositories;

import br.com.backend.gerenciamento_hotel.Enums.PrioridadeManutencao;
import br.com.backend.gerenciamento_hotel.Enums.StatusManutencao;
import br.com.backend.gerenciamento_hotel.Models.OrdemDeManutencao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrdemDeManutencaoRepository extends JpaRepository<OrdemDeManutencao, UUID> {

    List<OrdemDeManutencao> findByQuarto_Id(UUID quartoId);

    List<OrdemDeManutencao> findByTecnico_Id(UUID tecnicoId);


    List<OrdemDeManutencao> findByStatus(StatusManutencao status);

    List<OrdemDeManutencao> findByPrioridade(PrioridadeManutencao prioridade);
}