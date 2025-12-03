package school.sptech.projetoMima.infrastructure.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.projetoMima.core.application.command.Fornecedor.CadastrarFornecedorCommand;
import school.sptech.projetoMima.core.application.dto.fornecedorDto.FornecedorMapper;
import school.sptech.projetoMima.core.application.dto.fornecedorDto.FornecedorRequestDto;
import school.sptech.projetoMima.core.application.dto.fornecedorDto.FornecedorResponseDto;
import school.sptech.projetoMima.core.application.usecase.Fornecedor.BuscarFornecedorPorIdUseCase;
import school.sptech.projetoMima.core.application.usecase.Fornecedor.CadastrarFornecedorUseCase;
import school.sptech.projetoMima.core.application.usecase.Fornecedor.DeletarFornecedorUseCase;
import school.sptech.projetoMima.core.application.usecase.Fornecedor.ListarFornecedoresUseCase;
import school.sptech.projetoMima.core.usecase.AtualizarFornecedorUseCase;
import school.sptech.projetoMima.core.domain.Fornecedor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fornecedores")
@Tag(name = "Fornecedor", description = "Operações relacionadas aos fornecedores")
public class FornecedorController {

    private final CadastrarFornecedorUseCase cadastrarFornecedorUseCase;
    private final DeletarFornecedorUseCase deletarFornecedorUseCase;
    private final ListarFornecedoresUseCase listarFornecedoresUseCase;
    private final BuscarFornecedorPorIdUseCase buscarFornecedorPorIdUseCase;
    private final AtualizarFornecedorUseCase atualizarFornecedorUseCase;

    public FornecedorController(CadastrarFornecedorUseCase cadastrarFornecedorUseCase, DeletarFornecedorUseCase deletarFornecedorUseCase, ListarFornecedoresUseCase listarFornecedoresUseCase, BuscarFornecedorPorIdUseCase buscarFornecedorPorIdUseCase, AtualizarFornecedorUseCase atualizarFornecedorUseCase) {
        this.cadastrarFornecedorUseCase = cadastrarFornecedorUseCase;
        this.deletarFornecedorUseCase = deletarFornecedorUseCase;
        this.listarFornecedoresUseCase = listarFornecedoresUseCase;
        this.buscarFornecedorPorIdUseCase = buscarFornecedorPorIdUseCase;
        this.atualizarFornecedorUseCase = atualizarFornecedorUseCase;
    }

    @Operation(summary = "Listar todos os fornecedores")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fornecedores listados com sucesso"),
        @ApiResponse(responseCode = "204", description = "Nenhum fornecedor encontrado")
    })
    @GetMapping
    public ResponseEntity<Page<FornecedorResponseDto>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Fornecedor> fornecedoresEncontrados = listarFornecedoresUseCase.execute(pageable);
        Page<FornecedorResponseDto> response = fornecedoresEncontrados.map(FornecedorMapper::toResponse);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar fornecedor por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fornecedor encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponseDto> buscarPorId(@PathVariable Integer id) {
        Optional<Fornecedor> fornecedor = Optional.ofNullable(buscarFornecedorPorIdUseCase.execute(id));
        if (fornecedor.isPresent()) {
            Fornecedor fornecedorNovo = fornecedor.get();
            return ResponseEntity.ok(FornecedorMapper.toResponse(fornecedorNovo));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Cadastrar novo fornecedor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Fornecedor cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos para cadastro"),
        @ApiResponse(responseCode = "409", description = "Fornecedor já existente")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FornecedorResponseDto> cadastrar(@Valid @RequestBody FornecedorRequestDto requestDto) {
        CadastrarFornecedorCommand command = new CadastrarFornecedorCommand(
                requestDto.getNome(),
                requestDto.getTelefone(),
                requestDto.getEmail()
        );

        Fornecedor fornecedorCadastrado = cadastrarFornecedorUseCase.execute(command);
        return ResponseEntity.status(201).body(FornecedorMapper.toResponse(fornecedorCadastrado));
    }

    @Operation(summary = "Excluir fornecedor por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Fornecedor excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado para exclusão")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        deletarFornecedorUseCase.execute(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Atualizar fornecedor por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fornecedor atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado para atualização"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Fornecedor> atualizarFornecedor(@PathVariable Integer id, @RequestBody Fornecedor fornecedor) {
        Fornecedor fornecedorAtualizado = atualizarFornecedorUseCase.atualizarFornecedor(id, fornecedor);
        return ResponseEntity.ok(fornecedorAtualizado);
    }
}
