package school.sptech.projetoMima.core.application.dto.usuarioDto;

import school.sptech.projetoMima.core.domain.Usuario;

public class UsuarioMapper {

    public static Usuario fromCadastroDto(UsuarioCadastroDto dto) {
        return new Usuario(
                dto.getId(),
                dto.getNome(),
                dto.getEmail(),
                dto.getTelefone(),
                dto.getEndereco(),
                dto.getSenha(),
                dto.getCargo(),
                dto.getImagem()
        );
    }

    public static Usuario fromCriacaoDto(UsuarioCriacaoDto dto) {
        return new Usuario(
                dto.getId(),
                dto.getNome(),
                dto.getEmail(),
                dto.getTelefone(),
                dto.getEndereco(),
                dto.getSenha(),
                dto.getCargo(),
                dto.getImagem()
        );
    }

    public static Usuario fromLoginDto(UsuarioLoginDto dto) {
        return new Usuario(
                dto.getId(),
                null,
                dto.getEmail(),
                null,
                null,
                dto.getSenha(),
                null
        );
    }

    public static Usuario fromDetalhesDto(UsuarioDetalhesDto dto) {
        return new Usuario(
                dto.getId(),
                dto.getNome(),
                dto.getUsername(),
                dto.getTelefone(),
                dto.getEndereco(),
                dto.getPassword(),
                dto.getCargo()
        );
    }

    public static UsuarioResumidoDto toResumidoDto(Usuario usuario) {
        UsuarioResumidoDto dto = new UsuarioResumidoDto();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setCargo(usuario.getCargo());
        dto.setEmail(usuario.getEmail());
        dto.setImagem(usuario.getImagem());
        dto.setTelefone(usuario.getTelefone());
        dto.setEndereco(usuario.getEndereco());
        return dto;
    }

    public static UsuarioListagemDto toListagemDto(Usuario usuario) {
        UsuarioListagemDto dto = new UsuarioListagemDto();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setCargo(usuario.getCargo());
        dto.setImagem(usuario.getImagem());
        return dto;
    }

    public static UsuarioListarDto toListarDto(Usuario usuario) {
        UsuarioListarDto dto = new UsuarioListarDto();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setSenha(usuario.getSenha());
        return dto;
    }

    public static UsuarioTokenDto toTokenDto(Usuario usuario, String token) {
        UsuarioTokenDto dto = new UsuarioTokenDto();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setToken(token);
        return dto;
    }

    public static UsuarioCadastroResponseDto toCadastroResponseDto(Usuario usuario, String senhaProvisoria) {
        UsuarioCadastroResponseDto dto = new UsuarioCadastroResponseDto();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setCargo(usuario.getCargo());
        dto.setImagem(usuario.getImagem());
        dto.setSenhaProvisoria(senhaProvisoria);
        return dto;
    }
}
