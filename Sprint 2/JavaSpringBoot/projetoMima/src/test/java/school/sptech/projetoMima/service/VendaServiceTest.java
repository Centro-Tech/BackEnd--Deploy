package school.sptech.projetoMima.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.projetoMima.dto.vendaDto.VendaRequestDto;
import school.sptech.projetoMima.entity.Cliente;
import school.sptech.projetoMima.entity.ItemVenda;
import school.sptech.projetoMima.entity.Venda;
import school.sptech.projetoMima.entity.item.Item;
import school.sptech.projetoMima.exception.Usuario.UsuarioNaoEncontradoException;
import school.sptech.projetoMima.exception.Venda.CarrinhoVazioException;
import school.sptech.projetoMima.exception.Venda.EstoqueInsuficienteException;
import school.sptech.projetoMima.repository.ClienteRepository;
import school.sptech.projetoMima.repository.ItemRepository;
import school.sptech.projetoMima.repository.ItemVendaRepository;
import school.sptech.projetoMima.repository.VendaRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VendaServiceTest {

    @InjectMocks
    private VendaService vendaService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ItemVendaRepository itemVendaRepository;

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private ItemRepository itemRepository;

    @Test
    @DisplayName("deletarItemDaVenda() deve remover item e atualizar venda com sucesso")
    void deletarItemDaVenda_sucesso() {
        Integer vendaId = 1;
        Integer itemVendaId = 10;

        Item item = new Item();
        item.setPreco(50.0);

        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setId(itemVendaId);
        itemVenda.setItem(item);
        itemVenda.setQtdParaVender(2);

        Venda venda = new Venda();
        venda.setId(vendaId);
        venda.setValorTotal(200.0);
        venda.setItensVenda(new ArrayList<>(List.of(itemVenda)));

        Mockito.when(vendaRepository.findById(vendaId)).thenReturn(Optional.of(venda));
        Mockito.when(vendaRepository.save(Mockito.any(Venda.class))).thenReturn(venda);

        vendaService.deletarItemDaVenda(itemVendaId, vendaId);

        Mockito.verify(vendaRepository).findById(vendaId);
        Mockito.verify(vendaRepository).save(Mockito.argThat(v ->
                v.getItensVenda().isEmpty() &&
                        Math.abs(v.getValorTotal() - 100.0) < 0.001
        ));
    }

    @Test
    @DisplayName("deletarItemDaVenda() quando venda não existir deve lançar IllegalArgumentException")
    void deletarItemDaVenda_vendaNaoEncontrada() {
        Integer vendaId = 1;
        Integer itemVendaId = 10;

        Mockito.when(vendaRepository.findById(vendaId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            vendaService.deletarItemDaVenda(itemVendaId, vendaId);
        });

        assertEquals("Venda não encontrada com ID: " + vendaId, exception.getMessage());
        Mockito.verify(vendaRepository).findById(vendaId);
        Mockito.verifyNoMoreInteractions(vendaRepository);
    }

    @Test
    @DisplayName("deletarItemDaVenda() quando itemVenda não existir na venda deve lançar IllegalArgumentException")
    void deletarItemDaVenda_itemNaoEncontrado() {
        Integer vendaId = 1;
        Integer itemVendaId = 10;

        Venda venda = new Venda();
        venda.setId(vendaId);
        venda.setItensVenda(new ArrayList<>()); // lista vazia

        Mockito.when(vendaRepository.findById(vendaId)).thenReturn(Optional.of(venda));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            vendaService.deletarItemDaVenda(itemVendaId, vendaId);
        });

        assertEquals("ItemVenda com ID " + itemVendaId + " não encontrado na venda.", exception.getMessage());
        Mockito.verify(vendaRepository).findById(vendaId);
        Mockito.verifyNoMoreInteractions(vendaRepository);
    }

    @Test
    @DisplayName("vender() deve salvar venda e atualizar estoque com sucesso")
    void vender_sucesso() {
        VendaRequestDto dto = new VendaRequestDto();
        dto.setCliente(1);

        Cliente cliente = new Cliente();
        cliente.setId(1);

        Item item = new Item();
        item.setPreco(50.0);
        item.setQtdEstoque(10);
        item.setNome("Produto A");

        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setItem(item);
        itemVenda.setQtdParaVender(2);

        List<ItemVenda> itensCarrinho = List.of(itemVenda);

        Venda vendaSalva = new Venda();
        vendaSalva.setId(100);

        Mockito.when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        Mockito.when(itemVendaRepository.findByClienteIdAndVendaIsNull(cliente.getId())).thenReturn(itensCarrinho);
        Mockito.when(vendaRepository.save(Mockito.any(Venda.class))).thenReturn(vendaSalva);
        Mockito.when(itemRepository.save(Mockito.any(Item.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(itemVendaRepository.save(Mockito.any(ItemVenda.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Venda venda = vendaService.vender(dto);

        assertNotNull(venda);
        assertEquals(cliente, venda.getCliente());
        assertEquals(itensCarrinho, venda.getItensVenda());
        assertEquals(100.0, venda.getValorTotal());

        Mockito.verify(clienteRepository).findById(1);
        Mockito.verify(itemVendaRepository).findByClienteIdAndVendaIsNull(cliente.getId());
        Mockito.verify(vendaRepository).save(Mockito.any(Venda.class));
        Mockito.verify(itemRepository).save(Mockito.argThat(i -> i.getQtdEstoque() == 8));
        Mockito.verify(itemVendaRepository, Mockito.times(itensCarrinho.size())).save(Mockito.any(ItemVenda.class));
    }

    @Test
    @DisplayName("vender() deve lançar UsuarioNaoEncontradoException quando cliente não existir")
    void vender_clienteNaoEncontrado() {
        VendaRequestDto dto = new VendaRequestDto();
        dto.setCliente(99);

        Mockito.when(clienteRepository.findById(99)).thenReturn(Optional.empty());

        UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> {
            vendaService.vender(dto);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
        Mockito.verify(clienteRepository).findById(99);
        Mockito.verifyNoMoreInteractions(itemVendaRepository, vendaRepository, itemRepository);
    }

    @Test
    @DisplayName("vender() deve lançar CarrinhoVazioException quando não houver itens no carrinho")
    void vender_carrinhoVazio() {
        VendaRequestDto dto = new VendaRequestDto();
        dto.setCliente(1);

        Cliente cliente = new Cliente();
        cliente.setId(1);

        Mockito.when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        Mockito.when(itemVendaRepository.findByClienteIdAndVendaIsNull(cliente.getId())).thenReturn(Collections.emptyList());

        CarrinhoVazioException exception = assertThrows(CarrinhoVazioException.class, () -> {
            vendaService.vender(dto);
        });

        assertEquals("Carrinho está vazio", exception.getMessage());
        Mockito.verify(clienteRepository).findById(1);
        Mockito.verify(itemVendaRepository).findByClienteIdAndVendaIsNull(cliente.getId());
        Mockito.verifyNoMoreInteractions(vendaRepository, itemRepository);
    }

    @Test
    @DisplayName("vender() deve lançar EstoqueInsuficienteException quando estoque for insuficiente")
    void vender_estoqueInsuficiente() {
        VendaRequestDto dto = new VendaRequestDto();
        dto.setCliente(1);

        Cliente cliente = new Cliente();
        cliente.setId(1);

        Item item = new Item();
        item.setPreco(50.0);
        item.setQtdEstoque(1);
        item.setNome("Produto A");

        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setItem(item);
        itemVenda.setQtdParaVender(2); // maior que estoque

        List<ItemVenda> itensCarrinho = List.of(itemVenda);

        Mockito.when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        Mockito.when(itemVendaRepository.findByClienteIdAndVendaIsNull(cliente.getId())).thenReturn(itensCarrinho);

        EstoqueInsuficienteException exception = assertThrows(EstoqueInsuficienteException.class, () -> {
            vendaService.vender(dto);
        });

        assertEquals("Estoque insuficiente para: Produto A", exception.getMessage());
        Mockito.verify(clienteRepository).findById(1);
        Mockito.verify(itemVendaRepository).findByClienteIdAndVendaIsNull(cliente.getId());
    }


}