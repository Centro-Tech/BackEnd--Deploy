package school.sptech.projetoMima.infrastructure.web.controller.auxiliares;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import school.sptech.projetoMima.core.application.usecase.Item.auxiliares.MaterialUseCase.*;
import school.sptech.projetoMima.core.domain.item.Material;

import java.util.List;

@RestController
@RequestMapping("/materiais")
@Tag(name = "Material", description = "Operações relacionadas aos materiais")
public class MaterialController {

    private final CriarMaterialUseCase criarMaterialUseCase;
    private final BuscarMaterialPorIdUseCase buscarMaterialPorIdUseCase;
    private final ListarMateriaisUseCase listarMateriaisUseCase;
    private final AtualizarMaterialUseCase atualizarMaterialUseCase;
    private final DeletarMaterialUseCase deletarMaterialUseCase;

    public MaterialController(CriarMaterialUseCase criarMaterialUseCase,
                             BuscarMaterialPorIdUseCase buscarMaterialPorIdUseCase,
                             ListarMateriaisUseCase listarMateriaisUseCase,
                             AtualizarMaterialUseCase atualizarMaterialUseCase,
                             DeletarMaterialUseCase deletarMaterialUseCase) {
        this.criarMaterialUseCase = criarMaterialUseCase;
        this.buscarMaterialPorIdUseCase = buscarMaterialPorIdUseCase;
        this.listarMateriaisUseCase = listarMateriaisUseCase;
        this.atualizarMaterialUseCase = atualizarMaterialUseCase;
        this.deletarMaterialUseCase = deletarMaterialUseCase;
    }

    @Operation(summary = "Listar todos os materiais")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Materiais encontrados com sucesso") })
    @GetMapping
    public List<Material> listar() {
        return listarMateriaisUseCase.execute();
    }

    @Operation(summary = "Buscar material por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Material encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Material não encontrado")
    })
    @GetMapping("/{id}")
    public Material buscarPorId(@PathVariable Integer id) {
        return buscarMaterialPorIdUseCase.execute(id);
    }

    @Operation(summary = "Cadastrar novo material")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Material cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos para cadastro"),
        @ApiResponse(responseCode = "409", description = "Material já existente")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Material criar(@RequestBody Material material) {
        return criarMaterialUseCase.execute(material);
    }

    @Operation(summary = "Atualizar material")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Material atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Material não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização")
    })
    @PutMapping("/{id}")
    public Material atualizar(@PathVariable Integer id, @RequestBody String nome) {
        return atualizarMaterialUseCase.execute(id, nome);
    }

    @Operation(summary = "Excluir material por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Material excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Material não encontrado para exclusão")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id) {
        deletarMaterialUseCase.execute(id);
    }
}
