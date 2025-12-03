package school.sptech.projetoMima.Model;

import jakarta.persistence.*;

@Entity
public class Funcionario extends Usuario {

    private String cargo;

    public Funcionario() {}

    public Funcionario(String nome, String email, String telefone, String cargo) {
        super(nome, email, telefone);
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
