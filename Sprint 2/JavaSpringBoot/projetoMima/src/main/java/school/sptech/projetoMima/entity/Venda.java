package school.sptech.projetoMima.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Schema(description = "Representa uma venda realizada, contendo informações como valor total, data, cliente, funcionário e itens vendidos.")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único da venda", example = "101", type = "integer", format = "int32")
    private Integer id;

    @Schema(description = "Valor total da venda", example = "250.75", type = "number", format = "double", defaultValue = "0.0")
    private Double valorTotal = 0.0;

    @CurrentTimestamp
    @Schema(description = "Data em que a venda foi registrada", example = "2024-04-15", type = "string", format = "date")
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "fkCliente")
    @Schema(description = "Cliente associado à venda")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "fkFuncionario")
    @Schema(description = "Funcionário que realizou a venda")
    private Usuario usuario;

    @OneToMany
    @JsonManagedReference
    @Schema(description = "Lista de itens incluídos na venda")
    private List<ItemVenda> itensVenda;  // <- corrigido de 'itemVenda' para 'itensVenda'

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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemVenda> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<ItemVenda> itensVenda) {
        this.itensVenda = itensVenda;
    }

    // Métodos auxiliares para manter compatibilidade
    public Usuario getFuncionario() {
        return usuario;
    }

    public void setFuncionario(Usuario usuario) {
        this.usuario = usuario;
    }
}
