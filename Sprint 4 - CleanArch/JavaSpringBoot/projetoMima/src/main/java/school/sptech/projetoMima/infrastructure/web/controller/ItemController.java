package school.sptech.projetoMima.infrastructure.web.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.projetoMima.core.application.command.Item.*;
import school.sptech.projetoMima.core.application.usecase.Item.*;
import school.sptech.projetoMima.core.application.dto.itemDto.*;
import school.sptech.projetoMima.core.application.usecase.Item.auxiliares.AdicionarEstoqueItemUseCase;
import school.sptech.projetoMima.core.domain.item.Item;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/itens")
@Tag(name = "Item", description = "Operações relacionadas aos itens")
public class ItemController {

    private final CadastrarItemUseCase cadastrarItemUseCase;
    private final AtualizarItemUseCase atualizarItemUseCase;
    private final ExcluirItemUseCase excluirItemUseCase;
    private final DeletarItemPorCodigoUseCase deletarItemPorCodigoUseCase;
    private final BuscarItemPorIdUseCase buscarItemPorIdUseCase;
    private final BuscarItemPorCodigoUseCase buscarItemPorCodigoUseCase;

    private final ListarItensUseCase listarItensUseCase;
    private final ListarEstoqueUseCase listarEstoqueUseCase;
    private final FiltrarItemPorCategoriaUseCase filtrarItemPorCategoriaUseCase;
    private final FiltrarItemPorFornecedorUseCase filtrarItemPorFornecedorUseCase;
    private final FiltrarItemPorNomeUseCase filtrarItemPorNomeUseCase;
    private final AdicionarEstoqueItemUseCase adicionarEstoqueItemUseCase;

    public ItemController(CadastrarItemUseCase cadastrarItemUseCase,
                         AtualizarItemUseCase atualizarItemUseCase,
                         ExcluirItemUseCase excluirItemUseCase,
                         DeletarItemPorCodigoUseCase deletarItemPorCodigoUseCase,
                         BuscarItemPorIdUseCase buscarItemPorIdUseCase,
                         BuscarItemPorCodigoUseCase buscarItemPorCodigoUseCase,
                         ListarItensUseCase listarItensUseCase,
                         ListarEstoqueUseCase listarEstoqueUseCase,
                         FiltrarItemPorCategoriaUseCase filtrarItemPorCategoriaUseCase,
                         FiltrarItemPorFornecedorUseCase filtrarItemPorFornecedorUseCase,
                         FiltrarItemPorNomeUseCase filtrarItemPorNomeUseCase,
                         AdicionarEstoqueItemUseCase adicionarEstoqueItemUseCase) {
        this.cadastrarItemUseCase = cadastrarItemUseCase;
        this.atualizarItemUseCase = atualizarItemUseCase;
        this.excluirItemUseCase = excluirItemUseCase;
        this.deletarItemPorCodigoUseCase = deletarItemPorCodigoUseCase;
        this.buscarItemPorIdUseCase = buscarItemPorIdUseCase;
        this.buscarItemPorCodigoUseCase = buscarItemPorCodigoUseCase;

        this.listarItensUseCase = listarItensUseCase;
        this.listarEstoqueUseCase = listarEstoqueUseCase;
        this.filtrarItemPorCategoriaUseCase = filtrarItemPorCategoriaUseCase;
        this.filtrarItemPorFornecedorUseCase = filtrarItemPorFornecedorUseCase;
        this.filtrarItemPorNomeUseCase = filtrarItemPorNomeUseCase;
        this.adicionarEstoqueItemUseCase = adicionarEstoqueItemUseCase;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar item por ID")
    public ResponseEntity<ItemResponseDto> buscarPorId(@PathVariable Integer id) {
        BuscarItemPorIdCommand command = new BuscarItemPorIdCommand(id);
        Item item = buscarItemPorIdUseCase.execute(command);
        return ResponseEntity.ok(ItemMapper.toResponseDto(item));
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Buscar item por código")
    public ResponseEntity<ItemResponseDto> buscarPorCodigo(@PathVariable String codigo) {
        BuscarItemPorCodigoCommand command = new BuscarItemPorCodigoCommand(codigo);
        Item item = buscarItemPorCodigoUseCase.execute(command);
        return ResponseEntity.ok(ItemMapper.toResponseDto(item));
    }

    @GetMapping
    @Operation(summary = "Listar todos os itens")
    public ResponseEntity<Page<ItemListDto>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Item> itens = listarItensUseCase.execute(pageable);
        Page<ItemListDto> response = itens.map(ItemMapper::toListDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estoque")
    @Operation(summary = "Listar estoque de itens")
    public ResponseEntity<List<ItemListDto>> listarEstoque() {
        List<Item> itens = listarEstoqueUseCase.execute();
        List<ItemListDto> response = new ArrayList<>();
        for (Item item : itens) {
            response.add(ItemMapper.toListDto(item));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/categoria")
    @Operation(summary = "Filtrar itens por categoria")
    public ResponseEntity<List<ItemListDto>> filtrarPorCategoria(@RequestParam String nomeCategoria) {
        FiltrarItemPorCategoriaCommand command = new FiltrarItemPorCategoriaCommand(nomeCategoria);
        List<Item> itens = filtrarItemPorCategoriaUseCase.execute(command);
        List<ItemListDto> response = new ArrayList<>();
        for (Item item : itens) {
            response.add(ItemMapper.toListDto(item));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/fornecedor")
    @Operation(summary = "Filtrar itens por fornecedor")
    public ResponseEntity<List<ItemListDto>> filtrarPorFornecedor(@RequestParam String nomeFornecedor) {
        FiltrarItemPorFornecedorCommand command = new FiltrarItemPorFornecedorCommand(nomeFornecedor);
        List<Item> itens = filtrarItemPorFornecedorUseCase.execute(command);
        List<ItemListDto> response = new ArrayList<>();
        for (Item item : itens) {
            response.add(ItemMapper.toListDto(item));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/nome")
    @Operation(summary = "Filtrar itens por nome")
    public ResponseEntity<List<ItemListDto>> filtrarPorNome(@RequestParam String nome) {
        FiltrarItemPorNomeCommand command = new FiltrarItemPorNomeCommand(nome);
        List<Item> itens = filtrarItemPorNomeUseCase.execute(command);
        List<ItemListDto> response = new ArrayList<>();
        for (Item item : itens) {
            response.add(ItemMapper.toListDto(item));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo item")
    public ResponseEntity<ItemResponseDto> cadastrar(@Valid @RequestBody ItemRequestDto requestDto) {
        CadastrarItemCommand command = new CadastrarItemCommand(
                requestDto.getNome(),
                requestDto.getQtdEstoque(),
                requestDto.getPreco(),
                requestDto.getIdCategoria(),
                requestDto.getIdTamanho(),
                requestDto.getIdCor(),
                requestDto.getIdMaterial(),
                requestDto.getIdFornecedor()
        );

        Item item = cadastrarItemUseCase.execute(command);
        return ResponseEntity.status(201).body(ItemMapper.toResponseDto(item));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar item")
    public ResponseEntity<ItemResponseDto> atualizar(@PathVariable Integer id, @RequestBody ItemRequestDto requestDto) {
        AtualizarItemCommand command = new AtualizarItemCommand(
                id,
                requestDto.getNome(),
                requestDto.getQtdEstoque(),
                requestDto.getPreco(),
                requestDto.getIdCategoria(),
                requestDto.getIdTamanho(),
                requestDto.getIdCor(),
                requestDto.getIdMaterial(),
                requestDto.getIdFornecedor()
        );

        Item item = atualizarItemUseCase.execute(command);
        return ResponseEntity.ok(ItemMapper.toResponseDto(item));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir item por ID")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        ExcluirItemCommand command = new ExcluirItemCommand(id);
        excluirItemUseCase.execute(command);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/codigo/{codigo}")
    @Operation(summary = "Excluir item por código")
    public ResponseEntity<Void> excluirPorCodigo(@PathVariable String codigo) {
        DeletarItemPorCodigoCommand command = new DeletarItemPorCodigoCommand(codigo);
        deletarItemPorCodigoUseCase.execute(command);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/adicionar-estoque")
    @Operation(summary = "Adicionar quantidade ao estoque do item")
    public ResponseEntity<ItemResponseDto> adicionarEstoque(@PathVariable Integer id, @RequestParam Integer quantidade) {
        AdicionarEstoqueItemCommand command = new AdicionarEstoqueItemCommand(id, quantidade);
        Item item = adicionarEstoqueItemUseCase.execute(command);
        return ResponseEntity.ok(ItemMapper.toResponseDto(item));
    }

}
