package school.sptech.projetoMima.core.application.usecase.Venda;

import school.sptech.projetoMima.core.adapter.Venda.VendaGateway;
import school.sptech.projetoMima.core.adapter.Cliente.ClienteGateway;
import school.sptech.projetoMima.core.adapter.Usuario.UsuarioGateway;
import school.sptech.projetoMima.core.application.command.Venda.CriarVendaCommand;
import school.sptech.projetoMima.core.application.exception.Venda.CarrinhoVazioException;
import school.sptech.projetoMima.core.domain.Venda;
import school.sptech.projetoMima.core.domain.Cliente;
import school.sptech.projetoMima.core.domain.Usuario;



public class CriarVendaUseCase {

    private final VendaGateway vendaGateway;
    private final ClienteGateway clienteGateway;
    private final UsuarioGateway usuarioGateway;

    public CriarVendaUseCase(VendaGateway vendaGateway, ClienteGateway clienteGateway, UsuarioGateway usuarioGateway) {
        this.vendaGateway = vendaGateway;
        this.clienteGateway = clienteGateway;
        this.usuarioGateway = usuarioGateway;
    }

    public Venda execute(CriarVendaCommand command) {

        if (command.itensVenda() == null || command.itensVenda().isEmpty()) {
            throw new CarrinhoVazioException("O carrinho não pode estar vazio");
        }

        Cliente cliente = clienteGateway.findById(command.clienteId());
        if (cliente == null) {
            throw new RuntimeException("Cliente não encontrado");
        }

        Usuario funcionario = usuarioGateway.findById(command.funcionarioId())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        // Apenas cria a venda com os dados principais. A associação dos ItemVenda
        // persistidos no carrinho deve ser feita pelo use case de finalização
        // (FinalizarCarrinhoUseCase), que já faz a atualização dos itens existentes.
        Venda venda = new Venda();
        venda.setValorTotal(command.valorTotal());
        venda.setCliente(cliente);
        venda.setUsuario(funcionario);

        // Não setamos itens aqui para evitar criação de novos ItemVenda duplicados.
        return vendaGateway.save(venda);
    }
}
