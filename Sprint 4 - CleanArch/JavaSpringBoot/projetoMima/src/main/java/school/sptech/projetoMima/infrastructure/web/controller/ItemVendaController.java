package school.sptech.projetoMima.infrastructure.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import school.sptech.projetoMima.core.application.dto.itemVendaDto.ItemVendaRequestDto;
import school.sptech.projetoMima.core.application.dto.itemVendaDto.ItemVendaResponseDto;
import school.sptech.projetoMima.core.application.dto.itemVendaDto.ItemVendaMapper;
import school.sptech.projetoMima.core.application.dto.vendaDto.VendaResponseDto;
import school.sptech.projetoMima.core.application.dto.vendaDto.VendaMapper;
import school.sptech.projetoMima.core.application.command.ItemVenda.AdicionarItemAoCarrinhoCommand;
import school.sptech.projetoMima.core.application.command.ItemVenda.ListarCarrinhoCommand;
import school.sptech.projetoMima.core.application.command.ItemVenda.FinalizarCarrinhoCommand;
import school.sptech.projetoMima.core.application.command.ItemVenda.RemoverUltimoItemDoCarrinhoCommand;
import school.sptech.projetoMima.core.application.usecase.ItemVenda.*;
import school.sptech.projetoMima.core.domain.ItemVenda;
import school.sptech.projetoMima.core.domain.Venda;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/item-venda")
public class ItemVendaController {

    private final AdicionarItemAoCarrinhoUseCase adicionarItemAoCarrinhoUseCase;
    private final ListarCarrinhoUseCase listarCarrinhoUseCase;
    private final FinalizarCarrinhoUseCase finalizarCarrinhoUseCase;
    private final RemoverUltimoItemDoCarrinhoUseCase removerUltimoItemDoCarrinhoUseCase;

    @Autowired
    public ItemVendaController(AdicionarItemAoCarrinhoUseCase adicionarItemAoCarrinhoUseCase,
                              ListarCarrinhoUseCase listarCarrinhoUseCase,
                              FinalizarCarrinhoUseCase finalizarCarrinhoUseCase,
                              RemoverUltimoItemDoCarrinhoUseCase removerUltimoItemDoCarrinhoUseCase) {
        this.adicionarItemAoCarrinhoUseCase = adicionarItemAoCarrinhoUseCase;
        this.listarCarrinhoUseCase = listarCarrinhoUseCase;
        this.finalizarCarrinhoUseCase = finalizarCarrinhoUseCase;
        this.removerUltimoItemDoCarrinhoUseCase = removerUltimoItemDoCarrinhoUseCase;
    }

    @Operation(summary = "Adicionar item ao carrinho")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Item adicionado ao carrinho com sucesso",
                    content = @Content(schema = @Schema(implementation = ItemVendaResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Item, cliente ou funcionário não encontrado")
    })
    @Transactional
    @PostMapping("/carrinho")
    public ResponseEntity<ItemVendaResponseDto> adicionarAoCarrinho(@RequestBody @Valid ItemVendaRequestDto dto) {
        AdicionarItemAoCarrinhoCommand command = new AdicionarItemAoCarrinhoCommand(
            dto.getItemId(),
            dto.getClienteId(),
            dto.getFuncionarioId(),
            dto.getQtdParaVender()
        );

        ItemVenda itemVenda = adicionarItemAoCarrinhoUseCase.execute(command);
        ItemVendaResponseDto response = ItemVendaMapper.toResponseDto(itemVenda);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar itens do carrinho por cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carrinho listado com sucesso"),
        @ApiResponse(responseCode = "204", description = "Carrinho vazio")
    })
    @GetMapping("/carrinho/{clienteId}")
    public ResponseEntity<List<ItemVendaResponseDto>> listarCarrinho(@PathVariable Integer clienteId) {

        ListarCarrinhoCommand command = new ListarCarrinhoCommand(clienteId);

        List<ItemVenda> carrinho = listarCarrinhoUseCase.execute(command);
        List<ItemVendaResponseDto> response = carrinho.stream()
                .map(ItemVendaMapper::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Finalizar carrinho associando os itens a uma venda")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carrinho finalizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Carrinho vazio"),
        @ApiResponse(responseCode = "404", description = "Venda não encontrada")
    })
    @Transactional
    @PostMapping("/carrinho/finalizar/{clienteId}/{funcionarioId}")
    public ResponseEntity<VendaResponseDto> finalizarCarrinho(@PathVariable Integer clienteId, @PathVariable Integer funcionarioId){
        FinalizarCarrinhoCommand command = new FinalizarCarrinhoCommand(clienteId, funcionarioId);
        Venda novaVenda = finalizarCarrinhoUseCase.execute(command);
        VendaResponseDto responseDto = VendaMapper.toResponseDto(novaVenda);

        URI location = URI.create("/vendas/" + novaVenda.getId());
        return ResponseEntity.created(location).body(responseDto);
    }

    @Operation(summary = "Remover o último item adicionado do carrinho")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Último item removido do carrinho com sucesso"),
        @ApiResponse(responseCode = "404", description = "Carrinho vazio ou item não encontrado")
    })
    @DeleteMapping("/carrinho/remover-ultimo/{clienteId}")
    public ResponseEntity<String> removerUltimoItem(@PathVariable Integer clienteId) {

        RemoverUltimoItemDoCarrinhoCommand command = new RemoverUltimoItemDoCarrinhoCommand(clienteId);

        try {
            removerUltimoItemDoCarrinhoUseCase.execute(command);
            return ResponseEntity.ok("Último item removido do carrinho com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrinho vazio ou item não encontrado");
        }
    }
}
