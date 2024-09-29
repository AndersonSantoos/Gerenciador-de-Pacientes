package gestaoDePacientes.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gestaoDePacientes.exception.ResourceNotFoundException;
import gestaoDePacientes.model.Consulta;
import gestaoDePacientes.model.Prescricao;
import gestaoDePacientes.model.Relatorio;
import gestaoDePacientes.repository.ConsultaRepository;
import gestaoDePacientes.repository.PrescricaoRepository;

@Service
public class RelatorioService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PrescricaoRepository prescricaoRepository;

    public List<Relatorio> gerarRelatorio(Long pacienteId) {
        // Verifica se o paciente existe
        if (!consultaRepository.existsByPacienteId(pacienteId)) {
            throw new ResourceNotFoundException("Paciente não encontrado com ID: " + pacienteId);
        }

        // Buscando todas as consultas do paciente
        List<Consulta> consultas = consultaRepository.findByPacienteId(pacienteId);
        
        // Buscando todas as prescrições associadas às consultas do paciente
        List<Prescricao> prescricoes = prescricaoRepository.findByConsulta_Paciente_Id(pacienteId);
        
        return consultas.stream()
            .map(consulta -> {
                Relatorio relatorio = new Relatorio();
                relatorio.setPacienteId(pacienteId);
                relatorio.setPacienteNome(consulta.getPaciente().getNome());
                relatorio.setDataConsulta(consulta.getDataHora());

                // Associando a última prescrição para cada consulta
                Prescricao ultimaPrescricao = prescricoes.stream()
                    .filter(p -> p.getConsulta().getId().equals(consulta.getId()))
                    .findFirst()
                    .orElse(null);

                if (ultimaPrescricao != null) {
                    relatorio.setDescricaoPrescricao(ultimaPrescricao.getDescricao());
                }

                return relatorio;
            })
            .collect(Collectors.toList());
    }
}
