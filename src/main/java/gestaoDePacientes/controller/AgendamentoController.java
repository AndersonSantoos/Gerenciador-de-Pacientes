package gestaoDePacientes.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gestaoDePacientes.model.Agendamento;
import gestaoDePacientes.model.AgendamentoDTO;
import gestaoDePacientes.service.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @Operation(summary = "Get all agendamentos", description = "Retrieve a list of all agendamentos")
    @GetMapping
    public ResponseEntity<List<AgendamentoDTO>> getAllAgendamentos() {
        List<Agendamento> agendamentos = agendamentoService.findAll();
        List<AgendamentoDTO> agendamentoDTOs = agendamentos.stream()
                .map(agendamento -> {
                    AgendamentoDTO dto = new AgendamentoDTO();
                    dto.setId(agendamento.getId());
                    dto.setDataHora(agendamento.getDataHora());
                    dto.setPacienteId(agendamento.getPaciente().getId());
                    dto.setMedicoId(agendamento.getMedico().getId());
                    dto.setObservacao(agendamento.getObservacao());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(agendamentoDTOs);
    }

    @Operation(summary = "Get agendamento by ID", description = "Retrieve a specific agendamento by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agendamento found"),
        @ApiResponse(responseCode = "404", description = "Agendamento not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> getAgendamentoById(@Parameter(description = "ID of the agendamento to be retrieved") @PathVariable Long id) {
        return agendamentoService.findById(id)
                .map(agendamento -> {
                    AgendamentoDTO dto = new AgendamentoDTO();
                    dto.setId(agendamento.getId());
                    dto.setDataHora(agendamento.getDataHora());
                    dto.setPacienteId(agendamento.getPaciente().getId());
                    dto.setMedicoId(agendamento.getMedico().getId());
                    dto.setObservacao(agendamento.getObservacao());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create agendamento", description = "Create a new agendamento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Agendamento created"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<AgendamentoDTO> createAgendamento(@RequestBody AgendamentoDTO agendamentoDTO) {
        Agendamento agendamento = new Agendamento();
        agendamento.setDataHora(agendamentoDTO.getDataHora());
        agendamento.setObservacao(agendamentoDTO.getObservacao());
        
        Agendamento savedAgendamento = agendamentoService.save(agendamento, agendamentoDTO.getPacienteId(), agendamentoDTO.getMedicoId());

        AgendamentoDTO savedAgendamentoDTO = new AgendamentoDTO();
        savedAgendamentoDTO.setId(savedAgendamento.getId());
        savedAgendamentoDTO.setDataHora(savedAgendamento.getDataHora());
        savedAgendamentoDTO.setObservacao(savedAgendamento.getObservacao());
        savedAgendamentoDTO.setPacienteId(savedAgendamento.getPaciente().getId());
        savedAgendamentoDTO.setMedicoId(savedAgendamento.getMedico().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(savedAgendamentoDTO);
    }

    @Operation(summary = "Update agendamento", description = "Update an existing agendamento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agendamento updated"),
        @ApiResponse(responseCode = "404", description = "Agendamento not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> updateAgendamento(@PathVariable Long id, @RequestBody AgendamentoDTO agendamentoDTO) {
        return agendamentoService.findById(id)
                .map(existingAgendamento -> {
                    existingAgendamento.setDataHora(agendamentoDTO.getDataHora());
                    existingAgendamento.setObservacao(agendamentoDTO.getObservacao());
                    Agendamento updatedAgendamento = agendamentoService.save(existingAgendamento, agendamentoDTO.getPacienteId(), agendamentoDTO.getMedicoId());
                    AgendamentoDTO updatedAgendamentoDTO = new AgendamentoDTO();
                    updatedAgendamentoDTO.setId(updatedAgendamento.getId());
                    updatedAgendamentoDTO.setDataHora(updatedAgendamento.getDataHora());
                    updatedAgendamentoDTO.setPacienteId(updatedAgendamento.getPaciente().getId());
                    updatedAgendamentoDTO.setMedicoId(updatedAgendamento.getMedico().getId());
                    updatedAgendamentoDTO.setObservacao(updatedAgendamento.getObservacao());
                    return ResponseEntity.ok(updatedAgendamentoDTO);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete agendamento", description = "Delete an agendamento by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Agendamento deleted"),
        @ApiResponse(responseCode = "404", description = "Agendamento not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgendamento(@Parameter(description = "ID of the agendamento to be deleted") @PathVariable Long id) {
        if (agendamentoService.findById(id).isPresent()) {
            agendamentoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
