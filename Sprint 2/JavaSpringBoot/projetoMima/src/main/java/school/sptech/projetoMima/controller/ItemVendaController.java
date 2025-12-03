package school.sptech.projetoMima.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.projetoMima.dto.itemDto.ItemVendaRequestDto;
import school.sptech.projetoMima.dto.itemVendaDto.ItemVendaMapper;
import school.sptech.projetoMima.dto.itemVendaDto.ItemVendaResponseDto;
import school.sptech.projetoMima.entity.ItemVenda;
import school.sptech.projetoMima.service.ItemVendaService;

import java.util.List;

@RestController
@RequestMapping("/item-venda")
public class ItemVendaController {

    @Autowired
    private ItemVendaService itemVendaService;

    @PostMapping("/carrinho")
    public ResponseEntity<ItemVendaResponseDto> adicionar(@RequestBody @Valid ItemVendaRequestDto dto) {
        ItemVenda item = itemVendaService.adicionarAoCarrinho(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ItemVendaMapper.toResponse(item));
    }

    @GetMapping("/carrinho/{clienteId}")
    public ResponseEntity<List<ItemVendaResponseDto>> listar(@PathVariable Integer clienteId) {
        List<ItemVenda> itens = itemVendaService.listarCarrinho(clienteId);
        List<ItemVendaResponseDto> response = itens.stream()
                .map(ItemVendaMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/carrinho/finalizar/{clienteId}/{vendaId}")
    public ResponseEntity<String> finalizarCarrinho(@PathVariable Integer clienteId,
                                                    @PathVariable Integer vendaId) {
        itemVendaService.finalizarCarrinho(clienteId, vendaId);
        return ResponseEntity.ok("Carrinho finalizado com sucesso!");
    }

}

