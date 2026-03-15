package pt.com.ctrl.vault.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import pt.com.ctrl.vault.exception.CampoObrigatorioException;
import pt.com.ctrl.vault.exception.EmailJaRegistadoException;
import pt.com.ctrl.vault.exception.SenhaInvalidaException;
import pt.com.ctrl.vault.exception.UsuarioNaoEncontradoException;
import pt.com.ctrl.vault.model.Projeto;
import pt.com.ctrl.vault.model.TipoUsuario;
import pt.com.ctrl.vault.model.Usuario;
import pt.com.ctrl.vault.repository.ProjetoRepository;
import pt.com.ctrl.vault.repository.TipoUsuarioRepository;
import pt.com.ctrl.vault.repository.UsuarioRepository;

/**
 * Classe com logica de negocio referente ao usuario
 * @author aliceslombardi
 * @since 28/02/2026
 */
public class UsuarioService {

    public Usuario registarUsuario(String nome, String email, String senha) {

        if (nome == null || nome.isBlank()
                || email == null || email.isBlank()
                || senha == null || senha.isBlank()) {

            throw new CampoObrigatorioException("Todos os campos sao obrigatorios.");
        }

        UsuarioRepository usuarioRepository = new UsuarioRepository();

        if (usuarioRepository.buscarPorEmail(email) != null) {
            throw new EmailJaRegistadoException("O email ja se encontra registado.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));
        usuario.setAtivo(true);
        usuario.setDataCriacao(LocalDateTime.now());

        Integer idUsuarioCriado = usuarioRepository.salvarNovoUsuario(usuario);
        usuario.setId(idUsuarioCriado);

        return usuario;
    }

    public Usuario autenticar(String email, String senhaDigitada) {

        if (email == null || email.isBlank()
                || senhaDigitada == null || senhaDigitada.isBlank()) {

            throw new CampoObrigatorioException("Email e senha sao obrigatorios.");
        }

        UsuarioRepository usuarioRepository = new UsuarioRepository();
        Usuario usuario = usuarioRepository.buscarPorEmail(email);

        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Email nao registado.");
        }

        boolean senhaValida = BCrypt.checkpw(senhaDigitada, usuario.getSenha());

        if (!senhaValida) {
            throw new SenhaInvalidaException("Senha incorreta.");
        }

        return usuario;
    }

    public void validarUsuarioAtivoParaAcao(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            throw new CampoObrigatorioException("Utilizador invalido.");
        }

        if (!Boolean.TRUE.equals(usuario.getAtivo())) {
            throw new CampoObrigatorioException("O seu utilizador esta inativo e apenas pode consultar informacao.");
        }
    }

    public List<Usuario> listarUsuariosSemAcesso() {
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        return usuarioRepository.listarUsuariosSemAcesso();
    }

    public Usuario buscarPorId(Integer idUsuario) {
        if (idUsuario == null) {
            throw new CampoObrigatorioException("Usuario invalido.");
        }

        UsuarioRepository usuarioRepository = new UsuarioRepository();
        Usuario usuario = usuarioRepository.buscarPorId(idUsuario);

        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Usuario nao encontrado.");
        }

        return usuario;
    }

    public Projeto buscarProjetoDoUsuario(Integer idUsuario) {
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        return usuarioRepository.buscarProjetoDoUsuario(idUsuario);
    }

    public List<Projeto> listarProjetosDoUsuario(Integer idUsuario) {
        if (idUsuario == null) {
            throw new CampoObrigatorioException("Usuario invalido.");
        }

        UsuarioRepository usuarioRepository = new UsuarioRepository();
        return usuarioRepository.listarProjetosDoUsuario(idUsuario);
    }

    public List<Projeto> listarProjetos() {
        ProjetoRepository projetoRepository = new ProjetoRepository();
        return projetoRepository.listarTodos();
    }

    public List<TipoUsuario> listarTiposUsuario() {
        TipoUsuarioRepository tipoUsuarioRepository = new TipoUsuarioRepository();
        return tipoUsuarioRepository.listarTodos();
    }

    public void atualizarAcessoUsuario(Integer idUsuario, List<Integer> idsProjetos, Integer idTipoUsuario) {
        if (idUsuario == null || idsProjetos == null || idsProjetos.isEmpty() || idTipoUsuario == null) {
            throw new CampoObrigatorioException("Utilizador, pelo menos um projeto e tipo de utilizador sao obrigatorios.");
        }

        List<Integer> idsProjetosNormalizados = new ArrayList<>(new LinkedHashSet<>(idsProjetos));
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        usuarioRepository.atualizarAcessoUsuario(idUsuario, idsProjetosNormalizados, idTipoUsuario);
    }

    public List<Usuario> listarUsuariosDoProjeto(Integer idProjeto) {
        if (idProjeto == null) {
            throw new CampoObrigatorioException("Projeto invalido.");
        }

        UsuarioRepository usuarioRepository = new UsuarioRepository();
        return usuarioRepository.listarUsuariosDoProjeto(idProjeto);
    }

    public void atualizarStatusUsuarioNoProjeto(Integer idProjeto, Integer idUsuario, boolean ativo) {
        if (idProjeto == null || idUsuario == null) {
            throw new CampoObrigatorioException("Projeto ou utilizador invalido.");
        }

        UsuarioRepository usuarioRepository = new UsuarioRepository();
        List<Usuario> usuariosProjeto = usuarioRepository.listarUsuariosDoProjeto(idProjeto);
        boolean pertenceAoProjeto = false;

        for (Usuario usuario : usuariosProjeto) {
            if (idUsuario.equals(usuario.getId())) {
                pertenceAoProjeto = true;
                break;
            }
        }

        if (!pertenceAoProjeto) {
            throw new CampoObrigatorioException("O utilizador nao pertence a este projeto.");
        }

        usuarioRepository.atualizarStatusUsuario(idUsuario, ativo);
    }
}
