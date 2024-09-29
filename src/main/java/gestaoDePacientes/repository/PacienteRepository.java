package gestaoDePacientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gestaoDePacientes.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
