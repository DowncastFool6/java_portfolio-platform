package pt.com.ctrl.vault.model;

import java.time.LocalDateTime;

/**
 * Classe que representa o conteudo de um projeto
 * @author aliceslombardi
 * @since 28/02/2026
 */
public class Conteudo {

    private Integer id;
    private Projeto projeto;
    private String tipoConteudo;
    private String conteudo;
    private Integer ordemExibicao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataEdicao;
    private Usuario usuarioCriacao;
    private Usuario usuarioEdicao;

    public Conteudo() {}

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

    public String getTipoConteudo() {
        return tipoConteudo;
    }

    public void setTipoConteudo(String tipoConteudo) {
        this.tipoConteudo = tipoConteudo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Integer getOrdemExibicao() {
        return ordemExibicao;
    }

    public void setOrdemExibicao(Integer ordemExibicao) {
        this.ordemExibicao = ordemExibicao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataEdicao() {
        return dataEdicao;
    }

    public void setDataEdicao(LocalDateTime dataEdicao) {
        this.dataEdicao = dataEdicao;
    }

    public Usuario getUsuarioCriacao() {
        return usuarioCriacao;
    }

    public void setUsuarioCriacao(Usuario usuarioCriacao) {
        this.usuarioCriacao = usuarioCriacao;
    }

    public Usuario getUsuarioEdicao() {
        return usuarioEdicao;
    }

    public void setUsuarioEdicao(Usuario usuarioEdicao) {
        this.usuarioEdicao = usuarioEdicao;
    }
}
