package gestaoDePacientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gestaoDePacientes.model.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    
}
