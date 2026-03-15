package pt.com.ctrl.vault.service;

import java.time.LocalDateTime;
import org.mindrot.jbcrypt.BCrypt;
import pt.com.ctrl.vault.exception.CampoObrigatorioException;
import pt.com.ctrl.vault.exception.EmailJaRegistadoException;
import pt.com.ctrl.vault.exception.SenhaInvalidaException;
import pt.com.ctrl.vault.exception.UsuarioNaoEncontradoException;
import pt.com.ctrl.vault.repository.UsuarioRepository;
import pt.com.ctrl.vault.model.Usuario;

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

            throw new CampoObrigatorioException("Todos os campos são obrigatórios.");
        }

        UsuarioRepository usuarioRepository = new UsuarioRepository();

        if (usuarioRepository.buscarPorEmail(email) != null) {
            throw new EmailJaRegistadoException("O email já se encontra registado.");
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

            throw new CampoObrigatorioException("Email e senha são obrigatórios.");
        }

        UsuarioRepository usuarioRepository = new UsuarioRepository();
        Usuario usuario = usuarioRepository.buscarPorEmail(email);

        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Email não registado.");
        }

        boolean senhaValida = BCrypt.checkpw(senhaDigitada, usuario.getSenha());

        if (!senhaValida) {
            throw new SenhaInvalidaException("Senha incorreta.");
        }

        return usuario;
    }
    
}
