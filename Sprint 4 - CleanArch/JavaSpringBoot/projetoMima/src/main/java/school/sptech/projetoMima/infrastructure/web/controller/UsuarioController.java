package school.sptech.projetoMima.infrastructure.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import school.sptech.projetoMima.core.application.command.Usuario.*;
import school.sptech.projetoMima.core.application.dto.usuarioDto.*;
import school.sptech.projetoMima.core.application.usecase.Usuario.*;
import school.sptech.projetoMima.core.domain.Usuario;
import school.sptech.projetoMima.infrastructure.service.S3UploadService;
import school.sptech.projetoMima.infrastructure.config.GerenciadorTokenJwt;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final AtualizarUsuarioUseCase atualizarUsuarioUseCase;
    private final ListarUsuariosUseCase listarUsuariosUseCase;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final ExcluirUsuarioUseCase excluirUsuarioUseCase;
    private final AutenticarUsuarioUseCase autenticarUsuarioUseCase;
    private final TrocarSenhaUseCase trocarSenhaUseCase;
    private final S3UploadService s3UploadService;
    private final SolicitarRecuperacaoSenhaUseCase solicitarRecuperacaoSenhaUseCase;
    private final RedefinirSenhaUseCase redefinirSenhaUseCase;
    private final AutenticarPorTokenRecuperacaoUseCase autenticarPorTokenRecuperacaoUseCase;
    private final RedefinirSenhaAutenticadoUseCase redefinirSenhaAutenticadoUseCase;
    private final GerenciadorTokenJwt jwtTokenManager;

    public UsuarioController(CriarUsuarioUseCase criarUsuarioUseCase,
                             AtualizarUsuarioUseCase atualizarUsuarioUseCase,
                             ListarUsuariosUseCase listarUsuariosUseCase,
                             BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
                             ExcluirUsuarioUseCase excluirUsuarioUseCase,
                             AutenticarUsuarioUseCase autenticarUsuarioUseCase,
                             TrocarSenhaUseCase trocarSenhaUseCase,
                             S3UploadService s3UploadService,
                             SolicitarRecuperacaoSenhaUseCase solicitarRecuperacaoSenhaUseCase,
                             RedefinirSenhaUseCase redefinirSenhaUseCase,
                             AutenticarPorTokenRecuperacaoUseCase autenticarPorTokenRecuperacaoUseCase,
                             RedefinirSenhaAutenticadoUseCase redefinirSenhaAutenticadoUseCase,
                             GerenciadorTokenJwt jwtTokenManager) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.atualizarUsuarioUseCase = atualizarUsuarioUseCase;
        this.listarUsuariosUseCase = listarUsuariosUseCase;
        this.buscarUsuarioPorIdUseCase = buscarUsuarioPorIdUseCase;
        this.excluirUsuarioUseCase = excluirUsuarioUseCase;
        this.autenticarUsuarioUseCase = autenticarUsuarioUseCase;
        this.trocarSenhaUseCase = trocarSenhaUseCase;
        this.s3UploadService = s3UploadService;
        this.solicitarRecuperacaoSenhaUseCase = solicitarRecuperacaoSenhaUseCase;
        this.redefinirSenhaUseCase = redefinirSenhaUseCase;
        this.autenticarPorTokenRecuperacaoUseCase = autenticarPorTokenRecuperacaoUseCase;
        this.redefinirSenhaAutenticadoUseCase = redefinirSenhaAutenticadoUseCase;
        this.jwtTokenManager = jwtTokenManager;
    }

    @Operation(summary = "Buscar usuário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = @Content(schema = @Schema(implementation = UsuarioResumidoDto.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResumidoDto> buscarPorId(@PathVariable Integer id) {
        Usuario usuario = buscarUsuarioPorIdUseCase.executar(id);
        return ResponseEntity.ok(UsuarioMapper.toResumidoDto(usuario));
    }

    @Operation(summary = "Listar todos os usuários")
    @GetMapping
    public ResponseEntity<List<UsuarioResumidoDto>> listar() {
        List<Usuario> usuarios = listarUsuariosUseCase.executar();
        List<UsuarioResumidoDto> response = usuarios.stream()
                .map(UsuarioMapper::toResumidoDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Cadastrar funcionário com senha padrão")
    @PostMapping("/funcionarios")
    public ResponseEntity<UsuarioCadastroResponseDto> cadastrarFuncionario(@RequestBody UsuarioCadastroDto dto) {
        Usuario usuarioDomain = UsuarioMapper.fromCadastroDto(dto);

        CriarUsuarioCommand cmd = new CriarUsuarioCommand(
                usuarioDomain.getNome(),
                usuarioDomain.getEmail(),
                null, // senha padrão
                usuarioDomain.getTelefone(),
                usuarioDomain.getCargo(),
                usuarioDomain.getEndereco(),
                usuarioDomain.getImagem()
        );

        CriarUsuarioUseCase.Resultado resultado = criarUsuarioUseCase.executarComSenhaProvisoria(cmd, true);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UsuarioMapper.toCadastroResponseDto(resultado.usuario(), resultado.senhaProvisoria()));
    }

    @Operation(summary = "Cadastrar funcionário com imagem")
    @PostMapping(value = "/funcionarios/com-imagem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UsuarioCadastroResponseDto> cadastrarFuncionarioComImagem(@RequestParam("nome") String nome,
                                                                                    @RequestParam("email") String email,
                                                                                    @RequestParam("telefone") String telefone,
                                                                                    @RequestParam("cargo") String cargo,
                                                                                    @RequestParam("endereco") String endereco,
                                                                                    @RequestParam(value = "imagem", required = false) MultipartFile imagemFile) {
        String imagemUrl = null;

        // Se uma imagem foi enviada, fazer upload para S3
        if (imagemFile != null && !imagemFile.isEmpty()) {
            try {
                String nomeArquivo = "funcionario_" + System.currentTimeMillis() + "_" + imagemFile.getOriginalFilename();
                imagemUrl = s3UploadService.uploadFile(nomeArquivo, imagemFile.getBytes());
            } catch (Exception e) {
                throw new RuntimeException("Erro ao fazer upload da imagem: " + e.getMessage());
            }
        }

        CriarUsuarioCommand cmd = new CriarUsuarioCommand(
                nome,
                email,
                null, // senha padrão
                telefone,
                cargo,
                endereco,
                imagemUrl
        );

        CriarUsuarioUseCase.Resultado resultado = criarUsuarioUseCase.executarComSenhaProvisoria(cmd, true);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UsuarioMapper.toCadastroResponseDto(resultado.usuario(), resultado.senhaProvisoria()));
    }

    @Operation(summary = "Criar novo usuário com senha informada")
    @SecurityRequirement(name = "Bearer")
    @PostMapping("/criar")
    public ResponseEntity<Void> criar(@RequestBody @Valid UsuarioCriacaoDto dto) {
        Usuario usuarioDomain = UsuarioMapper.fromCriacaoDto(dto);

        CriarUsuarioCommand cmd = new CriarUsuarioCommand(
                usuarioDomain.getNome(),
                usuarioDomain.getEmail(),
                usuarioDomain.getSenha(),
                usuarioDomain.getTelefone(),
                usuarioDomain.getCargo(),
                usuarioDomain.getEndereco(),
                usuarioDomain.getImagem()
        );

        criarUsuarioUseCase.executar(cmd, false);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Atualizar usuário")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UsuarioResumidoDto> atualizar(@RequestParam("nome") String nome,
                                                        @RequestParam("email") String email,
                                                        @RequestParam("telefone") String telefone,
                                                        @RequestParam("cargo") String cargo,
                                                        @RequestParam("endereco") String endereco,
                                                        @RequestParam(value = "imagem", required = false) MultipartFile imagemFile,
                                                        @PathVariable Integer id) {
        String imagemUrl = null;

        // Se uma imagem foi enviada, fazer upload para S3
        if (imagemFile != null && !imagemFile.isEmpty()) {
            try {
                String nomeArquivo = "usuario_" + id + "_" + System.currentTimeMillis() + "_" + imagemFile.getOriginalFilename();
                imagemUrl = s3UploadService.uploadFile(nomeArquivo, imagemFile.getBytes());
            } catch (Exception e) {
                throw new RuntimeException("Erro ao fazer upload da imagem: " + e.getMessage());
            }
        }

        AtualizarUsuarioCommand cmd = new AtualizarUsuarioCommand(
                id,
                nome,
                email,
                telefone,
                cargo,
                endereco,
                imagemUrl
        );

        Usuario atualizado = atualizarUsuarioUseCase.executar(cmd);
        return ResponseEntity.ok(UsuarioMapper.toResumidoDto(atualizado));
    }

    @Operation(summary = "Deletar usuário por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        excluirUsuarioUseCase.executar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Realizar login de usuário")
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto dto) {
        Usuario usuarioDomain = UsuarioMapper.fromLoginDto(dto);
        LoginUsuarioCommand cmd = new LoginUsuarioCommand(usuarioDomain.getEmail(), usuarioDomain.getSenha());

        AutenticarUsuarioUseCase.Resultado res = autenticarUsuarioUseCase.executar(cmd);
        UsuarioTokenDto tokenDto = UsuarioMapper.toTokenDto(res.usuario(), res.token());

        return ResponseEntity.ok(tokenDto);
    }

    @Operation(summary = "Trocar a senha do usuário autenticado")
    @PutMapping("/trocar-senha")
    public ResponseEntity<String> trocarSenha(@RequestBody TrocarSenhaDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUsuario = (authentication != null) ? authentication.getName() : "anonymousUser";

        if ("anonymousUser".equals(emailUsuario)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
        }
        TrocarSenhaCommand cmd = new TrocarSenhaCommand(
                emailUsuario,
                dto.getSenhaAtual(),
                dto.getNovaSenha()
        );
        trocarSenhaUseCase.executar(cmd);
        return ResponseEntity.ok("Senha alterada com sucesso");
    }

    @Operation(summary = "Solicitar recuperação de senha (gera token e envia instruções por fila)")
    @PostMapping("/recuperar-senha")
    public ResponseEntity<String> solicitarRecuperacaoSenha(@RequestBody SolicitarRecuperacaoSenhaDto dto) {
        solicitarRecuperacaoSenhaUseCase.executar(dto.getEmail());
        return ResponseEntity.ok("Se o email existir, as instruções foram enviadas");
    }

    @Operation(summary = "Redefinir senha: se enviar token (JWT ou recuperação) identifica usuário automaticamente e não precisa estar logado")
    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(@RequestBody RedefinirSenhaDto dto) {
        String novaSenha = dto.getNovaSenha();
        if (novaSenha == null || novaSenha.isBlank()) {
            return ResponseEntity.badRequest().body("Nova senha obrigatória");
        }

        String token = dto.getToken();
        if (token != null && !token.isBlank()) {
            // Detecta se é JWT (começa com eyJ) ou token de recuperação UUID
            if (token.startsWith("eyJ")) {
                // É um JWT - precisa extrair o email diretamente do JWT sem usar AutenticarPorTokenRecuperacaoUseCase
                try {
                    // Usa o GerenciadorTokenJwt para extrair o email do JWT
                    String email = jwtTokenManager.getUsernameFromToken(token);
                    if (email == null || email.isBlank()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("JWT não contém email válido");
                    }
                    // Verifica se o JWT não expirou
                    if (jwtTokenManager.isTokenExpired(token)) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("JWT expirado");
                    }
                    redefinirSenhaAutenticadoUseCase.executar(email, novaSenha);
                    return ResponseEntity.ok("Senha redefinida com sucesso");
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("JWT inválido ou expirado");
                }
            } else {
                // É token de recuperação UUID - fluxo público
                redefinirSenhaUseCase.executar(token, novaSenha);
                return ResponseEntity.ok("Senha redefinida com sucesso");
            }
        }

        // Caminho sem token -> exige usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado e nenhum token informado");
        }
        redefinirSenhaAutenticadoUseCase.executar(authentication.getName(), novaSenha);
        return ResponseEntity.ok("Senha redefinida com sucesso");
    }

    @Operation(summary = "Autenticar usuário a partir do token de recuperação e obter JWT temporário")
    @GetMapping("/recuperar-senha/autenticar")
    public ResponseEntity<UsuarioTokenDto> autenticarPorToken(@RequestParam("token") String token) {
        AutenticarPorTokenRecuperacaoUseCase.Resultado resultado = autenticarPorTokenRecuperacaoUseCase.executar(token);
        return ResponseEntity.ok(UsuarioMapper.toTokenDto(resultado.usuario(), resultado.token()));
    }
}
