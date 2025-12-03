package school.sptech.projetoMima.service.auxiliares;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.projetoMima.entity.item.Categoria;
import school.sptech.projetoMima.exception.Item.Auxiliares.CategoriaDuplicadaException;
import school.sptech.projetoMima.exception.Item.Auxiliares.CategoriaInvalidoException;
import school.sptech.projetoMima.exception.Item.Auxiliares.CategoriaListaVaziaException;
import school.sptech.projetoMima.exception.Item.Auxiliares.CategoriaNaoEncontradoException;
import school.sptech.projetoMima.repository.auxiliares.CategoriaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @InjectMocks
    private CategoriaService categoriaService;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Test
    @DisplayName("listar() deve retornar lista de categorias quando houver registros")
    void listar() {
        List<Categoria> categorias = Arrays.asList(new Categoria(), new Categoria());
        Mockito.when(categoriaRepository.findAll()).thenReturn(categorias);

        List<Categoria> resultado = categoriaService.listar();

        assertEquals(2, resultado.size());
        Mockito.verify(categoriaRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("listar() deve lançar CategoriaListaVaziaException quando não houver categorias")
    void listar_listaVazia() {
        Mockito.when(categoriaRepository.findAll()).thenReturn(List.of());

        assertThrows(CategoriaListaVaziaException.class, () -> categoriaService.listar());
        Mockito.verify(categoriaRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("buscarPorId() deve retornar categoria quando ID válido é informado")
    void buscarPorId() {
        Categoria categoria = new Categoria();
        Mockito.when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));

        Categoria resultado = categoriaService.buscarPorId(1);

        assertNotNull(resultado);
        Mockito.verify(categoriaRepository, Mockito.times(1)).findById(1);
    }

    @Test
    @DisplayName("buscarPorId() deve lançar CategoriaNaoEncontradoException quando ID não existe")
    void buscarPorId_naoEncontrado() {
        Mockito.when(categoriaRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CategoriaNaoEncontradoException.class, () -> categoriaService.buscarPorId(1));
        Mockito.verify(categoriaRepository, Mockito.times(1)).findById(1);
    }

    @Test
    @DisplayName("salvar() deve persistir categoria válida com sucesso")
    void salvar() {
        Categoria categoria = new Categoria();
        categoria.setNome("Moda");

        Mockito.when(categoriaRepository.existsByNomeIgnoreCase("Moda")).thenReturn(false);
        Mockito.when(categoriaRepository.save(categoria)).thenReturn(categoria);

        Categoria resultado = categoriaService.salvar(categoria);

        assertNotNull(resultado);
        Mockito.verify(categoriaRepository).save(categoria);
    }

    @Test
    @DisplayName("salvar() deve lançar CategoriaInvalidoException quando categoria for nula")
    void salvar_nula() {
        assertThrows(CategoriaInvalidoException.class, () -> categoriaService.salvar(null));
        Mockito.verify(categoriaRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("salvar() deve lançar CategoriaDuplicadaException quando nome já existir")
    void salvar_duplicada() {
        Categoria categoria = new Categoria();
        categoria.setNome("Moda");

        Mockito.when(categoriaRepository.existsByNomeIgnoreCase("Moda")).thenReturn(true);

        assertThrows(CategoriaDuplicadaException.class, () -> categoriaService.salvar(categoria));
        Mockito.verify(categoriaRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("deletar() deve excluir categoria com ID válido")
    void deletar() {
        Mockito.when(categoriaRepository.existsById(1)).thenReturn(true);
        Mockito.doNothing().when(categoriaRepository).deleteById(1);

        categoriaService.deletar(1);

        Mockito.verify(categoriaRepository).existsById(1);
        Mockito.verify(categoriaRepository).deleteById(1);
    }

    @Test
    @DisplayName("deletar() deve lançar CategoriaNaoEncontradoException se ID não existir")
    void deletar_naoEncontrado() {
        Mockito.when(categoriaRepository.existsById(1)).thenReturn(false);

        assertThrows(CategoriaNaoEncontradoException.class, () -> categoriaService.deletar(1));
        Mockito.verify(categoriaRepository).existsById(1);
        Mockito.verify(categoriaRepository, Mockito.never()).deleteById(1);
    }
}
