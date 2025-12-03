package school.sptech.projetoMima.infrastructure.persistance.UsuarioPersistance;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
@Schema(description = "Classe base para os usuários, contendo nome, CPF, email, telefone e endereço.")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do usuário", example = "1", type = "integer", format = "int32")
    private Integer id;

    @NotBlank
    @Size(min = 3, max = 100)
    @Schema(description = "Nome completo do usuário", example = "João da Silva", minLength = 3, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED)
    private String nome;

    @NotBlank
    @Size(min = 5, max = 100)
    @Schema(description = "Endereço de email do usuário", example = "joao@email.com", minLength = 5, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank
    @Size(min = 8, max = 20)
    @Schema(description = "Número de telefone do usuário (apenas dígitos)", example = "11987654321", minLength = 8, maxLength = 20, requiredMode = Schema.RequiredMode.REQUIRED)
    private String telefone;

    @NotBlank
    @Size(min = 5, max = 255)
    @Schema(description = "Endereço físico do usuário", example = "Rua das Flores, 123 - Centro", minLength = 5, maxLength = 255, requiredMode = Schema.RequiredMode.REQUIRED)
    private String endereco;

    @NotBlank
    @Size(min = 5, max = 255)
    @Schema(description = "Senha de acesso do funcionário", example = "*******", minLength = 5, maxLength = 255, requiredMode = Schema.RequiredMode.REQUIRED)
    private String senha;


    @NotBlank(message = "O cargo é obrigatório")
    @Size(min = 2, max = 50, message = "O cargo deve ter entre 2 e 50 caracteres")
    @Schema(description = "Cargo do funcionário dentro da empresa, como por exemplo 'Atendente', 'Gerente de vendas' ou 'Estoquista'", example = "Gerente de vendas", type = "string", minLength = 2, maxLength = 50, requiredMode = Schema.RequiredMode.REQUIRED)
    private String cargo;

    @Size(max = 500)
    @Schema(description = "URL ou caminho da imagem de perfil do usuário", example = "https://exemplo.com/imagem.jpg", maxLength = 500)
    private String imagem;

    @Column(name = "recoveryToken")
    private String recoveryToken;

    @Column(name = "recoveryTokenExpiry")
    private LocalDateTime recoveryTokenExpiry;

    public UsuarioEntity() {

    }

    public UsuarioEntity(String nome, String cpf, String email, String telefone, String endereco, String senha, String cargo) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.senha = senha;
        this.cargo = cargo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getRecoveryToken() {
        return recoveryToken;
    }

    public void setRecoveryToken(String recoveryToken) {
        this.recoveryToken = recoveryToken;
    }

    public LocalDateTime getRecoveryTokenExpiry() {
        return recoveryTokenExpiry;
    }

    public void setRecoveryTokenExpiry(LocalDateTime recoveryTokenExpiry) {
        this.recoveryTokenExpiry = recoveryTokenExpiry;
    }
}
