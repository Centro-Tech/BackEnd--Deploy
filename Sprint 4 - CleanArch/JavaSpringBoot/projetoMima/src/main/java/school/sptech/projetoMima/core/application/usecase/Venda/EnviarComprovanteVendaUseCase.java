package school.sptech.projetoMima.core.application.usecase.Venda;

import school.sptech.projetoMima.core.adapter.Venda.ComprovanteVendaPublisherGateway;
import school.sptech.projetoMima.core.domain.Venda;

/**
 * Use case responsável por enviar comprovante de venda por email
 */
public class EnviarComprovanteVendaUseCase {

    private final ComprovanteVendaPublisherGateway comprovantePublisherGateway;

    public EnviarComprovanteVendaUseCase(ComprovanteVendaPublisherGateway comprovantePublisherGateway) {
        this.comprovantePublisherGateway = comprovantePublisherGateway;
    }

    public void executar(Venda venda) {
        // Valida se a venda tem cliente com email
        if (venda.getCliente() == null || venda.getCliente().getEmail() == null || venda.getCliente().getEmail().isBlank()) {
            // Log ou exceção silenciosa - não falha a venda por falta de email
            System.out.println("[COMPROVANTE] Cliente sem email válido para venda ID: " + venda.getId());
            return;
        }

        // Publica o comprovante na fila para processamento assíncrono
        comprovantePublisherGateway.publishComprovante(venda);
        System.out.println("[COMPROVANTE] Comprovante enviado para fila - Venda ID: " + venda.getId() + ", Cliente: " + venda.getCliente().getEmail());
    }
}
