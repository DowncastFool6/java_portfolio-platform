package pt.com.ctrl.vault.model;

/**
 * Classe que representa a ligacao entre um usuario e um projeto
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 * @since 28/02/2026
 */
public class UsuarioProjeto {

    private Integer id;
    private Usuario usuario;
    private Projeto projeto;

    public UsuarioProjeto() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }
}
