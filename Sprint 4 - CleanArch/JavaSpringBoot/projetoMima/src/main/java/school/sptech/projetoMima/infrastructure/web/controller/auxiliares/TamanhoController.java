package school.sptech.projetoMima.infrastructure.web.controller.auxiliares;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import school.sptech.projetoMima.core.application.usecase.Item.auxiliares.TamanhoUseCase.*;
import school.sptech.projetoMima.core.domain.item.Tamanho;

import java.util.List;

@RestController
@RequestMapping("/tamanhos")
@Tag(name = "Tamanho", description = "Operações relacionadas aos tamanhos")
public class TamanhoController {

    private final CriarTamanhoUseCase criarTamanhoUseCase;
    private final BuscarTamanhoPorIdUseCase buscarTamanhoPorIdUseCase;
    private final ListarTamanhosUseCase listarTamanhosUseCase;
    private final AtualizarTamanhoUseCase atualizarTamanhoUseCase;
    private final DeletarTamanhoUseCase deletarTamanhoUseCase;

    public TamanhoController(CriarTamanhoUseCase criarTamanhoUseCase,
                            BuscarTamanhoPorIdUseCase buscarTamanhoPorIdUseCase,
                            ListarTamanhosUseCase listarTamanhosUseCase,
                            AtualizarTamanhoUseCase atualizarTamanhoUseCase,
                            DeletarTamanhoUseCase deletarTamanhoUseCase) {
        this.criarTamanhoUseCase = criarTamanhoUseCase;
        this.buscarTamanhoPorIdUseCase = buscarTamanhoPorIdUseCase;
        this.listarTamanhosUseCase = listarTamanhosUseCase;
        this.atualizarTamanhoUseCase = atualizarTamanhoUseCase;
        this.deletarTamanhoUseCase = deletarTamanhoUseCase;
    }

    @Operation(summary = "Listar todos os tamanhos")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Tamanhos encontrados com sucesso") })
    @GetMapping
    public List<Tamanho> listar() {
        return listarTamanhosUseCase.execute();
    }

    @Operation(summary = "Buscar tamanho por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tamanho encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Tamanho não encontrado")
    })
    @GetMapping("/{id}")
    public Tamanho buscarPorId(@PathVariable Integer id) {
        return buscarTamanhoPorIdUseCase.execute(id);
    }

    @Operation(summary = "Cadastrar novo tamanho")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tamanho cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos para cadastro"),
        @ApiResponse(responseCode = "409", description = "Tamanho já existente")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tamanho criar(@RequestBody Tamanho tamanho) {
        return criarTamanhoUseCase.execute(tamanho);
    }

    @Operation(summary = "Atualizar tamanho")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tamanho atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Tamanho não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização")
    })
    @PutMapping("/{id}")
    public Tamanho atualizar(@PathVariable Integer id, @RequestBody String nome) {
        return atualizarTamanhoUseCase.execute(id, nome);
    }

    @Operation(summary = "Excluir tamanho por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Tamanho excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Tamanho não encontrado para exclusão")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id) {
        deletarTamanhoUseCase.execute(id);
    }
}