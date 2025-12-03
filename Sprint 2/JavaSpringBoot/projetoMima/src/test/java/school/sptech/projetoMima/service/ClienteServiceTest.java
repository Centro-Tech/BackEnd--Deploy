package school.sptech.projetoMima.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.projetoMima.dto.clienteDto.ClienteCadastroDto;
import school.sptech.projetoMima.dto.clienteDto.ClienteMapper;
import school.sptech.projetoMima.entity.Cliente;
import school.sptech.projetoMima.exception.Cliente.ClienteNaoEncontradoException;
import school.sptech.projetoMima.repository.ClienteRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("findClienteById() deve retornar cliente quando encontrado")
    void findClienteById_sucesso() {
        int id = 1;
        Cliente cliente = new Cliente();
        cliente.setId(id);

        Mockito.when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        Cliente resultado = clienteService.findClienteById(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        Mockito.verify(clienteRepository).findById(id);
    }

    @Test
    @DisplayName("findClienteById() deve lançar ClienteNaoEncontradoException quando cliente não encontrado")
    void findClienteById_naoEncontrado() {
        int id = 1;

        Mockito.when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        ClienteNaoEncontradoException exception = assertThrows(ClienteNaoEncontradoException.class, () -> {
            clienteService.findClienteById(id);
        });

        assertEquals("Cliente com o ID " + id + " não encontrado!", exception.getMessage());
        Mockito.verify(clienteRepository).findById(id);
    }

    @Test
    @DisplayName("cadastrarCliente() deve salvar e retornar cliente")
    void cadastrarCliente_sucesso() {
        ClienteCadastroDto dto = new ClienteCadastroDto();

        Cliente clienteEntity = new Cliente();


        Mockito.mockStatic(ClienteMapper.class).when(() -> ClienteMapper.toEntity(dto)).thenReturn(clienteEntity);
        Mockito.when(clienteRepository.save(clienteEntity)).thenReturn(clienteEntity);

        Cliente resultado = clienteService.cadastrarCliente(dto);

        assertNotNull(resultado);
        Mockito.verify(clienteRepository).save(clienteEntity);
    }

    @Test
    @DisplayName("atualizarCliente() deve atualizar e salvar cliente existente")
    void atualizarCliente_sucesso() {
        Integer id = 1;
        ClienteCadastroDto dto = new ClienteCadastroDto();
        dto.setNome("Novo Nome");
        dto.setCPF("12345678900");
        dto.setTelefone("999999999");
        dto.setEmail("novoemail@teste.com");
        dto.setEndereco("Rua Nova, 123");

        Cliente clienteExistente = new Cliente();
        clienteExistente.setId(id);

        Mockito.when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));
        Mockito.when(clienteRepository.save(clienteExistente)).thenReturn(clienteExistente);

        Cliente resultado = clienteService.atualizarCliente(dto, id);

        assertNotNull(resultado);
        assertEquals(dto.getNome(), resultado.getNome());
        assertEquals(dto.getCPF(), resultado.getCPF());
        assertEquals(dto.getTelefone(), resultado.getTelefone());
        assertEquals(dto.getEmail(), resultado.getEmail());
        assertEquals(dto.getEndereco(), resultado.getEndereco());

        Mockito.verify(clienteRepository).findById(id);
        Mockito.verify(clienteRepository).save(clienteExistente);
    }

    @Test
    @DisplayName("atualizarCliente() deve lançar ClienteNaoEncontradoException quando cliente não existir")
    void atualizarCliente_naoEncontrado() {
        Integer id = 1;
        ClienteCadastroDto dto = new ClienteCadastroDto();

        Mockito.when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        ClienteNaoEncontradoException exception = assertThrows(ClienteNaoEncontradoException.class, () -> {
            clienteService.atualizarCliente(dto, id);
        });

        assertEquals("Cliente com o ID " + id + " não encontrado!", exception.getMessage());
        Mockito.verify(clienteRepository).findById(id);
        Mockito.verify(clienteRepository, Mockito.never()).save(Mockito.any());
    }



}