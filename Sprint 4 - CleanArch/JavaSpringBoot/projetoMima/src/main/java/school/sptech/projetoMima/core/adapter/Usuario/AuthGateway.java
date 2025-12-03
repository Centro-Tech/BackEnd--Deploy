package school.sptech.projetoMima.core.adapter.Usuario;

public interface AuthGateway {
    Object authenticate(String email, String senha);
}