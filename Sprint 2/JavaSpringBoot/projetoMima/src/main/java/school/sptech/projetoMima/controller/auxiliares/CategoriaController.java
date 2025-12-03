package school.sptech.projetoMima.controller.auxiliares;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import school.sptech.projetoMima.entity.item.Categoria;
import school.sptech.projetoMima.service.auxiliares.CategoriaService;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas as categorias")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Categorias listadas com sucesso") })
    @GetMapping
    public List<Categoria> listar() {
        return service.listar();
    }

    @Operation(summary = "Buscar categoria por ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso"), @ApiResponse(responseCode = "404", description = "Categoria não encontrada") })
    @GetMapping("/{id}")
    public Categoria buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @Operation(summary = "Cadastrar nova categoria")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Categoria cadastrada com sucesso"), @ApiResponse(responseCode = "400", description = "Dados inválidos para cadastro"), @ApiResponse(responseCode = "409", description = "Categoria já existente") })
    @PostMapping
    public Categoria criar(@RequestBody Categoria categoria) {
        return service.salvar(categoria);
    }

    @Operation(summary = "Excluir categoria por ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Categoria excluída com sucesso"), @ApiResponse(responseCode = "404", description = "Categoria não encontrada para exclusão") })
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }
}
