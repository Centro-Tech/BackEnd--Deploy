package school.sptech.projetoMima.infrastructure.web.controller.auxiliares;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import school.sptech.projetoMima.core.application.usecase.Item.auxiliares.CategoriaUseCase.*;
import school.sptech.projetoMima.core.domain.item.Categoria;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categoria", description = "Operações relacionadas às categorias")
public class CategoriaController {

    private final CriarCategoriaUseCase criarCategoriaUseCase;
    private final BuscarCategoriaPorIdUseCase buscarCategoriaPorIdUseCase;
    private final ListarCategoriasUseCase listarCategoriasUseCase;
    private final AtualizarCategoriaUseCase atualizarCategoriaUseCase;
    private final DeletarCategoriaUseCase deletarCategoriaUseCase;

    public CategoriaController(CriarCategoriaUseCase criarCategoriaUseCase,
                              BuscarCategoriaPorIdUseCase buscarCategoriaPorIdUseCase,
                              ListarCategoriasUseCase listarCategoriasUseCase,
                              AtualizarCategoriaUseCase atualizarCategoriaUseCase,
                              DeletarCategoriaUseCase deletarCategoriaUseCase) {
        this.criarCategoriaUseCase = criarCategoriaUseCase;
        this.buscarCategoriaPorIdUseCase = buscarCategoriaPorIdUseCase;
        this.listarCategoriasUseCase = listarCategoriasUseCase;
        this.atualizarCategoriaUseCase = atualizarCategoriaUseCase;
        this.deletarCategoriaUseCase = deletarCategoriaUseCase;
    }

    @Operation(summary = "Listar todas as categorias")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Categorias listadas com sucesso") })
    @GetMapping
    public List<Categoria> listar() {
        return listarCategoriasUseCase.execute();
    }

    @Operation(summary = "Buscar categoria por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping("/{id}")
    public Categoria buscarPorId(@PathVariable Integer id) {
        return buscarCategoriaPorIdUseCase.execute(id);
    }

    @Operation(summary = "Cadastrar nova categoria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoria cadastrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos para cadastro"),
        @ApiResponse(responseCode = "409", description = "Categoria já existente")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria criar(@RequestBody Categoria categoria) {
        return criarCategoriaUseCase.execute(categoria);
    }

    @Operation(summary = "Atualizar categoria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização")
    })
    @PutMapping("/{id}")
    public Categoria atualizar(@PathVariable Integer id, @RequestBody String nome) {
        return atualizarCategoriaUseCase.execute(id, nome);
    }

    @Operation(summary = "Excluir categoria por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoria excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada para exclusão")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id) {
        deletarCategoriaUseCase.execute(id);
    }
}
