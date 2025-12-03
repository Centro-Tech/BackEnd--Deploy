package school.sptech.projetoMima.core.application.usecase.Venda;

import school.sptech.projetoMima.core.adapter.Venda.ComprovanteVendaPublisherGateway;
import school.sptech.projetoMima.core.adapter.Venda.VendaGateway;
import school.sptech.projetoMima.core.application.command.Venda.FinalizarVendaCommand;
import school.sptech.projetoMima.core.domain.Venda;

/**
 * Use case responsável por finalizar uma venda e enviar o comprovante via RabbitMQ
 */
public class FinalizarVendaUseCase {

    private final VendaGateway vendaGateway;
    private final ComprovanteVendaPublisherGateway comprovantePublisherGateway;

    public FinalizarVendaUseCase(VendaGateway vendaGateway, 
                                 ComprovanteVendaPublisherGateway comprovantePublisherGateway) {
        this.vendaGateway = vendaGateway;
        this.comprovantePublisherGateway = comprovantePublisherGateway;
    }

    public Venda execute(FinalizarVendaCommand command) {
        // Busca a venda pelo ID
        Venda venda = vendaGateway.findById(command.vendaId());
        
        if (venda == null) {
            throw new RuntimeException("Venda não encontrada com ID: " + command.vendaId());
        }

        // Valida se a venda tem itens
        if (venda.getItensVenda() == null || venda.getItensVenda().isEmpty()) {
            throw new RuntimeException("Venda sem itens não pode ser finalizada. ID: " + command.vendaId());
        }

        // Valida se a venda tem cliente com email
        if (venda.getCliente() == null || venda.getCliente().getEmail() == null || venda.getCliente().getEmail().isBlank()) {
            System.out.println("[FINALIZAR_VENDA] Cliente sem email válido para venda ID: " + venda.getId());
            // Retorna a venda mesmo sem enviar email
            return venda;
        }

        // Envia o comprovante para o RabbitMQ
        try {
            comprovantePublisherGateway.publishComprovante(venda);
            System.out.println("[FINALIZAR_VENDA] Comprovante enviado para fila - Venda ID: " + venda.getId() + ", Cliente: " + venda.getCliente().getEmail());
        } catch (Exception e) {
            System.err.println("[FINALIZAR_VENDA][ERRO] Falha ao enviar comprovante: " + e.getMessage());
            throw new RuntimeException("Erro ao enviar comprovante da venda ID: " + command.vendaId(), e);
        }

        return venda;
    }
}
