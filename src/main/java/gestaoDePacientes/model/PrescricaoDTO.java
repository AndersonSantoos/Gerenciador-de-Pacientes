package gestaoDePacientes.model;

import java.time.LocalDateTime;

public class PrescricaoDTO {
    private Long id;
    private String descricao; 
    private Long consultaId; // ID da consulta associada
    private LocalDateTime dataPrescricao;
    private String posologia;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(Long consultaId) {
        this.consultaId = consultaId;
    }

    public LocalDateTime getDataPrescricao() {
        return dataPrescricao;
    }

    public void setDataPrescricao(LocalDateTime dataPrescricao) {
        this.dataPrescricao = dataPrescricao;
    }

    public String getPosologia() {
        return posologia;
    }

    public void setPosologia(String posologia) {
        this.posologia = posologia;
    }
}
