package gestaoDePacientes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gestaoDePacientes.exception.ResourceNotFoundException;
import gestaoDePacientes.model.Administrador;
import gestaoDePacientes.repository.AdministradorRepository;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    public List<Administrador> listarTodos() {
        return administradorRepository.findAll();
    }

    public Administrador salvar(Administrador administrador) {
        return administradorRepository.save(administrador); 
    }

    public Administrador buscarPorId(Long id) {
        return administradorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador não encontrado com ID: " + id));
    }

    public Administrador buscarPorEmail(String email) {
        return administradorRepository.findByEmail(email);
    }

    public void deletar(Long id) {
        if (!administradorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Administrador não encontrado com ID: " + id);
        }
        administradorRepository.deleteById(id);
    }

    public Administrador atualizar(Long id, Administrador administradorAtualizado) {
        Administrador administradorExistente = buscarPorId(id);
        administradorExistente.setNome(administradorAtualizado.getNome());
        administradorExistente.setEmail(administradorAtualizado.getEmail());
        
        // Atualiza a senha apenas se ela for diferente
        if (administradorAtualizado.getSenha() != null && !administradorAtualizado.getSenha().isEmpty()) {
            administradorExistente.setSenha(administradorAtualizado.getSenha()); // Define a nova senha
        }
        
        return administradorRepository.save(administradorExistente);
    }
}
