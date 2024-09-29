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

import gestaoDePacientes.model.Medico;
import gestaoDePacientes.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @Operation(summary = "Get all medicos", description = "Retrieve a list of all medicos")
    @GetMapping
    public ResponseEntity<List<Medico>> getAllMedicos() {
        List<Medico> medicos = medicoService.findAll();
        return ResponseEntity.ok(medicos);
    }

    @Operation(summary = "Get medico by ID", description = "Retrieve a specific medico by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Medico found"),
        @ApiResponse(responseCode = "404", description = "Medico not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Medico> getMedicoById(@Parameter(description = "ID of the medico to be retrieved") @PathVariable Long id) {
        return medicoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create medico", description = "Create a new medico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Medico created"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Medico> createMedico(@RequestBody Medico medico) {
        Medico savedMedico = medicoService.save(medico);
        return ResponseEntity.ok(savedMedico);
    }

    @Operation(summary = "Update medico", description = "Update an existing medico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Medico updated"),
        @ApiResponse(responseCode = "404", description = "Medico not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Medico> updateMedico(@Parameter(description = "ID of the medico to be updated") @PathVariable Long id, @RequestBody Medico medico) {
        return medicoService.findById(id)
                .map(existingMedico -> {
                    medico.setId(existingMedico.getId());
                    Medico updatedMedico = medicoService.save(medico);
                    return ResponseEntity.ok(updatedMedico);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete medico", description = "Delete a medico by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Medico deleted"),
        @ApiResponse(responseCode = "404", description = "Medico not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedico(@Parameter(description = "ID of the medico to be deleted") @PathVariable Long id) {
        if (medicoService.findById(id).isPresent()) {
            medicoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
