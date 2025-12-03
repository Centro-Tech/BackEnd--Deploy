package school.sptech.projetoMima.controller.auxiliares;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import school.sptech.projetoMima.entity.item.Cor;
import school.sptech.projetoMima.service.auxiliares.CorService;

import java.util.List;

@RestController
@RequestMapping("/cores")
public class CorController {

    private final CorService service;

    public CorController(CorService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas as cores")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cores encontradas com sucesso") })
    @GetMapping
    public List<Cor> listar() {
        return service.listar();
    }

    @Operation(summary = "Buscar cor por ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cor encontrada com sucesso"), @ApiResponse(responseCode = "404", description = "Cor não encontrada") })
    @GetMapping("/{id}")
    public Cor buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @Operation(summary = "Cadastrar nova cor")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Cor cadastrada com sucesso"), @ApiResponse(responseCode = "400", description = "Dados inválidos para cadastro"), @ApiResponse(responseCode = "409", description = "Cor já existente")})
    @PostMapping
    public Cor criar(@RequestBody Cor cor) {
        return service.salvar(cor);
    }


    @Operation(summary = "Excluir cor por ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Cor excluída com sucesso"), @ApiResponse(responseCode = "404", description = "Cor não encontrada para exclusão") })
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }
}
