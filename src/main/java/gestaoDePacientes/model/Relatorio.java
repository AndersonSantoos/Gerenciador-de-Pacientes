package gestaoDePacientes.model;

import java.time.LocalDateTime;

public class Relatorio {
    private Long pacienteId;
    private String pacienteNome;
    private LocalDateTime dataConsulta;
    private String descricaoPrescricao;

    // Getters e Setters
    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getPacienteNome() {
        return pacienteNome;
    }

    public void setPacienteNome(String pacienteNome) {
        this.pacienteNome = pacienteNome;
    }

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDateTime dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getDescricaoPrescricao() {
        return descricaoPrescricao;
    }

    public void setDescricaoPrescricao(String descricaoPrescricao) {
        this.descricaoPrescricao = descricaoPrescricao;
    }
}
