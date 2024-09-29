package gestaoDePacientes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gestaoDePacientes.model.Agendamento;
import gestaoDePacientes.model.Medico;
import gestaoDePacientes.model.Paciente;
import gestaoDePacientes.repository.AgendamentoRepository;
import gestaoDePacientes.repository.MedicoRepository;
import gestaoDePacientes.repository.PacienteRepository;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public List<Agendamento> findAll() {
        return agendamentoRepository.findAll();
    }

    public Optional<Agendamento> findById(Long id) {
        return agendamentoRepository.findById(id);
    }

    public Agendamento save(Agendamento agendamento, Long pacienteId, Long medicoId) {
        Optional<Paciente> paciente = pacienteRepository.findById(pacienteId);
        Optional<Medico> medico = medicoRepository.findById(medicoId);

        if (paciente.isPresent() && medico.isPresent()) {
            agendamento.setPaciente(paciente.get());
            agendamento.setMedico(medico.get());
            return agendamentoRepository.save(agendamento);
        } else {
            throw new IllegalArgumentException("Paciente ou Médico não encontrado");
        }
    }

    public void deleteById(Long id) {
        agendamentoRepository.deleteById(id);
    }
}
