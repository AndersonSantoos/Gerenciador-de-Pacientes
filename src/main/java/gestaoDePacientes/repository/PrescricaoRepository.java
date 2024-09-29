package gestaoDePacientes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gestaoDePacientes.model.Prescricao;

public interface PrescricaoRepository extends JpaRepository<Prescricao, Long> {
    List<Prescricao> findByConsulta_Paciente_Id(Long pacienteId); 
}
