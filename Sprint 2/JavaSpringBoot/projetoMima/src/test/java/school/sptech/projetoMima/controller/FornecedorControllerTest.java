package school.sptech.projetoMima.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import school.sptech.projetoMima.dto.fornecedorDto.FornecedorMapper;
import school.sptech.projetoMima.dto.fornecedorDto.FornecedorRequestDto;
import school.sptech.projetoMima.dto.fornecedorDto.FornecedorResponseDto;
import school.sptech.projetoMima.entity.Fornecedor;
import static org.mockito.Mockito.*;
import school.sptech.projetoMima.exception.Fornecedor.FornecedorExistenteException;
import school.sptech.projetoMima.exception.Fornecedor.FornecedorNaoEncontradoException;
import school.sptech.projetoMima.service.FornecedorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FornecedorControllerTest {

    @InjectMocks
    private FornecedorController controller;

    @Mock
    private FornecedorService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("listar() quando houver fornecedores deve retornar lista com status 200")
    void listar() {
        List<Fornecedor> fornecedores = new ArrayList<>();

        Fornecedor fornecedor1 = new Fornecedor();
        fornecedor1.setId(1);
        fornecedor1.setNome("Fornecedor 1");
        fornecedor1.setEmail("email1@fornecedor.com");
        fornecedor1.setTelefone("1111-1111");

        Fornecedor fornecedor2 = new Fornecedor();
        fornecedor2.setId(2);
        fornecedor2.setNome("Fornecedor 2");
        fornecedor2.setEmail("email2@fornecedor.com");
        fornecedor2.setTelefone("2222-2222");

        fornecedores.add(fornecedor1);
        fornecedores.add(fornecedor2);

        Mockito.when(service.listar()).thenReturn(fornecedores);

        ResponseEntity<List<FornecedorResponseDto>> response = controller.listar();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(fornecedores.size(), response.getBody().size());
        Mockito.verify(service, Mockito.times(1)).listar();
    }

    @Test
    @DisplayName("listar() quando não houver fornecedores deve retornar status 404")
    void listar2() {
        Mockito.when(service.listar()).thenReturn(new ArrayList<>());

        ResponseEntity<List<FornecedorResponseDto>> response = controller.listar();

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        Mockito.verify(service, Mockito.times(1)).listar();
    }

    @Test
    @DisplayName("buscar() quando id existir deve retornar fornecedor com status 200")
    void buscar() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(1);
        fornecedor.setNome("Fornecedor 1");
        fornecedor.setEmail("email1@fornecedor.com");
        fornecedor.setTelefone("1111-1111");

        Mockito.when(service.findById(1)).thenReturn(Optional.of(fornecedor));

        ResponseEntity<FornecedorResponseDto> response = controller.buscar(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(fornecedor.getNome(), response.getBody().getNome());
        Mockito.verify(service, Mockito.times(1)).findById(1);
    }

    @Test
    @DisplayName("buscar() quando id não existir deve retornar status 404")
    void buscar2() {
        Mockito.when(service.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        ResponseEntity<FornecedorResponseDto> response = controller.buscar(1);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        Mockito.verify(service, Mockito.times(1)).findById(1);
    }

    @Test
    @DisplayName("cadastrar() quando dados válidos deve retornar fornecedor cadastrado com status 201")
    void cadastrar() {
        FornecedorRequestDto requestDto = new FornecedorRequestDto();
        requestDto.setNome("Fornecedor 1");
        requestDto.setEmail("email1@fornecedor.com");
        requestDto.setTelefone("11987678890");

        Fornecedor fornecedorSalvo = new Fornecedor();
        fornecedorSalvo.setId(1);
        fornecedorSalvo.setNome(requestDto.getNome());
        fornecedorSalvo.setEmail(requestDto.getEmail());
        fornecedorSalvo.setTelefone(requestDto.getTelefone());

        Mockito.when(service.cadastrar(Mockito.any(Fornecedor.class))).thenReturn(fornecedorSalvo);

        ResponseEntity<FornecedorResponseDto> response = controller.cadastrar(requestDto);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(fornecedorSalvo.getNome(), response.getBody().getNome());
        Mockito.verify(service, Mockito.times(1)).cadastrar(Mockito.any(Fornecedor.class));
    }

    @Test
    @DisplayName("cadastrar() quando fornecedor duplicado deve lançar FornecedorExistenteException")
    void cadastrar2() {
        FornecedorRequestDto requestDto = new FornecedorRequestDto();
        requestDto.setNome("Fornecedor 1");
        requestDto.setEmail("email1@fornecedor.com");
        requestDto.setTelefone("1111-1111");

        Mockito.when(service.cadastrar(Mockito.any(Fornecedor.class)))
                .thenThrow(new FornecedorExistenteException("Fornecedor já cadastrado"));

        assertThrows(FornecedorExistenteException.class, () -> controller.cadastrar(requestDto));
        Mockito.verify(service, Mockito.times(1)).cadastrar(Mockito.any(Fornecedor.class));
    }

    @Test
    @DisplayName("excluir() quando id existir deve deletar fornecedor e retornar status 204")
    void excluir() {
        Mockito.doNothing().when(service).deletar(1);

        ResponseEntity<Void> response = controller.excluir(1);

        assertEquals(204, response.getStatusCodeValue());
        Mockito.verify(service, Mockito.times(1)).deletar(1);
    }

    @Test
    @DisplayName("excluir() quando id não existir deve lançar FornecedorNaoEncontradoException")
    void excluir2() {
        Mockito.doThrow(new FornecedorNaoEncontradoException("Fornecedor não encontrado"))
                .when(service).deletar(1);

        assertThrows(FornecedorNaoEncontradoException.class, () -> controller.excluir(1));
        Mockito.verify(service, Mockito.times(1)).deletar(1);
    }

    @Test
    @DisplayName("Quando tentar cadastrar com email que não é válido, deve estourar exceção")
    void cadastrarQuandoAcionadoComEmailInvalidoDeveRetornarIllegalArgumentException() {
        FornecedorRequestDto fornecedor = new FornecedorRequestDto();
        fornecedor.setTelefone("119789877");
        fornecedor.setNome("Empresa A LTDA");
        fornecedor.setEmail("emailinvalido.com");

        when(service.cadastrar(argThat(f ->
                !f.getEmail().contains("@")
        ))).thenThrow(new IllegalArgumentException("E-mail inválido"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.cadastrar(fornecedor);
        });

        assertEquals("E-mail inválido", exception.getMessage());
        verify(service, times(1)).cadastrar(argThat(f ->
                f.getNome().equals(fornecedor.getNome()) &&
                        f.getEmail().equals(fornecedor.getEmail()) &&
                        f.getTelefone().equals(fornecedor.getTelefone())
        ));
    }

    @Test
    @DisplayName("Quando tentar cadastrar com telefone contendo caracteres não numéricos, deve estourar exceção")
    void cadastrarQuandoAcionadoComTelefoneQueTenhaParentesesDeveRetornarBadRequest() {
        FornecedorRequestDto fornecedor = new FornecedorRequestDto();
        fornecedor.setTelefone("(11)9789877");
        fornecedor.setNome("Empresa A LTDA");
        fornecedor.setEmail("email@valido.com");

        when(service.cadastrar(argThat(f ->
                !f.getTelefone().matches("\\d+")
        ))).thenThrow(new IllegalArgumentException("Telefone inválido"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.cadastrar(fornecedor);
        });

        assertEquals("Telefone inválido", exception.getMessage());
        verify(service, times(1)).cadastrar(argThat(f ->
                f.getNome().equals(fornecedor.getNome()) &&
                        f.getEmail().equals(fornecedor.getEmail()) &&
                        f.getTelefone().equals(fornecedor.getTelefone())
        ));
    }

    @Test
    @DisplayName("Quando tentar cadastrar com telefone de mais de 11 dígitos, deve estourar exceção")
    void cadastrarQuandoAcionadoComTelefoneQueTenha13DigitosDeveRetornarBadRequest() {
        FornecedorRequestDto fornecedor = new FornecedorRequestDto();
        fornecedor.setTelefone("5511981276566");
        fornecedor.setNome("Empresa A LTDA");
        fornecedor.setEmail("email@valido.com");

        when(service.cadastrar(argThat(f ->
                f.getTelefone().length() > 11
        ))).thenThrow(new IllegalArgumentException("Telefone inválido"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.cadastrar(fornecedor);
        });

        assertEquals("Telefone inválido", exception.getMessage());
        verify(service, times(1)).cadastrar(argThat(f ->
                f.getNome().equals(fornecedor.getNome()) &&
                        f.getEmail().equals(fornecedor.getEmail()) &&
                        f.getTelefone().equals(fornecedor.getTelefone())
        ));
    }
}
