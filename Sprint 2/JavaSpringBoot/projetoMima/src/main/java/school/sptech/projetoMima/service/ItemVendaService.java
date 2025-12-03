package school.sptech.projetoMima.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.projetoMima.dto.itemDto.ItemVendaRequestDto;
import school.sptech.projetoMima.entity.Cliente;
import school.sptech.projetoMima.entity.ItemVenda;
import school.sptech.projetoMima.entity.Usuario;
import school.sptech.projetoMima.entity.Venda;
import school.sptech.projetoMima.entity.item.Item;
import school.sptech.projetoMima.exception.Venda.CarrinhoVazioException;
import school.sptech.projetoMima.repository.*;

import java.util.List;

@Service
public class ItemVendaService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    public ItemVenda adicionarAoCarrinho(ItemVendaRequestDto dto) {
        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Usuario funcionario = usuarioRepository.findById(dto.getFuncionarioId())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setItem(item);
        itemVenda.setQtdParaVender(dto.getQtdParaVender());
        itemVenda.setCliente(cliente);
        itemVenda.setFuncionario(funcionario);
        itemVenda.setFornecedor(item.getFornecedor());
        itemVenda.setVenda(null);

        return itemVendaRepository.save(itemVenda);
    }

    public List<ItemVenda> listarCarrinho(Integer clienteId) {
        List<ItemVenda> carrinho = itemVendaRepository.findByClienteIdAndVendaIsNull(clienteId);
        if (carrinho.isEmpty()) {
            throw new CarrinhoVazioException("O carrinho está vazio.");
        }
        return carrinho;
    }

    @Autowired
    private VendaRepository vendaRepository;

    public void finalizarCarrinho(Integer clienteId, Integer vendaId) {
        List<ItemVenda> carrinho = itemVendaRepository.buscarCarrinhoParaFinalizar(clienteId);

        if (carrinho.isEmpty()) {
            throw new CarrinhoVazioException("Carrinho está vazio.");
        }

        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        for (ItemVenda item : carrinho) {
            item.setVenda(venda);
            itemVendaRepository.save(item);
        }
    }


}
