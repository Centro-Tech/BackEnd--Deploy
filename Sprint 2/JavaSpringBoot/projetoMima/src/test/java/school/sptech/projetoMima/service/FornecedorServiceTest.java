package school.sptech.projetoMima.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import school.sptech.projetoMima.entity.Fornecedor;
import school.sptech.projetoMima.exception.Fornecedor.FornecedorExistenteException;
import school.sptech.projetoMima.repository.FornecedorRepository;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FornecedorServiceTest {

    @InjectMocks
    private FornecedorService service;

    @Mock
    private FornecedorRepository repository;

    @Test
    @DisplayName("Quando tentar cadastrar com todos os campos, deve retornar o fornecedor cadastrado")
    void cadastrarQuandoAcionadoComTodosOsCamposPreenchidosCorretamenteDeveRetornarODtoResponseDoFornecedorCadastrado() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setEmail("fornecedor@gmail.com");
        fornecedor.setNome("Empresa A LTDA");
        fornecedor.setTelefone("11987654567");

        Fornecedor fornecedorSalvo = new Fornecedor();
        fornecedorSalvo.setId(1);
        fornecedorSalvo.setNome(fornecedor.getNome());
        fornecedorSalvo.setEmail(fornecedor.getEmail());
        fornecedorSalvo.setTelefone(fornecedor.getTelefone());

        when(repository.save(Mockito.any(Fornecedor.class))).thenReturn(fornecedorSalvo);

        Fornecedor resultado = service.cadastrar(fornecedor);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Empresa A LTDA", resultado.getNome());
        assertEquals("fornecedor@gmail.com", resultado.getEmail());
        assertEquals("11987654567", resultado.getTelefone());

        verify(repository, times(1)).save(any(Fornecedor.class));
    }

    @Test
    @DisplayName("Quando tentar cadastrar com todos os campos vazios, deve estourar exceção")
    void cadastrarQuandoAcionadoComCorpoNuloDeveRetornarNullPointerException() {
        Fornecedor fornecedor = new Fornecedor();
        assertThrows(NullPointerException.class, () -> {service.cadastrar(fornecedor);});
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Quando tentar cadastrar com apenas o nome preenchido, deve estourar exceção")
    void cadastrarQuandoAcionadoComCorpoIncompletoDeveRetornarNullPointerException() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setTelefone(null);
        fornecedor.setEmail(null);
        fornecedor.setNome("Empresa A LTDA");

        assertThrows(NullPointerException.class, () -> {service.cadastrar(fornecedor);});
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Quando tentar cadastrar fornecedor já cadastrado, deve estourar exceção")
    void cadastrarQuandoAcionadoComFornecedorJaCadastradoNoSistemaDeveRetornarConflict() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome("Fornecedor X");
        fornecedor.setEmail("email@exemplo.com");
        fornecedor.setTelefone("123456789");

        when(repository.existsByNome("Fornecedor X")).thenReturn(true);

        assertThrows(FornecedorExistenteException.class, () -> {
            service.cadastrar(fornecedor);
        });
    }

}