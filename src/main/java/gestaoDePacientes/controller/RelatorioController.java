package gestaoDePacientes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gestaoDePacientes.exception.ResourceNotFoundException;
import gestaoDePacientes.model.Relatorio;
import gestaoDePacientes.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @Operation(summary = "Generate report for a patient", description = "Generate a list of reports based on the patient ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reports found"),
        @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @GetMapping("/{pacienteId}")
    public ResponseEntity<List<Relatorio>> gerarRelatorio(@Parameter(description = "ID of the patient for whom to generate the report") @PathVariable Long pacienteId) {
        try {
            List<Relatorio> relatorios = relatorioService.gerarRelatorio(pacienteId);
            return ResponseEntity.ok(relatorios);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 se paciente não for encontrado
        }
    }
}
