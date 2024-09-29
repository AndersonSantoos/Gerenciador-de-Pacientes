package gestaoDePacientes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gestaoDePacientes.model.Administrador;
import gestaoDePacientes.service.AdministradorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/administradores")
@Tag(name = "Administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @GetMapping
    @Operation(summary = "Listar todos os administradores")
    public ResponseEntity<List<Administrador>> listarTodos() {
        List<Administrador> administradores = administradorService.listarTodos();
        return ResponseEntity.ok(administradores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar administrador por ID")
    @ApiResponse(responseCode = "200", description = "Administrador encontrado")
    @ApiResponse(responseCode = "404", description = "Administrador não encontrado")
    public ResponseEntity<Administrador> buscarPorId(@PathVariable Long id) {
        Administrador administrador = administradorService.buscarPorId(id);
        return ResponseEntity.ok(administrador);
    }

    @PostMapping
    @Operation(summary = "Salvar um novo administrador")
    public ResponseEntity<Administrador> salvar(@RequestBody Administrador administrador) {
        Administrador administradorSalvo = administradorService.salvar(administrador);
        return ResponseEntity.ok(administradorSalvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um administrador existente")
    @ApiResponse(responseCode = "200", description = "Administrador atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Administrador não encontrado")
    public ResponseEntity<Administrador> atualizar(@PathVariable Long id, @RequestBody Administrador administradorAtualizado) {
        Administrador administradorAtualizadoResult = administradorService.atualizar(id, administradorAtualizado);
        return ResponseEntity.ok(administradorAtualizadoResult);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um administrador por ID")
    @ApiResponse(responseCode = "204", description = "Administrador deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Administrador não encontrado")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        administradorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
