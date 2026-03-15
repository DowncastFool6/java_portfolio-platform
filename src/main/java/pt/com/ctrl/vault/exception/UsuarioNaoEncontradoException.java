package pt.com.ctrl.vault.exception;

/**
 * Classe que representa o erro de nao encontrar um usuario na base de dados
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 * @since 01/03/2026
 */
public class UsuarioNaoEncontradoException extends RuntimeException {

    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }
}
