package school.sptech.projetoMima.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import school.sptech.projetoMima.entity.item.Item;

@Entity
@Schema(description = "Representa um item incluído em uma venda, contendo a quantidade, o item vendido, e as associações com fornecedor, venda, cliente e funcionário.")
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do item da venda", example = "501", type = "integer", format = "int32")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fkItem")
    @Schema(description = "Item que está sendo vendido neste registro")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "fkItemFornecedor")
    @Schema(description = "Fornecedor responsável por fornecer o item vendido")
    private Fornecedor fornecedor;

    @ManyToOne
    @JoinColumn(name = "fkVenda")
    @JsonBackReference
    @Schema(description = "Venda à qual este item pertence")
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "fkVendaCliente")
    @Schema(description = "Cliente que realizou a compra deste item")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "fkVendaFuncionario")
    @Schema(description = "Funcionário responsável pela venda deste item")
    private Usuario funcionario;

    @Schema(description = "Quantidade do item incluído na venda", example = "3", type = "integer", format = "int32")
    private Integer qtdParaVender;

    public Integer getQtdParaVender() {
        return qtdParaVender;
    }

    public void setQtdParaVender(Integer qtdParaVender) {
        this.qtdParaVender = qtdParaVender;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Usuario funcionario) {
        this.funcionario = funcionario;
    }
}
