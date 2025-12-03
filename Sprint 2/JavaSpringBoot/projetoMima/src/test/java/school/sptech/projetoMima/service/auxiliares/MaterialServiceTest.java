package school.sptech.projetoMima.service.auxiliares;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.projetoMima.entity.item.Material;
import school.sptech.projetoMima.exception.Item.Auxiliares.MaterialInvalidoException;
import school.sptech.projetoMima.exception.Item.Auxiliares.MaterialListaVaziaException;
import school.sptech.projetoMima.exception.Item.Auxiliares.MaterialNaoEncontradoException;
import school.sptech.projetoMima.repository.auxiliares.MaterialRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MaterialServiceTest {

    @InjectMocks
    private MaterialService materialService;

    @Mock
    private MaterialRepository materialRepository;

    @Test
    @DisplayName("listar() deve retornar lista de materiais quando houver registros")
    void listar() {
        List<Material> materiais = Arrays.asList(new Material(), new Material());
        Mockito.when(materialRepository.findAll()).thenReturn(materiais);

        List<Material> resultado = materialService.listar();

        assertEquals(2, resultado.size());
        Mockito.verify(materialRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("listar() deve lançar MaterialListaVaziaException quando não houver materiais")
    void listar2() {
        Mockito.when(materialRepository.findAll()).thenReturn(List.of());

        assertThrows(MaterialListaVaziaException.class, () -> materialService.listar());
        Mockito.verify(materialRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("buscarPorId() deve retornar material quando ID válido é informado")
    void buscarPorId() {
        Material material = new Material();
        Mockito.when(materialRepository.findById(1)).thenReturn(Optional.of(material));

        Material resultado = materialService.buscarPorId(1);

        assertNotNull(resultado);
        Mockito.verify(materialRepository, Mockito.times(1)).findById(1);
    }

    @Test
    @DisplayName("buscarPorId() deve lançar MaterialNaoEncontradoException quando ID não existe")
    void buscarPorId2() {
        Mockito.when(materialRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(MaterialNaoEncontradoException.class, () -> materialService.buscarPorId(1));
        Mockito.verify(materialRepository, Mockito.times(1)).findById(1);
    }

    @Test
    @DisplayName("salvar() deve persistir material válido com sucesso")
    void salvar() {
        Material material = new Material();
        Mockito.when(materialRepository.save(material)).thenReturn(material);

        Material resultado = materialService.salvar(material);

        assertNotNull(resultado);
        Mockito.verify(materialRepository, Mockito.times(1)).save(material);
    }

    @Test
    @DisplayName("salvar() deve lançar MaterialInvalidoException quando material for nulo")
    void salvar2() {
        assertThrows(MaterialInvalidoException.class, () -> materialService.salvar(null));
        Mockito.verify(materialRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("deletar() deve excluir material com ID válido")
    void deletar() {
        Mockito.when(materialRepository.existsById(1)).thenReturn(true);
        Mockito.doNothing().when(materialRepository).deleteById(1);

        materialService.deletar(1);

        Mockito.verify(materialRepository, Mockito.times(1)).existsById(1);
        Mockito.verify(materialRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    @DisplayName("deletar() deve lançar MaterialNaoEncontradoException se ID não existir")
    void deletar2() {
        Mockito.when(materialRepository.existsById(1)).thenReturn(false);

        assertThrows(MaterialNaoEncontradoException.class, () -> materialService.deletar(1));
        Mockito.verify(materialRepository, Mockito.times(1)).existsById(1);
        Mockito.verify(materialRepository, Mockito.never()).deleteById(1);
    }
}
