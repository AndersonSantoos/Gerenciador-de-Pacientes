package gestaoDePacientes.model;

import java.util.List;

public class RelatorioDTO {

    private Long pacienteId; // ID do paciente
    private List<Consulta> consultas; // Lista de consultas associadas ao paciente
    private List<Prescricao> prescricoes; // Lista de prescrições associadas ao paciente

    // Construtor padrão
    public RelatorioDTO() {
    }

    // Construtor com parâmetros
    public RelatorioDTO(Long pacienteId, List<Consulta> consultas, List<Prescricao> prescricoes) {
        this.pacienteId = pacienteId;
        this.consultas = consultas;
        this.prescricoes = prescricoes;
    }

    // Getters e Setters
    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public List<Prescricao> getPrescricoes() {
        return prescricoes;
    }

    public void setPrescricoes(List<Prescricao> prescricoes) {
        this.prescricoes = prescricoes;
    }
}
