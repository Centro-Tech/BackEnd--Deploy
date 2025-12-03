package school.sptech.projetoMima.infrastructure.persistance.VendaPersistance;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CurrentTimestamp;
import school.sptech.projetoMima.infrastructure.persistance.ClientePersistance.Enitity.ClienteEntity;
import school.sptech.projetoMima.infrastructure.persistance.UsuarioPersistance.UsuarioEntity;
import school.sptech.projetoMima.infrastructure.persistance.ItemVendaPersistance.ItemVendaEntity;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "venda")
@Schema(description = "Representa uma venda realizada, contendo informações como valor total, data, cliente, funcionário e itens vendidos.")
public class VendaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único da venda", example = "101", type = "integer", format = "int32")
    private Integer id;

    @Schema(description = "Valor total da venda", example = "250.75", type = "number", format = "double", defaultValue = "0.0")
    private Double valorTotal = 0.0;

    @CurrentTimestamp
    @Schema(description = "Data e hora em que a venda foi registrada", example = "2024-04-15T10:30:00", type = "string", format = "date-time")
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "fkCliente")
    @Schema(description = "Cliente associado à venda")
    private ClienteEntity cliente;

    @ManyToOne
    @JoinColumn(name = "fkFuncionario")
    @Schema(description = "Funcionário que realizou a venda")
    private UsuarioEntity usuario;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @Schema(description = "Lista de itens incluídos na venda")
    private List<ItemVendaEntity> itensVenda;  // <- corrigido de 'itemVenda' para 'itensVenda'

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public List<ItemVendaEntity> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<ItemVendaEntity> itensVenda) {
        this.itensVenda = itensVenda;
    }

}
