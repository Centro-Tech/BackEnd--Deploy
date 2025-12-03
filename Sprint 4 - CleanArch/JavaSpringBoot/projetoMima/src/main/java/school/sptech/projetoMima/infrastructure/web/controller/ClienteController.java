package school.sptech.projetoMima.infrastructure.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.projetoMima.core.application.command.Cliente.AtualizarClienteCommand;
import school.sptech.projetoMima.core.application.command.Cliente.CadastrarClienteCommand;
import school.sptech.projetoMima.core.application.command.Cliente.ExcluirClienteCommand;
import school.sptech.projetoMima.core.application.command.Cliente.BuscarClientePorIdCommand;
import school.sptech.projetoMima.core.application.usecase.Cliente.*;
import school.sptech.projetoMima.core.application.dto.clienteDto.ClienteListagemDto;
import school.sptech.projetoMima.core.application.dto.clienteDto.ClienteMapper;
import school.sptech.projetoMima.core.application.dto.clienteDto.ClienteResumidoDto;
import school.sptech.projetoMima.core.domain.Cliente;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Cliente", description = "Operações relacionadas aos clientes")
public class ClienteController {

    private final CadastrarClienteUseCase cadastrarClienteUseCase;
    private final AtualizarClienteUseCase atualizarClienteUseCase;
    private final ExcluirClienteUseCase excluirClienteUseCase;
    private final BuscarClientePorIdUseCase buscarClientePorIdUseCase;
    private final ListarClientesUseCase listarClientesUseCase;

    public ClienteController(CadastrarClienteUseCase cadastrarClienteUseCase,
                             AtualizarClienteUseCase atualizarClienteUseCase,
                             ExcluirClienteUseCase excluirClienteUseCase,
                             BuscarClientePorIdUseCase buscarClientePorIdUseCase,
                             ListarClientesUseCase listarClientesUseCase) {
        this.cadastrarClienteUseCase = cadastrarClienteUseCase;
        this.atualizarClienteUseCase = atualizarClienteUseCase;
        this.excluirClienteUseCase = excluirClienteUseCase;
        this.buscarClientePorIdUseCase = buscarClientePorIdUseCase;
        this.listarClientesUseCase = listarClientesUseCase;
    }

    @Operation(summary = "Buscar cliente por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResumidoDto> buscarPorId(@PathVariable Integer id) {
        Cliente cliente = buscarClientePorIdUseCase.execute(new BuscarClientePorIdCommand(id));
        return ResponseEntity.ok(ClienteMapper.toResumidoDto(cliente));
    }

    @Operation(summary = "Listar todos os clientes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clientes listados com sucesso"),
        @ApiResponse(responseCode = "204", description = "Nenhum cliente encontrado")
    })
    @GetMapping
    public ResponseEntity<Page<ClienteListagemDto>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Cliente> clientes = listarClientesUseCase.execute(pageable);
        Page<ClienteListagemDto> response = clientes.map(ClienteMapper::toList);
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Cadastrar novo cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos para cadastro"),
        @ApiResponse(responseCode = "409", description = "Cliente já existente")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClienteResumidoDto> cadastrar(@RequestBody CadastrarClienteCommand command) {
        Cliente novoCliente = cadastrarClienteUseCase.execute(command);
        return ResponseEntity.status(201).body(ClienteMapper.toResumidoDto(novoCliente));
    }

    @Operation(summary = "Atualizar cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResumidoDto> atualizar(@PathVariable Integer id, @RequestBody AtualizarClienteCommand command) {
        AtualizarClienteCommand commandComId = new AtualizarClienteCommand(
                id,
                command.nome(),
                command.email(),
                command.cpf(),
                command.telefone(),
                command.endereco()
        );

        Cliente clienteAtualizado = atualizarClienteUseCase.execute(commandComId);
        return ResponseEntity.ok(ClienteMapper.toResumidoDto(clienteAtualizado));
    }

    @Operation(summary = "Excluir cliente por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado para exclusão")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        excluirClienteUseCase.execute(new ExcluirClienteCommand(id));
        return ResponseEntity.noContent().build();
    }
}
