package gestaoDePacientes.controller;

import java.util.List;

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

import gestaoDePacientes.model.Consulta;
import gestaoDePacientes.model.ConsultaDTO;
import gestaoDePacientes.service.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Operation(summary = "Get all consultas", description = "Retrieve a list of all consultas")
    @GetMapping
    public ResponseEntity<List<ConsultaDTO>> getAllConsultas() {
        List<Consulta> consultas = consultaService.findAll();
        List<ConsultaDTO> consultaDTOs = consultas.stream()
                .map(this::convertToDTO)
                .toList(); // Converte a lista de Consulta para ConsultaDTO
        return ResponseEntity.ok(consultaDTOs);
    }

    @Operation(summary = "Get consulta by ID", description = "Retrieve a specific consulta by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consulta found"),
        @ApiResponse(responseCode = "404", description = "Consulta not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> getConsultaById(@Parameter(description = "ID of the consulta to be retrieved") @PathVariable Long id) {
        return consultaService.findById(id)
                .map(consulta -> ResponseEntity.ok(convertToDTO(consulta)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create consulta", description = "Create a new consulta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consulta created"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<ConsultaDTO> createConsulta(@RequestBody ConsultaDTO consultaDTO) {
        Consulta consulta = new Consulta();
        consulta.setDataHora(consultaDTO.getDataHora());
        consulta.setDescricao(consultaDTO.getDescricao());
        
        Consulta savedConsulta = consultaService.save(consulta, consultaDTO.getPacienteId(), consultaDTO.getMedicoId());
        return ResponseEntity.ok(convertToDTO(savedConsulta)); // Retorna o DTO do que foi cadastrado
    }

    @Operation(summary = "Update consulta", description = "Update an existing consulta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consulta updated"),
        @ApiResponse(responseCode = "404", description = "Consulta not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ConsultaDTO> updateConsulta(@Parameter(description = "ID of the consulta to be updated") @PathVariable Long id, @RequestBody ConsultaDTO consultaDTO) {
        return consultaService.findById(id)
                .map(existingConsulta -> {
                    existingConsulta.setDataHora(consultaDTO.getDataHora());
                    existingConsulta.setDescricao(consultaDTO.getDescricao());
                    Consulta updatedConsulta = consultaService.save(existingConsulta, consultaDTO.getPacienteId(), consultaDTO.getMedicoId());
                    return ResponseEntity.ok(convertToDTO(updatedConsulta)); // Retorna o DTO atualizado
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete consulta", description = "Delete a consulta by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Consulta deleted"),
        @ApiResponse(responseCode = "404", description = "Consulta not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsulta(@Parameter(description = "ID of the consulta to be deleted") @PathVariable Long id) {
        if (consultaService.findById(id).isPresent()) {
            consultaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Método auxiliar para converter Consulta em ConsultaDTO
    private ConsultaDTO convertToDTO(Consulta consulta) {
        ConsultaDTO consultaDTO = new ConsultaDTO();
        consultaDTO.setId(consulta.getId());
        consultaDTO.setDataHora(consulta.getDataHora());
        consultaDTO.setDescricao(consulta.getDescricao());
        consultaDTO.setPacienteId(consulta.getPaciente().getId());
        consultaDTO.setMedicoId(consulta.getMedico().getId());
        return consultaDTO;
    }
}
