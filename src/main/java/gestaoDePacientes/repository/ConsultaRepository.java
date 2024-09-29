package gestaoDePacientes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gestaoDePacientes.model.Consulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    // Adicione este método
    boolean existsByPacienteId(Long pacienteId);
    
    // Outros métodos existentes
    List<Consulta> findByPacienteId(Long pacienteId);
}
