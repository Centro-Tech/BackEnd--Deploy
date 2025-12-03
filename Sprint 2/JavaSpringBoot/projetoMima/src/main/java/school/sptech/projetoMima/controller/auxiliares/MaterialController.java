package school.sptech.projetoMima.controller.auxiliares;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import school.sptech.projetoMima.entity.item.Material;
import school.sptech.projetoMima.service.auxiliares.MaterialService;

import java.util.List;

@RestController
@RequestMapping("/materiais")
public class MaterialController {

    private final MaterialService service;

    public MaterialController(MaterialService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos os materiais")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Materiais encontrados com sucesso") })
    @GetMapping
    public List<Material> listar() {
        return service.listar();
    }

    @Operation(summary = "Buscar material por ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Material encontrado com sucesso"), @ApiResponse(responseCode = "404", description = "Material não encontrado") })
    @GetMapping("/{id}")
    public Material buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @Operation(summary = "Cadastrar novo material")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Material cadastrado com sucesso"), @ApiResponse(responseCode = "400", description = "Dados inválidos para cadastro"), @ApiResponse(responseCode = "409", description = "Material já existente")})
    @PostMapping
    public Material criar(@RequestBody Material material) {
        return service.salvar(material);
    }


    @Operation(summary = "Excluir material por ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Material excluído com sucesso"), @ApiResponse(responseCode = "404", description = "Material não encontrado para exclusão") })
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }
}
