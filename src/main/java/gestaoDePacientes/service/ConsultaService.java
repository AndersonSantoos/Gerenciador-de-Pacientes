package gestaoDePacientes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gestaoDePacientes.model.Consulta;
import gestaoDePacientes.model.Medico;
import gestaoDePacientes.model.Paciente;
import gestaoDePacientes.repository.ConsultaRepository;
import gestaoDePacientes.repository.MedicoRepository;
import gestaoDePacientes.repository.PacienteRepository;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public List<Consulta> findAll() {
        return consultaRepository.findAll();
    }

    public Optional<Consulta> findById(Long id) {
        return consultaRepository.findById(id);
    }

    public Consulta save(Consulta consulta, Long pacienteId, Long medicoId) {
        Optional<Paciente> paciente = pacienteRepository.findById(pacienteId);
        Optional<Medico> medico = medicoRepository.findById(medicoId);

        if (paciente.isPresent() && medico.isPresent()) {
            consulta.setPaciente(paciente.get());
            consulta.setMedico(medico.get());
            return consultaRepository.save(consulta);
        } else {
            throw new IllegalArgumentException("Paciente ou Médico não encontrado");
        }
    }

    public void deleteById(Long id) {
        consultaRepository.deleteById(id);
    }
}
