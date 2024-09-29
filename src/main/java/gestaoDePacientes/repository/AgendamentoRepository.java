package gestaoDePacientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gestaoDePacientes.model.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
}
