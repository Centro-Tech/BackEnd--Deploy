package school.sptech.projetoMima.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.projetoMima.dto.clienteDto.ClienteCadastroDto;
import school.sptech.projetoMima.dto.clienteDto.ClienteMapper;
import school.sptech.projetoMima.entity.Cliente;
import school.sptech.projetoMima.exception.Cliente.ClienteListaVaziaException;
import school.sptech.projetoMima.exception.Cliente.ClienteNaoEncontradoException;
import school.sptech.projetoMima.repository.ClienteRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente findClienteById(int id) {
        return clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException("Cliente com o ID " + id + " não encontrado!"));
    }

    public Cliente cadastrarCliente(ClienteCadastroDto dto) {
        Cliente clienteCad = ClienteMapper.toEntity(dto);
        return clienteRepository.save(clienteCad);
    }


    public Cliente atualizarCliente(ClienteCadastroDto dto, Integer id) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente com o ID " + id + " não encontrado!"));

        clienteExistente.setNome(dto.getNome());
        clienteExistente.setCPF(dto.getCPF());
        clienteExistente.setTelefone(dto.getTelefone());
        clienteExistente.setEmail(dto.getEmail());
        clienteExistente.setEndereco(dto.getEndereco());

        return clienteRepository.save(clienteExistente);
    }



    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        for (Cliente cliente : clienteRepository.findAll()) {
            clientes.add(cliente);
        }

        if (clientes.isEmpty()) {
            throw new ClienteListaVaziaException("Lista de clientes está vazia");
        }
        return clientes;
    }

    public void excluir(Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado");
        }
        clienteRepository.deleteById(id);
    }
}
