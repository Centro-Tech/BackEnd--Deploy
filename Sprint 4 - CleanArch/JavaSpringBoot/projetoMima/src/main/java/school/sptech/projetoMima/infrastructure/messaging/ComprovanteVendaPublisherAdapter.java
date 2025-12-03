package school.sptech.projetoMima.infrastructure.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import school.sptech.projetoMima.core.adapter.Venda.ComprovanteVendaPublisherGateway;
import school.sptech.projetoMima.core.domain.ItemVenda;
import school.sptech.projetoMima.core.domain.Venda;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ComprovanteVendaPublisherAdapter implements ComprovanteVendaPublisherGateway {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public ComprovanteVendaPublisherAdapter(RabbitTemplate rabbitTemplate,
                                           @Value("${app.rabbitmq.comprovante-venda.exchange}") String exchange,
                                           @Value("${app.rabbitmq.comprovante-venda.routing-key}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    @Override
    public void publishComprovante(Venda venda) {
        Map<String, Object> payload = new HashMap<>();

        // Dados da venda
        payload.put("vendaId", venda.getId());
        payload.put("valorTotal", venda.getValorTotal());
        payload.put("dataVenda", venda.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        // Dados do cliente
        if (venda.getCliente() != null) {
            Map<String, Object> clienteData = new HashMap<>();
            clienteData.put("nome", venda.getCliente().getNome());
            clienteData.put("email", venda.getCliente().getEmail());
            clienteData.put("cpf", venda.getCliente().getCPF());
            payload.put("cliente", clienteData);
        }

        // Dados do funcionário
        if (venda.getUsuario() != null) {
            Map<String, Object> funcionarioData = new HashMap<>();
            funcionarioData.put("nome", venda.getUsuario().getNome());
            funcionarioData.put("cargo", venda.getUsuario().getCargo());
            payload.put("funcionario", funcionarioData);
        }

        // Itens da venda
        if (venda.getItensVenda() != null && !venda.getItensVenda().isEmpty()) {
            List<Map<String, Object>> itensData = venda.getItensVenda().stream()
                .map(this::mapItemVenda)
                .collect(Collectors.toList());
            payload.put("itens", itensData);
        }

        rabbitTemplate.convertAndSend(exchange, routingKey, payload);
    }

    private Map<String, Object> mapItemVenda(ItemVenda itemVenda) {
        Map<String, Object> itemData = new HashMap<>();

        if (itemVenda.getItem() != null) {
            itemData.put("nome", itemVenda.getItem().getNome());
            itemData.put("codigo", itemVenda.getItem().getCodigo());
            itemData.put("precoUnitario", itemVenda.getItem().getPreco());

            // Categoria, cor, tamanho, material se disponíveis
            if (itemVenda.getItem().getCategoria() != null) {
                itemData.put("categoria", itemVenda.getItem().getCategoria().getNome());
            }
            if (itemVenda.getItem().getCor() != null) {
                itemData.put("cor", itemVenda.getItem().getCor().getNome());
            }
            if (itemVenda.getItem().getTamanho() != null) {
                itemData.put("tamanho", itemVenda.getItem().getTamanho().getNome());
            }
            if (itemVenda.getItem().getMaterial() != null) {
                itemData.put("material", itemVenda.getItem().getMaterial().getNome());
            }
        }

        itemData.put("quantidade", itemVenda.getQtdParaVender());

        // Calcula subtotal (preço * quantidade)
        if (itemVenda.getItem() != null && itemVenda.getQtdParaVender() != null) {
            Double subtotal = itemVenda.getItem().getPreco() * itemVenda.getQtdParaVender();
            itemData.put("subtotal", subtotal);
        }

        return itemData;
    }
}
