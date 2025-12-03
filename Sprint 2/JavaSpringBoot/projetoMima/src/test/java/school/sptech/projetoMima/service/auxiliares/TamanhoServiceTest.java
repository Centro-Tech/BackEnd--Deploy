package school.sptech.projetoMima.service.auxiliares;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import school.sptech.projetoMima.dto.itemDto.auxiliares.TamanhoDto;
import school.sptech.projetoMima.entity.item.Tamanho;
import school.sptech.projetoMima.exception.Item.Auxiliares.TamanhoListaVaziaException;
import school.sptech.projetoMima.exception.Item.Auxiliares.TamanhoNaoEncontradoException;
import school.sptech.projetoMima.repository.auxiliares.TamanhoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TamanhoServiceTest {

    @InjectMocks
    private TamanhoService tamanhoService;

    @Mock
    private TamanhoRepository tamanhoRepository;

    @Test
    @DisplayName("buscarPorId() quando receber ID válido, deve retornar tamanho correspondente")
    void buscarPorId_valido() {
        Tamanho tamanho = new Tamanho();
        tamanho.setId(1);
        tamanho.setNome("M");

        Mockito.when(tamanhoRepository.findById(1)).thenReturn(Optional.of(tamanho));

        Tamanho resultado = tamanhoService.buscarPorId(1);

        assertEquals("M", resultado.getNome());
        Mockito.verify(tamanhoRepository, Mockito.times(1)).findById(1);
    }

    @Test
    @DisplayName("buscarPorId() quando receber ID inválido, deve lançar TamanhoNaoEncontradoException")
    void buscarPorId_invalido() {
        Mockito.when(tamanhoRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(TamanhoNaoEncontradoException.class, () -> tamanhoService.buscarPorId(999));
        Mockito.verify(tamanhoRepository, Mockito.times(1)).findById(999);
    }

    @Test
    @DisplayName("listar() quando houver tamanhos, deve retornar lista com tamanhos")
    void listar_comTamanhos() {
        List<Tamanho> tamanhos = new ArrayList<>();
        tamanhos.add(new Tamanho());
        tamanhos.add(new Tamanho());

        Mockito.when(tamanhoRepository.findAll()).thenReturn(tamanhos);

        List<Tamanho> resultado = tamanhoService.listar();

        assertEquals(2, resultado.size());
        Mockito.verify(tamanhoRepository, Mockito.times(2)).findAll();
    }

    @Test
    @DisplayName("listar() quando não houver tamanhos, deve lançar TamanhoListaVaziaException")
    void listar_semTamanhos() {
        Mockito.when(tamanhoRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(TamanhoListaVaziaException.class, () -> tamanhoService.listar());
        Mockito.verify(tamanhoRepository, Mockito.times(1)).findAll();
    }


    @Test
    @DisplayName("deletar() quando tamanho existe, deve deletar com sucesso")
    void deletar_existente() {
        int id = 1;
        Mockito.when(tamanhoRepository.existsById(id)).thenReturn(true);
        Mockito.doNothing().when(tamanhoRepository).deleteById(id);

        assertDoesNotThrow(() -> tamanhoService.deletar(id));

        Mockito.verify(tamanhoRepository).existsById(id);
        Mockito.verify(tamanhoRepository).deleteById(id);
    }

    @Test
    @DisplayName("deletar() quando tamanho não existe, deve lançar TamanhoNaoEncontradoException")
    void deletar_inexistente() {
        Mockito.when(tamanhoRepository.existsById(999)).thenReturn(false);

        assertThrows(TamanhoNaoEncontradoException.class, () -> tamanhoService.deletar(999));
        Mockito.verify(tamanhoRepository, Mockito.never()).delete(Mockito.any());
    }
}
