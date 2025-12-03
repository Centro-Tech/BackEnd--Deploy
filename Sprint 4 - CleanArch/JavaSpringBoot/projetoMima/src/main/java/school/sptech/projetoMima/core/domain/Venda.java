package school.sptech.projetoMima.core.domain;

import java.time.LocalDateTime;
import java.util.List;
public class Venda {

    private Integer id;

    private Double valorTotal = 0.0;

    private LocalDateTime data;

    private Cliente cliente;

    private Usuario usuario;

    private Integer usuarioId;

    private List<ItemVenda> itensVenda;

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

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Usuario getFuncionario() {
        return usuario;
    }

    public void setFuncionario(Usuario usuario) {
        this.usuario = usuario;
    }
}
