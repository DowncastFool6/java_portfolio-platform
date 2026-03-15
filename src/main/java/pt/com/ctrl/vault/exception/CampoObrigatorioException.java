package pt.com.ctrl.vault.exception;

/**
 * Classe que representa o erro de campos obrigatorios nao preenchidos pelo usuario
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 * @since 01/03/2026
 */
public class CampoObrigatorioException extends RuntimeException {

    public CampoObrigatorioException(String message) {
        super(message);
    }
}
