package school.sptech.projetoMima.infrastructure.web.controller.auxiliares;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import school.sptech.projetoMima.core.application.usecase.Item.auxiliares.CorUseCase.*;
import school.sptech.projetoMima.core.domain.item.Cor;

import java.util.List;

@RestController
@RequestMapping("/cores")
@Tag(name = "Cor", description = "Operações relacionadas às cores")
public class CorController {

    private final CriarCorUseCase criarCorUseCase;
    private final BuscarCorPorIdUseCase buscarCorPorIdUseCase;
    private final ListarCoresUseCase listarCoresUseCase;
    private final AtualizarCorUseCase atualizarCorUseCase;
    private final DeletarCorUseCase deletarCorUseCase;

    public CorController(CriarCorUseCase criarCorUseCase,
                        BuscarCorPorIdUseCase buscarCorPorIdUseCase,
                        ListarCoresUseCase listarCoresUseCase,
                        AtualizarCorUseCase atualizarCorUseCase,
                        DeletarCorUseCase deletarCorUseCase) {
        this.criarCorUseCase = criarCorUseCase;
        this.buscarCorPorIdUseCase = buscarCorPorIdUseCase;
        this.listarCoresUseCase = listarCoresUseCase;
        this.atualizarCorUseCase = atualizarCorUseCase;
        this.deletarCorUseCase = deletarCorUseCase;
    }

    @Operation(summary = "Listar todas as cores")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cores encontradas com sucesso") })
    @GetMapping
    public List<Cor> listar() {
        return listarCoresUseCase.execute();
    }

    @Operation(summary = "Buscar cor por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cor encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cor não encontrada")
    })
    @GetMapping("/{id}")
    public Cor buscarPorId(@PathVariable Integer id) {
        return buscarCorPorIdUseCase.execute(id);
    }

    @Operation(summary = "Cadastrar nova cor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cor cadastrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos para cadastro"),
        @ApiResponse(responseCode = "409", description = "Cor já existente")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cor criar(@RequestBody Cor cor) {
        return criarCorUseCase.execute(cor);
    }

    @Operation(summary = "Atualizar cor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cor atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cor não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização")
    })
    @PutMapping("/{id}")
    public Cor atualizar(@PathVariable Integer id, @RequestBody String nome) {
        return atualizarCorUseCase.execute(id, nome);
    }

    @Operation(summary = "Excluir cor por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cor excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cor não encontrada para exclusão")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id) {
        deletarCorUseCase.execute(id);
    }
}
