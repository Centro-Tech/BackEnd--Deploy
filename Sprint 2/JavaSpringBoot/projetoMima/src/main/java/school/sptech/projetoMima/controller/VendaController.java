package school.sptech.projetoMima.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.projetoMima.dto.itemDto.ItemVendaRequestDto;
import school.sptech.projetoMima.dto.vendaDto.VendaMapper;
import school.sptech.projetoMima.dto.vendaDto.VendaRequestDto;
import school.sptech.projetoMima.dto.vendaDto.VendaResponseDto;
import school.sptech.projetoMima.entity.Cliente;
import school.sptech.projetoMima.entity.ItemVenda;
import school.sptech.projetoMima.entity.Venda;
import school.sptech.projetoMima.entity.item.Item;
import school.sptech.projetoMima.repository.ItemRepository;
import school.sptech.projetoMima.repository.ItemVendaRepository;
import school.sptech.projetoMima.repository.VendaRepository;
import school.sptech.projetoMima.service.VendaService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @Operation(summary = "Registrar uma nova venda")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Venda registrada com sucesso", content = @Content(schema = @Schema(implementation = VendaResponseDto.class))), @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    @PutMapping("/vender")
    public ResponseEntity<VendaResponseDto> vender(@Valid @RequestBody VendaRequestDto request) {
        VendaResponseDto response = vendaService.vender(request);
        return ResponseEntity.ok(response);
    }

 /*   @Operation(summary = "Adicionar item a uma venda existente")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Item adicionado com sucesso", content = @Content(schema = @Schema(implementation = VendaResponseDto.class))), @ApiResponse(responseCode = "400", description = "Dados inválidos") })
    @PutMapping("/adicionar-item")
    public ResponseEntity<VendaResponseDto> adicionarItens(@Valid @RequestBody ItemVendaRequestDto request) {
        Venda venda = vendaService.adicionarItem(request);
        return ResponseEntity.status(200).body(VendaMapper.toResponse(venda));
    }*/


    @Operation(summary = "Remover item de uma venda")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Item removido com sucesso"), @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    @DeleteMapping
    public ResponseEntity<Void> removerItemDaVenda(@Valid @RequestBody ItemVendaRequestDto request) {
        Integer venda = request.getItemId();
        Integer itemVenda = request.getVendaId();

        vendaService.deletarItemDaVenda(itemVenda, venda);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Filtrar vendas por intervalo de datas")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Vendas encontradas", content = @Content(schema = @Schema(implementation = Venda.class))), @ApiResponse(responseCode = "204", description = "Nenhuma venda encontrada no intervalo")})
    @GetMapping("/filtrar-por-data")
    public ResponseEntity<List<Venda>> filtrarPorData(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        List<Venda> vendasEncontradas = vendaService.filtrarPorDatas(inicio, fim);
        if (vendasEncontradas.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(vendasEncontradas);
    }

    @Operation(summary = "Filtrar vendas por cliente")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Vendas encontradas", content = @Content(schema = @Schema(implementation = Venda.class))), @ApiResponse(responseCode = "204", description = "Nenhuma venda encontrada para o cliente")})
    @GetMapping("/filtrar-por-cliente")
    public ResponseEntity<List<Venda>> filtrarPorCliente(@RequestParam Cliente cliente) {
        List<Venda> vendasEncontradas = vendaService.filtrarPorCliente(cliente);
        if (vendasEncontradas.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(vendasEncontradas);
    }

    @Operation(summary = "Filtrar vendas por faixa de valor")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Vendas encontradas", content = @Content(schema = @Schema(implementation = Venda.class))), @ApiResponse(responseCode = "204", description = "Nenhuma venda encontrada na faixa de valor")})
    @GetMapping("/filtrar-por-valor")
    public ResponseEntity<List<Venda>> filtrarPorValor(@RequestParam Double valorMinimo, @RequestParam Double valorMax) {
        List<Venda> vendasEncontradas = vendaService.filtrarPorValor(valorMinimo, valorMax);
        if (vendasEncontradas.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(vendasEncontradas);
    }

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    @DeleteMapping("/{idVenda}/itens/{idItemVenda}")
    public ResponseEntity<?> removerItemDaVendaComDto(
            @PathVariable Integer idVenda,
            @PathVariable Integer idItemVenda) {

        System.out.println("Tentando buscar ItemVenda com id = " + idItemVenda + " e venda id = " + idVenda);

        Optional<ItemVenda> itemVendaOpt = itemVendaRepository.buscarPorIdEVenda(idItemVenda, idVenda);

        System.out.println("ItemVenda encontrado: " + (itemVendaOpt.isPresent() ? "SIM" : "NÃO"));

        Optional<Venda> vendaOpt = vendaRepository.findById(idVenda);
        if (vendaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venda não encontrada.");
        }

        if (itemVendaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Este item não pertence à venda informada.");
        }

        Venda venda = vendaOpt.get();
        ItemVenda itemVenda = itemVendaOpt.get();
        Item item = itemVenda.getItem();

        Double valorItem = item.getPreco() * itemVenda.getQtdParaVender();
        venda.setValorTotal(venda.getValorTotal() - valorItem);
        item.setQtdEstoque(item.getQtdEstoque() + itemVenda.getQtdParaVender());
        venda.getItensVenda().removeIf(i -> i.getId().equals(itemVenda.getId()));

        itemRepository.save(item);
        vendaRepository.save(venda);
        itemVendaRepository.delete(itemVenda);

        VendaResponseDto dto = VendaMapper.toResponse(venda);
        return ResponseEntity.ok(dto);
    }

}
