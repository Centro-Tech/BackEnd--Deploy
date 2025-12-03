// Java
package school.sptech.projetoMima.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.projetoMima.dto.vendaDto.VendaRequestDto;
import school.sptech.projetoMima.dto.vendaDto.VendaResponseDto;
import school.sptech.projetoMima.dto.vendaDto.VendaMapper;
import school.sptech.projetoMima.entity.Cliente;
import school.sptech.projetoMima.entity.ItemVenda;
import school.sptech.projetoMima.entity.Venda;
import school.sptech.projetoMima.entity.item.Item;
import school.sptech.projetoMima.exception.Usuario.UsuarioNaoEncontradoException;
import school.sptech.projetoMima.exception.Venda.CarrinhoVazioException;
import school.sptech.projetoMima.exception.Venda.EstoqueInsuficienteException;
import school.sptech.projetoMima.repository.ClienteRepository;
import school.sptech.projetoMima.repository.ItemRepository;
import school.sptech.projetoMima.repository.ItemVendaRepository;
import school.sptech.projetoMima.repository.VendaRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class    VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    @Autowired
    private ItemRepository itemRepository;

    public VendaResponseDto vender(VendaRequestDto dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Cliente não encontrado"));

        List<ItemVenda> itensCarrinho = itemVendaRepository.findByClienteIdAndVendaIsNull(cliente.getId());
        if (itensCarrinho.isEmpty()) {
            throw new CarrinhoVazioException("Carrinho está vazio");
        }

        double valorTotal = itensCarrinho.stream()
                .mapToDouble(iv -> iv.getItem().getPreco() * iv.getQtdParaVender())
                .sum();

        Venda venda = VendaMapper.toEntity(dto, itensCarrinho);
        venda.setCliente(cliente);
        venda.setValorTotal(valorTotal);

        vendaRepository.save(venda);

        for (ItemVenda itemVenda : itensCarrinho) {
            Item item = itemVenda.getItem();

            if (item.getQtdEstoque() < itemVenda.getQtdParaVender()) {
                throw new EstoqueInsuficienteException("Estoque insuficiente para: " + item.getNome());
            }

            item.setQtdEstoque(item.getQtdEstoque() - itemVenda.getQtdParaVender());
            itemRepository.save(item);

            itemVenda.setVenda(venda);
            itemVendaRepository.save(itemVenda);
        }

        return VendaMapper.toResponse(venda);
    }

    @Transactional
    public void deletarItemDaVenda(Integer itemVendaId, Integer vendaId) {
        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new IllegalArgumentException("Venda não encontrada com ID: " + vendaId));

        ItemVenda itemEncontrado = venda.getItensVenda().stream()
                .filter(iv -> iv.getId().equals(itemVendaId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ItemVenda com ID " + itemVendaId + " não encontrado na venda."));

        Item item = itemEncontrado.getItem();
        item.setQtdEstoque(item.getQtdEstoque() + itemEncontrado.getQtdParaVender());

        double valorItem = item.getPreco() * itemEncontrado.getQtdParaVender();
        venda.setValorTotal(venda.getValorTotal() - valorItem);

        venda.getItensVenda().remove(itemEncontrado);

        itemRepository.save(item);
        vendaRepository.save(venda);
        itemVendaRepository.delete(itemEncontrado);
    }

    public List<Venda> filtrarPorDatas(LocalDate inicio, LocalDate fim) {
        return vendaRepository.findByDataBetween(inicio, fim);
    }

    public List<Venda> filtrarPorCliente(Cliente cliente) {
        return vendaRepository.findByCliente(cliente);
    }

    public List<Venda> filtrarPorValor(Double valorMinimo, Double valorMax) {
        return vendaRepository.findByValorTotalBetween(valorMinimo, valorMax);
    }
}