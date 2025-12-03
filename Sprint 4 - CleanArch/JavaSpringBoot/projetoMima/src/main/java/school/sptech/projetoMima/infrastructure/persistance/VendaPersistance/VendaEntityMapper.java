package school.sptech.projetoMima.infrastructure.persistance.VendaPersistance;

import school.sptech.projetoMima.core.domain.Venda;
import school.sptech.projetoMima.core.domain.Cliente;
import school.sptech.projetoMima.core.domain.Usuario;
import school.sptech.projetoMima.core.domain.ItemVenda;
import school.sptech.projetoMima.infrastructure.persistance.ClientePersistance.Enitity.ClienteEntity;
import school.sptech.projetoMima.infrastructure.persistance.UsuarioPersistance.UsuarioEntity;
import school.sptech.projetoMima.infrastructure.persistance.ItemVendaPersistance.ItemVendaEntityMapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class VendaEntityMapper {

    public VendaEntity toEntity(Venda venda) {
        if (venda == null) return null;

        VendaEntity vendaEntity = new VendaEntity();
        vendaEntity.setId(venda.getId());
        vendaEntity.setValorTotal(venda.getValorTotal());
        vendaEntity.setData(venda.getData());

        if (venda.getCliente() != null) {
            ClienteEntity clienteEntity = new ClienteEntity();
            clienteEntity.setIdCliente(venda.getCliente().getId());
            clienteEntity.setNome(venda.getCliente().getNome());
            clienteEntity.setCPF(venda.getCliente().getCPF());
            clienteEntity.setTelefone(venda.getCliente().getTelefone());
            clienteEntity.setEmail(venda.getCliente().getEmail());
            vendaEntity.setCliente(clienteEntity);
        }

        if (venda.getUsuario() != null) {
            UsuarioEntity usuarioEntity = new UsuarioEntity();
            usuarioEntity.setId(venda.getUsuario().getId());
            usuarioEntity.setNome(venda.getUsuario().getNome());
            usuarioEntity.setEmail(venda.getUsuario().getEmail());
            usuarioEntity.setTelefone(venda.getUsuario().getTelefone());
            usuarioEntity.setEndereco(venda.getUsuario().getEndereco());
            usuarioEntity.setSenha(venda.getUsuario().getSenha());
            usuarioEntity.setCargo(venda.getUsuario().getCargo());
            vendaEntity.setUsuario(usuarioEntity);
        }

        if (venda.getItensVenda() != null) {
            vendaEntity.setItensVenda(venda.getItensVenda().stream()
                    .map(ItemVendaEntityMapper::toEntity)
                    .collect(Collectors.toList()));
        } else {
            vendaEntity.setItensVenda(new ArrayList<>());
        }
        return vendaEntity;
    }

    public Venda toDomain(VendaEntity entity) {
        if (entity == null) return null;

        Venda venda = new Venda();
        venda.setId(entity.getId());
        venda.setValorTotal(entity.getValorTotal());
        venda.setData(entity.getData());

        if (entity.getCliente() != null) {
            Cliente cliente = new Cliente();
            cliente.setId(entity.getCliente().getIdCliente());
            cliente.setNome(entity.getCliente().getNome());
            cliente.setCPF(entity.getCliente().getCPF());
            cliente.setTelefone(entity.getCliente().getTelefone());
            cliente.setEmail(entity.getCliente().getEmail());
            venda.setCliente(cliente);
        }

        if (entity.getUsuario() != null) {
            Usuario usuario = new Usuario(
                entity.getUsuario().getNome(),
                entity.getUsuario().getEmail(),
                entity.getUsuario().getTelefone(),
                entity.getUsuario().getEndereco(),
                entity.getUsuario().getSenha(),
                entity.getUsuario().getCargo()
            );
            usuario.setId(entity.getUsuario().getId());
            venda.setUsuario(usuario);
        }

        if (entity.getItensVenda() != null) {
            venda.setItensVenda(entity.getItensVenda().stream()
                    .map(ItemVendaEntityMapper::toDomain)
                    .collect(Collectors.toList()));
        } else {
            venda.setItensVenda(new ArrayList<>());
        }
        return venda;
    }
}
