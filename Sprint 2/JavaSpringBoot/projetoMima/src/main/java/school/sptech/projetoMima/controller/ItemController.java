package school.sptech.projetoMima.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.projetoMima.dto.itemDto.ItemListDto;
import school.sptech.projetoMima.dto.itemDto.ItemMapper;
import school.sptech.projetoMima.dto.itemDto.ItemRequestDto;
import school.sptech.projetoMima.dto.itemDto.ItemResponseDto;
import school.sptech.projetoMima.entity.Fornecedor;
import school.sptech.projetoMima.entity.item.Item;
import school.sptech.projetoMima.exception.Item.ItemNaoEncontradoException;
import school.sptech.projetoMima.service.FornecedorService;
import school.sptech.projetoMima.service.ItemService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/itens")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private FornecedorService fornecedorService;

    @Operation(summary = "Buscar item por ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Item encontrado", content = @Content(schema = @Schema(implementation = ItemResponseDto.class))), @ApiResponse(responseCode = "404", description = "Item não encontrado")})
    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDto> buscarPorId(@PathVariable Integer id) {
        Item itemEncontrado = itemService.buscarPorId(id);
        ItemResponseDto itemResponse = ItemMapper.toResponse(itemEncontrado);
        return ResponseEntity.status(200).body(itemResponse);
    }

    @Operation(summary = "Buscar todos os itens em estoque")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista retornada com sucesso", content = @Content(schema = @Schema(implementation = Item.class))), @ApiResponse(responseCode = "204", description = "Nenhum item em estoque")})
    @GetMapping("/estoque")
    public ResponseEntity<List<ItemListDto>> listarEstoque() {
        List<Item> itens = itemService.listarEstoque();
        if (itens.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        List<ItemListDto> response = itens.stream().map(ItemMapper::toList).toList();
        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "Cadastrar novo item")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Item cadastrado com sucesso", content = @Content(schema = @Schema(implementation = Item.class))), @ApiResponse(responseCode = "400", description = "Dados inválidos ou código duplicado"), @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")})
    @PostMapping
    public ResponseEntity<ItemResponseDto> cadastrarItem(@Valid @RequestBody ItemRequestDto request) {
        System.out.println("Entrou no cadastrarItem");

        Item item = ItemMapper.toEntity(request);

        if (itemService.existsByCodigo(item.getCodigo())) {
            return ResponseEntity.status(400).body(null);
        }

        if (item.getFornecedor() == null || item.getFornecedor().getId() == null) {
            return ResponseEntity.status(400).body(null);
        }

        Optional<Fornecedor> fornecedorOpt = fornecedorService.findById(item.getFornecedor().getId());
        if (fornecedorOpt.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        String nome = item.getNome().toUpperCase();
        if (!itemService.isCategoriaValida(nome)) {
            return ResponseEntity.status(400).body(null);
        }

        Item novoItem = itemService.cadastrarItem(item, fornecedorOpt.get(), request);

        return ResponseEntity.status(201).body(ItemMapper.toResponse(novoItem));
    }

    @Operation(summary = "Deletar Item")
    @ApiResponses(value = {@ApiResponse(responseCode = "205", description = "Item deletado com sucesso"), @ApiResponse(responseCode = "404", description = "Item não encontrado")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVestuario(@PathVariable Integer id) {
        try {
            Item itemParaDeletar = itemService.buscarPorId(id);
            itemService.deletar(itemParaDeletar);
            return ResponseEntity.status(205).build();
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    @Operation(summary = "Deletar vestuário por código")
    @ApiResponses(value = {@ApiResponse(responseCode = "205", description = "Item deletado com sucesso"), @ApiResponse(responseCode = "404", description = "Item não encontrado")})
    @DeleteMapping("/item/{codigo}")
    public ResponseEntity<Void> deletarPorCodigo(@PathVariable String codigo) {
        try {
            itemService.deletarPorCodigo(codigo);
            return ResponseEntity.status(205).build();
        } catch (ItemNaoEncontradoException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @Operation(summary = "Filtrar itens por nome")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Itens encontrados", content = @Content(schema = @Schema(implementation = ItemListDto.class))), @ApiResponse(responseCode = "204", description = "Nenhum item encontrado")})
    @GetMapping("/filtro-por-nome/{nome}")
    public ResponseEntity<List<ItemListDto>> filtrarPorNome(@PathVariable String nome) {
        List<Item> itens = itemService.filtrarPorNome(nome);

        if (itens.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        List<ItemListDto> response = itens.stream().map(ItemMapper::toList).toList();
        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "Filtrar itens por fornecedor")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Itens encontrados", content = @Content(schema = @Schema(implementation = ItemListDto.class))), @ApiResponse(responseCode = "204", description = "Nenhum item encontrado")})
    @GetMapping("/filtro-por-fornecedor/{fornecedor}")
    public ResponseEntity<List<ItemListDto>> filtrarPorFornecedor(@PathVariable String fornecedor) {
        List<Item> itens = itemService.filtrarPorFornecedor(fornecedor);

        if (itens.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        List<ItemListDto> response = itens.stream().map(ItemMapper::toList).toList();
        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "Filtrar itens por categoria")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Itens encontrados", content = @Content(schema = @Schema(implementation = ItemListDto.class))), @ApiResponse(responseCode = "204", description = "Nenhum item encontrado")})
    @GetMapping("/filtro-por-categoria/{categoria}")
    public ResponseEntity<List<ItemListDto>> filtrarPorCategoria(@PathVariable String categoria) {
        List<Item> itens = itemService.filtrarPorCategoria(categoria);

        if (itens.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        List<ItemListDto> response = itens.stream().map(ItemMapper::toList).toList();
        return ResponseEntity.status(200).body(response);
    }
}
