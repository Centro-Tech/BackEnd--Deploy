package school.sptech.projetoMima.core.adapter.Usuario;

public interface CryptoGateway {
    String encode(String raw);
    boolean matches(String raw, String encoded);
}
