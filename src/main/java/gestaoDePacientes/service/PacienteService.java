package gestaoDePacientes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gestaoDePacientes.exception.ResourceNotFoundException;
import gestaoDePacientes.model.Paciente; // Importando a exceção
import gestaoDePacientes.repository.PacienteRepository;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public Paciente salvar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public void deletar(Long id) {
        if (!pacienteRepository.existsById(id)) { // Verifica se o paciente existe
            throw new ResourceNotFoundException("Paciente não encontrado com ID: " + id);
        }
        pacienteRepository.deleteById(id);
    }

    public Paciente buscarPorId(Long id) {
        return pacienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com ID: " + id)); // Lança exceção se não encontrado
    }

    public Paciente atualizar(Long id, Paciente pacienteAtualizado) {
        Paciente pacienteExistente = buscarPorId(id); // Usa o método que lança exceção se não encontrado
        pacienteExistente.setNome(pacienteAtualizado.getNome());
        pacienteExistente.setDataNascimento(pacienteAtualizado.getDataNascimento());
        pacienteExistente.setCpf(pacienteAtualizado.getCpf());
        pacienteExistente.setEndereco(pacienteAtualizado.getEndereco());
        pacienteExistente.setTelefone(pacienteAtualizado.getTelefone());
        pacienteExistente.setEmail(pacienteAtualizado.getEmail());
        return pacienteRepository.save(pacienteExistente);
    }
}
