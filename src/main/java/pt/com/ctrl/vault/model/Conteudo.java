package pt.com.ctrl.vault.model;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * Classe que representa o conteudo de um projeto
 * 
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 * @since 28/02/2026
 */
public class Conteudo {
    private static final DateTimeFormatter DATA_HORA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	private Integer id;
	private Projeto projeto;
	private String titulo;
	private String tipoConteudo;
	private String conteudo;
	private String nomeArquivo;
	private String tipoArquivo;
	private byte[] arquivo;
	private boolean arquivoDisponivel;
	private Integer ordemExibicao;
	private LocalDateTime dataCriacao;
	private LocalDateTime dataEdicao;
	private Usuario usuarioCriacao;
	private Usuario usuarioEdicao;

	public Conteudo() {
	}

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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
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

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getTipoMime() {
		return tipoArquivo;
	}

	public void setTipoMime(String tipoMime) {
		this.tipoArquivo = tipoMime;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public boolean isArquivoDisponivel() {
		return arquivoDisponivel;
	}

	public void setArquivoDisponivel(boolean arquivoDisponivel) {
		this.arquivoDisponivel = arquivoDisponivel;
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

	public String getDataCriacaoFormatada() {
		return formatarData(dataCriacao);
	}

	public String getDataEdicaoFormatada() {
		return formatarData(dataEdicao);
	}

	public boolean isEditado() {
		if (dataEdicao == null) {
			return false;
		}
		if (dataCriacao == null) {
			return true;
		}
		return dataEdicao.equals(dataCriacao) == false;
	}

	private String formatarData(LocalDateTime data) {
		return data == null ? "" : data.format(DATA_HORA_FORMATTER);
	}
}
