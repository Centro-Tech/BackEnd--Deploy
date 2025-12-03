package school.sptech.projetoMima.dto.usuarioDto;

import school.sptech.projetoMima.entity.Usuario;

public class UsuarioMapper {


    public static Usuario toEntity(UsuarioCadastroDto funcionario) {
        Usuario usuarioCadastro = new Usuario();
        usuarioCadastro.setNome(funcionario.getNome());
        usuarioCadastro.setEmail(funcionario.getEmail());
        usuarioCadastro.setTelefone(funcionario.getTelefone());
        usuarioCadastro.setSenha(funcionario.getSenha());
        usuarioCadastro.setEndereco(funcionario.getEndereco());
        return usuarioCadastro;
    }

    public static UsuarioResumidoDto toResumidoDto(Usuario usuario) {
        UsuarioResumidoDto usuarioResumidoDto = new UsuarioResumidoDto();
        usuarioResumidoDto.setCargo(usuario.getCargo());
        usuarioResumidoDto.setNome(usuario.getNome());

        return usuarioResumidoDto;
    }

    public static UsuarioListagemDto toListagemDto(Usuario usuario) {
        UsuarioListagemDto listagemDto = new UsuarioListagemDto();
        listagemDto.setNome(usuario.getNome());
        listagemDto.setEmail(usuario.getEmail());
        return listagemDto;
    }

    public static Usuario of(UsuarioCriacaoDto usuarioCriacaoDto) {
        Usuario usuario = new Usuario();

        usuario.setEmail(usuarioCriacaoDto.getEmail());
        usuario.setNome(usuarioCriacaoDto.getNome());
        usuario.setSenha(usuarioCriacaoDto.getSenha());
        return usuario;
    }

    public static Usuario of(UsuarioLoginDto usuarioLoginDto) {
        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioLoginDto.getEmail());
        usuario.setSenha(usuarioLoginDto.getSenha());
        return usuario;
    }

    public static UsuarioTokenDto of(Usuario usuario, String token) {
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();
        usuarioTokenDto.setId(usuario.getId());
        usuarioTokenDto.setEmail(usuario.getEmail());
        usuarioTokenDto.setNome(usuario.getNome());
        usuarioTokenDto.setToken(token);

        return usuarioTokenDto;
    }

    public static UsuarioListarDto of(Usuario usuario) {
        UsuarioListarDto usuarioListarDto = new UsuarioListarDto();
        usuarioListarDto.setId(usuario.getId());
        usuarioListarDto.setEmail(usuario.getEmail());
        usuarioListarDto.setNome(usuario.getNome());

        return usuarioListarDto;
    }

    public static Usuario toUsuarioFromUsuarioDetalhes(UsuarioDetalhesDto request) {
        Usuario response = new Usuario();
        response.setNome(request.getNome());
        response.setEmail(request.getUsername());
        response.setSenha(request.getPassword());
        return response;
    }


}
