package pt.com.ctrl.vault.exception;

/**
 * Classe que presenta o erro de um email ja registado
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 * @since 01/03/2026
 */
public class EmailJaRegistadoException extends RuntimeException {

    public EmailJaRegistadoException(String message) {
        super(message);
    }
}
