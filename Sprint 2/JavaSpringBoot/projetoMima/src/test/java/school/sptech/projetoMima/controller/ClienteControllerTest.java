package school.sptech.projetoMima.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.projetoMima.dto.clienteDto.ClienteCadastroDto;
import school.sptech.projetoMima.entity.Cliente;
import school.sptech.projetoMima.exception.Cliente.ClienteInvalidoException;
import school.sptech.projetoMima.exception.Cliente.ClienteListaVaziaException;
import school.sptech.projetoMima.exception.Cliente.ClienteNaoEncontradoException;
import school.sptech.projetoMima.service.ClienteService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @InjectMocks
    private ClienteController controller;

    @Mock
    private ClienteService clienteService;

    @Test
    @DisplayName("buscarPorId() quando receber ID válido, deve retornar cliente correspondente")
    void buscarPorId() {
        Cliente cliente = new Cliente();

        Mockito.when(clienteService.findClienteById(1)).thenReturn(cliente);

        var response = controller.buscarPorId(1);

        assertEquals(200, response.getStatusCodeValue());
        Mockito.verify(clienteService, Mockito.times(1)).findClienteById(1);
    }

    @Test
    @DisplayName("buscarPorId() quando receber ID inválido, deve lançar ClienteNaoEncontradoException")
    void buscarPorId2() {
        Mockito.when(clienteService.findClienteById(1)).thenThrow(new ClienteNaoEncontradoException("Cliente não encontrado"));

        assertThrows(ClienteNaoEncontradoException.class, () -> controller.buscarPorId(1));
        Mockito.verify(clienteService, Mockito.times(1)).findClienteById(1);
    }

    @Test
    @DisplayName("listar() quando houver clientes, deve retornar lista com clientes")
    void listar() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente());
        clientes.add(new Cliente());

        Mockito.when(clienteService.listarClientes()).thenReturn(clientes);

        var response = controller.listar();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        Mockito.verify(clienteService, Mockito.times(1)).listarClientes();
    }

    @Test
    @DisplayName("listar() quando não houver clientes, deve lançar ClienteListaVaziaException")
    void listar2() {
        Mockito.when(clienteService.listarClientes()).thenThrow(new ClienteListaVaziaException("Nenhum cliente encontrado"));

        assertThrows(ClienteListaVaziaException.class, () -> controller.listar());
        Mockito.verify(clienteService, Mockito.times(1)).listarClientes();
    }

    @Test
    @DisplayName("cadastrar() quando dados válidos, deve cadastrar cliente e retornar status 201")
    void cadastrar() {
        ClienteCadastroDto dto = new ClienteCadastroDto();
        Cliente cliente = new Cliente();

        Mockito.when(clienteService.cadastrarCliente(dto)).thenReturn(cliente);

        var response = controller.cadastrar(dto);

        assertEquals(201, response.getStatusCodeValue());
        Mockito.verify(clienteService, Mockito.times(1)).cadastrarCliente(dto);
    }

    @Test
    @DisplayName("cadastrar() quando dados inválidos, deve lançar ClienteInvalidoException")
    void cadastrar2() {
        ClienteCadastroDto dto = new ClienteCadastroDto();

        Mockito.when(clienteService.cadastrarCliente(dto)).thenThrow(new ClienteInvalidoException("Dados inválidos"));

        assertThrows(ClienteInvalidoException.class, () -> controller.cadastrar(dto));
        Mockito.verify(clienteService, Mockito.times(1)).cadastrarCliente(dto);
    }

    @Test
    @DisplayName("atualizar() quando cliente existe, deve atualizar e retornar cliente")
    void atualizar() {
        ClienteCadastroDto dto = new ClienteCadastroDto();
        Cliente cliente = new Cliente();

        Mockito.when(clienteService.atualizarCliente(dto, 1)).thenReturn(cliente);

        var response = controller.atualizar(dto, 1);

        assertEquals(200, response.getStatusCodeValue());
        Mockito.verify(clienteService, Mockito.times(1)).atualizarCliente(dto, 1);
    }

    @Test
    @DisplayName("atualizar() quando cliente não existe, deve lançar ClienteNaoEncontradoException")
    void atualizar2() {
        ClienteCadastroDto dto = new ClienteCadastroDto();

        Mockito.when(clienteService.atualizarCliente(dto, 1)).thenThrow(new ClienteNaoEncontradoException("Cliente não encontrado"));

        assertThrows(ClienteNaoEncontradoException.class, () -> controller.atualizar(dto, 1));
        Mockito.verify(clienteService, Mockito.times(1)).atualizarCliente(dto, 1);
    }

    @Test
    @DisplayName("deletar() quando cliente existe, deve deletar com sucesso")
    void deletar() {
        Mockito.doNothing().when(clienteService).excluir(1);

        var response = controller.deletar(1);

        assertEquals(204, response.getStatusCodeValue());
        Mockito.verify(clienteService, Mockito.times(1)).excluir(1);
    }

    @Test
    @DisplayName("deletar() quando cliente não existe, deve lançar ClienteNaoEncontradoException")
    void deletar2() {
        Mockito.doThrow(new ClienteNaoEncontradoException("Cliente não encontrado")).when(clienteService).excluir(1);

        assertThrows(ClienteNaoEncontradoException.class, () -> controller.deletar(1));
        Mockito.verify(clienteService, Mockito.times(1)).excluir(1);
    }
}
