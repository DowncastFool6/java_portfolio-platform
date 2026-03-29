package pt.com.ctrl.vault.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe que presenta um projeto
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 * @since 28/02/2026
 */
public class Projeto {
    private static final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATA_HORA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private Integer id;
    private String titulo;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDateTime dataFim;
    private LocalDateTime dataCriacao;

    public Projeto() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDataCriacaoFormatada() {
        return dataCriacao == null ? "" : dataCriacao.format(DATA_HORA_FORMATTER);
    }

    public String getDataFimFormatada() {
        return dataFim == null ? "" : dataFim.format(DATA_HORA_FORMATTER);
    }

    public boolean isFechado() {
        return dataFim != null;
    }

    public boolean isAberto() {
        return !isFechado();
    }

    public String getStatusDescricao() {
        return isFechado() ? "Fechado" : "Aberto";
    }
    
}
