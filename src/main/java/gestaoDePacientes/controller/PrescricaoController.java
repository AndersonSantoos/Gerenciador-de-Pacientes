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
import gestaoDePacientes.model.Consulta;
import gestaoDePacientes.model.Prescricao;
import gestaoDePacientes.model.PrescricaoDTO;
import gestaoDePacientes.repository.ConsultaRepository;
import gestaoDePacientes.service.PrescricaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/prescricoes")
public class PrescricaoController {

    @Autowired
    private PrescricaoService prescricaoService;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Operation(summary = "List all prescriptions", description = "Retrieve a list of all prescriptions")
    @GetMapping
    public List<PrescricaoDTO> listarTodos() {
        return prescricaoService.listarTodos().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get prescription by ID", description = "Retrieve a specific prescription by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prescription found"),
        @ApiResponse(responseCode = "404", description = "Prescription not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PrescricaoDTO> buscarPorId(@Parameter(description = "ID of the prescription to be retrieved") @PathVariable Long id) {
        try {
            Prescricao prescricao = prescricaoService.buscarPorId(id);
            return ResponseEntity.ok(converterParaDTO(prescricao));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create new prescription", description = "Save a new prescription")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prescription created"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<PrescricaoDTO> salvar(@RequestBody PrescricaoDTO prescricaoDTO) {
        Prescricao prescricao = converterParaEntidade(prescricaoDTO);
        Prescricao prescricaoSalva = prescricaoService.salvar(prescricao);
        return ResponseEntity.ok(converterParaDTO(prescricaoSalva));
    }

    @Operation(summary = "Update prescription", description = "Update an existing prescription by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prescription updated"),
        @ApiResponse(responseCode = "404", description = "Prescription not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PrescricaoDTO> atualizar(@Parameter(description = "ID of the prescription to be updated") @PathVariable Long id, @RequestBody PrescricaoDTO prescricaoDTO) {
        try {
            Prescricao prescricaoAtualizada = prescricaoService.atualizar(id, converterParaEntidade(prescricaoDTO));
            return ResponseEntity.ok(converterParaDTO(prescricaoAtualizada));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete prescription", description = "Delete a prescription by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Prescription deleted"),
        @ApiResponse(responseCode = "404", description = "Prescription not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@Parameter(description = "ID of the prescription to be deleted") @PathVariable Long id) {
        try {
            prescricaoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private PrescricaoDTO converterParaDTO(Prescricao prescricao) {
        PrescricaoDTO dto = new PrescricaoDTO();
        dto.setId(prescricao.getId());
        dto.setDescricao(prescricao.getDescricao());
        dto.setConsultaId(prescricao.getConsulta().getId());
        return dto;
    }

    private Prescricao converterParaEntidade(PrescricaoDTO prescricaoDTO) {
        Prescricao prescricao = new Prescricao();
        prescricao.setId(prescricaoDTO.getId());
        prescricao.setDescricao(prescricaoDTO.getDescricao());
        Consulta consulta = consultaRepository.findById(prescricaoDTO.getConsultaId())
            .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));
        prescricao.setConsulta(consulta);
        return prescricao;
    }
}
