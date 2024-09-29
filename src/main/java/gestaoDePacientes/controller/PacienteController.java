package gestaoDePacientes.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gestaoDePacientes.exception.ResourceNotFoundException;
import gestaoDePacientes.model.Paciente;
import gestaoDePacientes.model.PacienteDTO;
import gestaoDePacientes.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @Operation(summary = "List all pacientes", description = "Retrieve a list of all pacientes")
    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listarTodos() {
        List<Paciente> pacientes = pacienteService.listarTodos();
        List<PacienteDTO> pacienteDTOs = pacientes.stream()
                .map(paciente -> {
                    PacienteDTO dto = new PacienteDTO();
                    dto.setId(paciente.getId());
                    dto.setNome(paciente.getNome());
                    dto.setCpf(paciente.getCpf());
                    dto.setTelefone(paciente.getTelefone());
                    dto.setEmail(paciente.getEmail());
                    dto.setDataNascimento(paciente.getDataNascimento());
                    dto.setEndereco(paciente.getEndereco());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(pacienteDTOs);
    }

    @Operation(summary = "Get paciente by ID", description = "Retrieve a specific paciente by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paciente found"),
        @ApiResponse(responseCode = "404", description = "Paciente not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscarPorId(@Parameter(description = "ID of the paciente to be retrieved") @PathVariable Long id) {
        Paciente paciente = pacienteService.buscarPorId(id);
        if (paciente != null) {
            PacienteDTO pacienteDTO = new PacienteDTO();
            pacienteDTO.setId(paciente.getId());
            pacienteDTO.setNome(paciente.getNome());
            pacienteDTO.setCpf(paciente.getCpf());
            pacienteDTO.setTelefone(paciente.getTelefone());
            pacienteDTO.setEmail(paciente.getEmail());
            pacienteDTO.setDataNascimento(paciente.getDataNascimento());
            pacienteDTO.setEndereco(paciente.getEndereco());
            return ResponseEntity.ok(pacienteDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Create new paciente", description = "Save a new paciente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paciente created"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<PacienteDTO> salvar(@RequestBody Paciente paciente) {
        Paciente pacienteSalvo = pacienteService.salvar(paciente);
        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setId(pacienteSalvo.getId());
        pacienteDTO.setNome(pacienteSalvo.getNome());
        pacienteDTO.setCpf(pacienteSalvo.getCpf());
        pacienteDTO.setTelefone(pacienteSalvo.getTelefone());
        pacienteDTO.setEmail(pacienteSalvo.getEmail());
        pacienteDTO.setDataNascimento(pacienteSalvo.getDataNascimento());
        pacienteDTO.setEndereco(pacienteSalvo.getEndereco());
        return ResponseEntity.ok(pacienteDTO);
    }

    @Operation(summary = "Update paciente", description = "Update an existing paciente by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paciente updated"),
        @ApiResponse(responseCode = "404", description = "Paciente not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> atualizar(@Parameter(description = "ID of the paciente to be updated") @PathVariable Long id, @RequestBody Paciente pacienteAtualizado) {
        try {
            Paciente pacienteAtualizadoResult = pacienteService.atualizar(id, pacienteAtualizado);
            PacienteDTO pacienteDTO = new PacienteDTO();
            pacienteDTO.setId(pacienteAtualizadoResult.getId());
            pacienteDTO.setNome(pacienteAtualizadoResult.getNome());
            pacienteDTO.setCpf(pacienteAtualizadoResult.getCpf());
            pacienteDTO.setTelefone(pacienteAtualizadoResult.getTelefone());
            pacienteDTO.setEmail(pacienteAtualizadoResult.getEmail());
            pacienteDTO.setDataNascimento(pacienteAtualizadoResult.getDataNascimento());
            pacienteDTO.setEndereco(pacienteAtualizadoResult.getEndereco());
            return ResponseEntity.ok(pacienteDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete paciente", description = "Delete a paciente by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Paciente deleted"),
        @ApiResponse(responseCode = "404", description = "Paciente not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@Parameter(description = "ID of the paciente to be deleted") @PathVariable Long id) {
        try {
            pacienteService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
