package pt.com.ctrl.vault.exception;

/**
 * Classe que representa o erro de senha do usuario
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 * @since 01/03/2026
 */
public class SenhaInvalidaException extends RuntimeException {

    public SenhaInvalidaException(String message) {
        super(message);
    }
}
