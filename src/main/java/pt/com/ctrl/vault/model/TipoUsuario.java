package pt.com.ctrl.vault.model;

/**
 * Classe que representa o tipo do usuario
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 * @since 28/02/2026
 */
public class TipoUsuario {

    private Integer id;
    private String descricao;

    public TipoUsuario() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
