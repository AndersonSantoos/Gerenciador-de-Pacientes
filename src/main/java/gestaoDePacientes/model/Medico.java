package gestaoDePacientes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome não pode ter mais de 100 caracteres")
    private String nome;

    @NotBlank(message = "Especialidade é obrigatória")
    private String especialidade;

    @NotBlank(message = "Registro CRM é obrigatório")
    @Size(max = 20, message = "Registro CRM não pode ter mais de 20 caracteres")
    @Column(unique = true)
    private String registroCRM;

    @Column(unique = true)
    private String email;

    private String telefone;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getRegistroCRM() {
        return registroCRM;
    }

    public void setRegistroCRM(String registroCRM) {
        this.registroCRM = registroCRM;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
