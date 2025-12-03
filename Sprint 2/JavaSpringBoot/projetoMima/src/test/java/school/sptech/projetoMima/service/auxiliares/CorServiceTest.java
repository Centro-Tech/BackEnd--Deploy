package school.sptech.projetoMima.service.auxiliares;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.projetoMima.entity.item.Cor;
import school.sptech.projetoMima.exception.Item.Auxiliares.CorDuplicadaException;
import school.sptech.projetoMima.exception.Item.Auxiliares.CorListaVaziaException;
import school.sptech.projetoMima.exception.Item.Auxiliares.CorNaoEncontradoException;
import school.sptech.projetoMima.repository.auxiliares.CorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CorServiceTest {

    @InjectMocks
    private CorService corService;

    @Mock
    private CorRepository corRepository;

    @Test
    @DisplayName("listar() deve retornar lista de cores quando houver cores")
    void listarComCores() {
        List<Cor> cores = List.of(new Cor(), new Cor());

        Mockito.when(corRepository.findAll()).thenReturn(cores);

        List<Cor> resultado = corService.listar();

        assertEquals(2, resultado.size());
        Mockito.verify(corRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("listar() deve lançar CorListaVaziaException quando não houver cores")
    void listarSemCores() {
        Mockito.when(corRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(CorListaVaziaException.class, () -> corService.listar());
        Mockito.verify(corRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("buscarPorId() deve retornar cor existente")
    void buscarPorIdExistente() {
        Cor cor = new Cor();
        cor.setId(1);
        cor.setNome("Azul");

        Mockito.when(corRepository.findById(1)).thenReturn(Optional.of(cor));

        Cor resultado = corService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals("Azul", resultado.getNome());
        Mockito.verify(corRepository, Mockito.times(1)).findById(1);
    }

    @Test
    @DisplayName("buscarPorId() deve lançar CorNaoEncontradaException quando ID for inválido")
    void buscarPorIdInexistente() {
        Mockito.when(corRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CorNaoEncontradoException.class, () -> corService.buscarPorId(1));
        Mockito.verify(corRepository, Mockito.times(1)).findById(1);
    }

    @Test
    @DisplayName("salvar() deve salvar nova cor se nome for único")
    void salvarCorNova() {
        Cor cor = new Cor();
        cor.setNome("Verde");

        Mockito.when(corRepository.existsByNomeIgnoreCase("Verde")).thenReturn(false);
        Mockito.when(corRepository.save(cor)).thenReturn(cor);

        Cor resultado = corService.salvar(cor);

        assertNotNull(resultado);
        assertEquals("Verde", resultado.getNome());
        Mockito.verify(corRepository).existsByNomeIgnoreCase("Verde");
        Mockito.verify(corRepository).save(cor);
    }

    @Test
    @DisplayName("salvar() deve lançar CorDuplicadaException se nome já existir")
    void salvarCorDuplicada() {
        Cor cor = new Cor();
        cor.setNome("Verde");

        Mockito.when(corRepository.existsByNomeIgnoreCase("Verde")).thenReturn(true);

        assertThrows(CorDuplicadaException.class, () -> corService.salvar(cor));
        Mockito.verify(corRepository).existsByNomeIgnoreCase("Verde");
        Mockito.verify(corRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("deletar() deve excluir cor com ID válido")
    void deletar() {
        Mockito.doNothing().when(corRepository).deleteById(1);

        corService.deletar(1);

        Mockito.verify(corRepository, Mockito.times(1)).deleteById(1);
    }
}
