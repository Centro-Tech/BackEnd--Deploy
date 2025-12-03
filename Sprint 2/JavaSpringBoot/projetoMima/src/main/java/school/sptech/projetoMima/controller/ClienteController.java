package school.sptech.projetoMima.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.projetoMima.dto.clienteDto.ClienteCadastroDto;
import school.sptech.projetoMima.dto.clienteDto.ClienteListagemDto;
import school.sptech.projetoMima.dto.clienteDto.ClienteMapper;
import school.sptech.projetoMima.dto.clienteDto.ClienteResumidoDto;
import school.sptech.projetoMima.entity.Cliente;
import school.sptech.projetoMima.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Buscar cliente por ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = @Content(schema = @Schema(implementation = ClienteResumidoDto.class))), @ApiResponse(responseCode = "404", description = "Cliente não encontrado") })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResumidoDto> buscarPorId(@PathVariable Integer id) {
        Cliente cliente = clienteService.findClienteById(id);
        ClienteResumidoDto dto = ClienteMapper.toResumidoDto(cliente);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Listar todos os clientes")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Clientes listados com sucesso", content = @Content(schema = @Schema(implementation = ClienteListagemDto.class))), @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado") })
    @GetMapping
    public ResponseEntity<List<ClienteListagemDto>> listar() {
        List<Cliente> clientes = clienteService.listarClientes();
        List<ClienteListagemDto> response = clientes.stream().map(ClienteMapper::toList).toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Inserir novo cliente")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso", content = @Content(schema = @Schema(implementation = ClienteCadastroDto.class))), @ApiResponse(responseCode = "400", description = "Dados inválidos") })
    @PostMapping
    public ResponseEntity<ClienteResumidoDto> cadastrar(@RequestBody ClienteCadastroDto dto) {
        Cliente novoCliente = clienteService.cadastrarCliente(dto);
        return ResponseEntity.status(201).body(ClienteMapper.toResumidoDto(novoCliente));
    }

    @Operation(summary = "Atualizar cliente")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso", content = @Content(schema = @Schema(implementation = ClienteCadastroDto.class))), @ApiResponse(responseCode = "404", description = "Cliente não encontrado") })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResumidoDto> atualizar(@RequestBody ClienteCadastroDto dto, @PathVariable Integer id) {
        Cliente clienteAtualizado = clienteService.atualizarCliente(dto, id);
        ClienteResumidoDto dtoResposta = ClienteMapper.toResumidoDto(clienteAtualizado);
        return ResponseEntity.ok(dtoResposta);
    }

    @Operation(summary = "Deletar cliente por ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"), @ApiResponse(responseCode = "404", description = "Cliente não encontrado") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        clienteService.excluir(id);
        return ResponseEntity.status(204).build();
    }
}
