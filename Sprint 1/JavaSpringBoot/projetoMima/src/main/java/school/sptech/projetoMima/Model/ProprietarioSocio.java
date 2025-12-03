package school.sptech.projetoMima.Model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class ProprietarioSocio extends Usuario {

    private String cpf;
    private LocalDate dataDeRegistro;
    private String papel;

    public ProprietarioSocio() {}

    public ProprietarioSocio(String nome, String email, String telefone, String cpf, LocalDate dataDeRegistro, String papel) {
        super(nome, email, telefone);
        this.cpf = cpf;
        this.dataDeRegistro = dataDeRegistro;
        this.papel = papel;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataDeRegistro() {
        return dataDeRegistro;
    }

    public void setDataDeRegistro(LocalDate dataDeRegistro) {
        this.dataDeRegistro = dataDeRegistro;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }
}
