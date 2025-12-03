package school.sptech.projetoMima.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.projetoMima.dto.fornecedorDto.FornecedorMapper;
import school.sptech.projetoMima.dto.fornecedorDto.FornecedorRequestDto;
import school.sptech.projetoMima.dto.fornecedorDto.FornecedorResponseDto;
import school.sptech.projetoMima.entity.Fornecedor;
import school.sptech.projetoMima.service.FornecedorService;
import school.sptech.projetoMima.service.ItemService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private ItemService itemService;

    @Operation(summary = "Listar todos os fornecedores")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Fornecedores encontrados com sucesso"), @ApiResponse(responseCode = "404", description = "Nenhum fornecedor encontrado na base de dados") })
    @GetMapping
    public ResponseEntity<List<FornecedorResponseDto>> listar() {
        List<Fornecedor> fornecedoresEncontrados = fornecedorService.listar();
        if (fornecedoresEncontrados.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        List<FornecedorResponseDto> response = fornecedoresEncontrados.stream().map(FornecedorMapper::toResponse).toList();
        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "Buscar fornecedor por ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Fornecedor encontrado com sucesso"), @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado com o ID informado") })
    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponseDto> buscar(@PathVariable int id) {
        Optional<Fornecedor> fornecedor = fornecedorService.findById(id);
        if (fornecedor.isPresent()) {
            Fornecedor fornecedorNovo = fornecedor.get();
            return ResponseEntity.ok(FornecedorMapper.toResponse(fornecedorNovo));
        }
        return ResponseEntity.status(404).body(null);
    }

    @Operation(summary = "Cadastrar novo fornecedor")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Fornecedor cadastrado com sucesso"), @ApiResponse(responseCode = "409", description = "Conflito: Fornecedor com este CNPJ já existe") })
    @PostMapping
    public ResponseEntity<FornecedorResponseDto> cadastrar(@Valid @RequestBody FornecedorRequestDto request) {
        Fornecedor fornecedor = FornecedorMapper.toEntity(request);
        Fornecedor fornecedorCadastrado = fornecedorService.cadastrar(fornecedor);
        return ResponseEntity.status(201).body(FornecedorMapper.toResponse(fornecedorCadastrado));
    }

    @Operation(summary = "Excluir fornecedor")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Fornecedor excluído com sucesso"), @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado para exclusão") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable int id) {
        fornecedorService.deletar(id);
        return ResponseEntity.status(204).build();
    }
}
