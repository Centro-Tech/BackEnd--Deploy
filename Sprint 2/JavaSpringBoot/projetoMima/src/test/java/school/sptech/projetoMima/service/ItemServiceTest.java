package school.sptech.projetoMima.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.projetoMima.entity.Fornecedor;
import school.sptech.projetoMima.entity.item.*;
import school.sptech.projetoMima.exception.Item.ItemCampoVazioException;
import school.sptech.projetoMima.exception.Item.ItemNaoEncontradoException;
import school.sptech.projetoMima.exception.Item.ItemQuantidadeInvalida;
import school.sptech.projetoMima.repository.ItemRepository;
import school.sptech.projetoMima.repository.FornecedorRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    @DisplayName("BuscarPorId com ID válido retorna o item esperado e chama findById uma vez")
    void testeX1() {
        Item item = new Item();
        when(itemRepository.findById(1)).thenReturn(Optional.of(item));

        Item resultado = itemService.buscarPorId(1);

        assertEquals(item, resultado);
        verify(itemRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("BuscarPorId com ID inválido lança ItemNaoEncontradoException e chama findById uma vez")
    void testeX2() {
        when(itemRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ItemNaoEncontradoException.class, () -> itemService.buscarPorId(999));
        verify(itemRepository, times(1)).findById(999);
    }

    @Test
    @DisplayName("Deletar chama método delete do repositório exatamente uma vez")
    void testeDeletar() {
        Item item = new Item();
        itemService.deletar(item);
        verify(itemRepository, times(1)).delete(item);
    }

    @Test
    @DisplayName("Validação lança exceção quando campos obrigatórios estiverem vazios")
    void testeValidarCampoVazio() {
        Item item = new Item();
        Categoria categoria = new Categoria();
        categoria.setNome("  ");
        item.setCategoria(categoria);

        assertThrows(ItemCampoVazioException.class, () -> itemService.validarCampoVazio(item));
        verifyNoInteractions(itemRepository);
    }

    @Test
    @DisplayName("Lança exceção se preço for inválido (não numérico) ou menor que zero")
    void testeValidarPreco() {
        Item item = new Item();
        item.setPreco(null);

        assertThrows(ItemCampoVazioException.class, () -> itemService.validarPreco(item));
        verifyNoInteractions(itemRepository);
    }

    @Test
    @DisplayName("Lança exceção se quantidade for menor ou igual a zero")
    void testeValidarQuantidade() {
        Item item = new Item();
        item.setQtdEstoque(0);

        assertThrows(ItemQuantidadeInvalida.class, () -> itemService.validarQuantidade(item));
        verifyNoInteractions(itemRepository);
    }

    @Test
    @DisplayName("Retorna true se categoria contém caracteres especiais")
    void testeValidarCaracteres() {
        Item item = new Item();
        Categoria categoria = new Categoria();
        categoria.setNome("CAMISETA!");
        item.setCategoria(categoria);

        boolean resultado = itemService.validarCaracteres(item);

        assertTrue(resultado);
        verifyNoInteractions(itemRepository);
    }

    @Test
    @DisplayName("Pesquisar por nome de item cadastrado. Deve retornar todos os itens contendo o termo")
    void pesquisarPorNomeQuandoAcionadoComNomeDeRoupaValidaELowerCaseDeveRetornarTodosOsResultados() {
        List<Item> itensValidos = List.of(
                new Item(1, null, null, "Vestido", null, null, null, null, null, null),
                new Item(2, null, null, "vestido", null, null, null, null, null, null),
                new Item(3, null, null, "VESTIDO", null, null, null, null, null, null)
        );

        String busca = "vestido";

        when(itemRepository.findByNomeContainsIgnoreCase(busca)).thenReturn(itensValidos);
        List<Item> listagem = itemService.filtrarPorNome(busca);
        assertEquals(3, listagem.size());
    }

    @Test
    @DisplayName("Pesquisar por nome de item cadastrado. Deve retornar todos os itens contendo o termo")
    void pesquisarPorNomeQuandoAcionadoComNomeDeRoupaValidaEUpperCaseDeveRetornarTodosOsResultados() {
        List<Item> itensValidos = List.of(
                new Item(1, null, null, "Vestido", null, null, null, null, null, null),
                new Item(2, null, null, "vestido", null, null, null, null, null, null),
                new Item(3, null, null, "VESTIDO", null, null, null, null, null, null)
        );

        String busca = "VESTIDO";

        when(itemRepository.findByNomeContainsIgnoreCase(busca)).thenReturn(itensValidos);
        List<Item> listagem = itemService.filtrarPorNome(busca);
        assertEquals(3, listagem.size());
    }

    @Test
    @DisplayName("Quando pesquisar por nome inexistente deve retornar ItemNaoEncontradoException")
    void pesquisarPorNomeQuandoTermoNaoExisteDeveRetornarItemNaoEncontradoException() {
        String busca = "calça";

        when(itemRepository.findByNomeContainsIgnoreCase(busca)).thenReturn(List.of());

        assertThrows(ItemNaoEncontradoException.class, () -> itemService.filtrarPorNome(busca));
    }

    @Test
    @DisplayName("Quando tentar pesquisar sem digitar nenhum termo, deve lançar exceção")
    void filtrarPorNomeQuandoPesquisarSemDigitarNadaDeveLancarNullPointerException() {
        String nome = null;

        assertThrows(NullPointerException.class, () -> itemService.filtrarPorNome(nome));
    }
}
