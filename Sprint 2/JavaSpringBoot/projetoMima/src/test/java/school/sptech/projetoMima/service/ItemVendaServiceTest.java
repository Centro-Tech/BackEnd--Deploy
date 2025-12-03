package school.sptech.projetoMima.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.projetoMima.dto.itemDto.ItemVendaRequestDto;
import school.sptech.projetoMima.entity.Cliente;
import school.sptech.projetoMima.entity.Fornecedor;
import school.sptech.projetoMima.entity.ItemVenda;
import school.sptech.projetoMima.entity.Usuario;
import school.sptech.projetoMima.entity.item.Item;
import school.sptech.projetoMima.exception.Venda.CarrinhoVazioException;
import school.sptech.projetoMima.repository.ClienteRepository;
import school.sptech.projetoMima.repository.ItemRepository;
import school.sptech.projetoMima.repository.ItemVendaRepository;
import school.sptech.projetoMima.repository.UsuarioRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemVendaServiceTest {

    @InjectMocks
    private ItemVendaService itemVendaService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ItemVendaRepository itemVendaRepository;

    @Test
    @DisplayName("adicionarAoCarrinho() com dados válidos deve salvar e retornar ItemVenda")
    void adicionarAoCarrinho_comSucesso() {
        Item item = new Item();
        item.setId(1);
        Fornecedor fornecedor = new Fornecedor();
        item.setFornecedor(fornecedor);

        Cliente cliente = new Cliente();
        cliente.setId(2);

        Usuario funcionario = new Usuario();
        funcionario.setId(3);

        ItemVendaRequestDto dto = new ItemVendaRequestDto();
        dto.setItemId(1);
        dto.setClienteId(2);
        dto.setFuncionarioId(3);
        dto.setQtdParaVender(5);

        ItemVenda itemVendaSalvo = new ItemVenda(); // simulando retorno do save
        Mockito.when(itemRepository.findById(1)).thenReturn(Optional.of(item));
        Mockito.when(clienteRepository.findById(2)).thenReturn(Optional.of(cliente));
        Mockito.when(usuarioRepository.findById(3)).thenReturn(Optional.of(funcionario));
        Mockito.when(itemVendaRepository.save(Mockito.any(ItemVenda.class))).thenReturn(itemVendaSalvo);

        ItemVenda resultado = itemVendaService.adicionarAoCarrinho(dto);

        assertNotNull(resultado);
        Mockito.verify(itemRepository).findById(1);
        Mockito.verify(clienteRepository).findById(2);
        Mockito.verify(usuarioRepository).findById(3);
        Mockito.verify(itemVendaRepository).save(Mockito.any(ItemVenda.class));
    }

    @Test
    @DisplayName("adicionarAoCarrinho() com item inexistente deve lançar exceção")
    void adicionarAoCarrinho_itemNaoEncontrado() {
        ItemVendaRequestDto dto = new ItemVendaRequestDto();
        dto.setItemId(99);
        dto.setClienteId(2);
        dto.setFuncionarioId(3);
        dto.setQtdParaVender(5);

        Mockito.when(itemRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            itemVendaService.adicionarAoCarrinho(dto);
        });

        assertEquals("Item não encontrado", exception.getMessage());
        Mockito.verify(itemRepository).findById(99);
        Mockito.verifyNoMoreInteractions(clienteRepository, usuarioRepository, itemVendaRepository);
    }

    @Test
    @DisplayName("listarCarrinho() com cliente que tem itens deve retornar lista")
    void listarCarrinho_comItens() {
        Integer clienteId = 1;
        List<ItemVenda> carrinho = List.of(new ItemVenda(), new ItemVenda());

        Mockito.when(itemVendaRepository.findByClienteIdAndVendaIsNull(clienteId)).thenReturn(carrinho);

        List<ItemVenda> resultado = itemVendaService.listarCarrinho(clienteId);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        Mockito.verify(itemVendaRepository).findByClienteIdAndVendaIsNull(clienteId);
    }

    @Test
    @DisplayName("listarCarrinho() com cliente sem itens deve lançar CarrinhoVazioException")
    void listarCarrinho_carrinhoVazio() {
        Integer clienteId = 2;

        Mockito.when(itemVendaRepository.findByClienteIdAndVendaIsNull(clienteId))
                .thenReturn(Collections.emptyList());

        CarrinhoVazioException exception = assertThrows(CarrinhoVazioException.class, () -> {
            itemVendaService.listarCarrinho(clienteId);
        });

        assertEquals("O carrinho está vazio.", exception.getMessage());
        Mockito.verify(itemVendaRepository).findByClienteIdAndVendaIsNull(clienteId);
    }


}
