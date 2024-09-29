package gestaoDePacientes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gestaoDePacientes.exception.ResourceNotFoundException;
import gestaoDePacientes.model.Consulta;
import gestaoDePacientes.model.Prescricao;
import gestaoDePacientes.repository.ConsultaRepository;
import gestaoDePacientes.repository.PrescricaoRepository;

@Service
public class PrescricaoService {

    @Autowired
    private PrescricaoRepository prescricaoRepository;

    @Autowired
    private ConsultaRepository consultaRepository; // Injetar o repositório de consulta

    public List<Prescricao> listarTodos() {
        return prescricaoRepository.findAll();
    }

    public Prescricao salvar(Prescricao prescricao) {
        // Buscar a consulta antes de salvar
        Consulta consulta = consultaRepository.findById(prescricao.getConsulta().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada com ID: " + prescricao.getConsulta().getId())); // Usar a exceção personalizada
        prescricao.setConsulta(consulta); // Setar a consulta
        return prescricaoRepository.save(prescricao);
    }

    public void deletar(Long id) {
        if (!prescricaoRepository.existsById(id)) { // Verifica se a prescrição existe
            throw new ResourceNotFoundException("Prescrição não encontrada com ID: " + id); // Usar a exceção personalizada
        }
        prescricaoRepository.deleteById(id);
    }

    public Prescricao buscarPorId(Long id) {
        return prescricaoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Prescrição não encontrada com ID: " + id)); // Lança exceção se não encontrado
    }

    public Prescricao atualizar(Long id, Prescricao prescricaoAtualizada) {
        Prescricao prescricaoExistente = buscarPorId(id); // Usa o método que lança exceção se não encontrado

        prescricaoExistente.setDescricao(prescricaoAtualizada.getDescricao());

        // Atualiza a consulta
        Consulta consulta = consultaRepository.findById(prescricaoAtualizada.getConsulta().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada com ID: " + prescricaoAtualizada.getConsulta().getId())); // Usar a exceção personalizada
        prescricaoExistente.setConsulta(consulta); // Setar a consulta

        return prescricaoRepository.save(prescricaoExistente);
    }
}
