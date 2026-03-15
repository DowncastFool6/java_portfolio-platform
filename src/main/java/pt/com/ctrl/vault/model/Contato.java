package pt.com.ctrl.vault.model;

import java.time.LocalDateTime;

/**
 * Classe que representa o Contato realizado no sistema
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 * @since 28/02/2026
 */
public class Contato {

    private Integer id;
    private Projeto projeto;
    private Usuario usuario;
    private String mensagem;
    private Boolean flgLida;
    private LocalDateTime dataEnvio;

    public Contato() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Boolean getFlgLida() {
        return flgLida;
    }

    public void setFlgLida(Boolean flgLida) {
        this.flgLida = flgLida;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }
}
