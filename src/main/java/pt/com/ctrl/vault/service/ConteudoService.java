package pt.com.ctrl.vault.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import javax.servlet.http.Part;
import pt.com.ctrl.vault.exception.CampoObrigatorioException;
import pt.com.ctrl.vault.model.Conteudo;
import pt.com.ctrl.vault.model.Projeto;
import pt.com.ctrl.vault.model.Usuario;
import pt.com.ctrl.vault.repository.ConteudoRepository;

/**
 * Classe com logica de negocio referente aos conteudos.
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 */
public class ConteudoService {

    public List<Projeto> listarProjetosDoUsuario(Usuario usuario) {
        validarUsuario(usuario);
        UsuarioService usuarioService = new UsuarioService();
        return usuarioService.listarProjetosDoUsuario(usuario.getId());
    }

    public Projeto buscarProjetoDoUsuario(Usuario usuario, Integer idProjeto) {
        validarUsuario(usuario);

        if (idProjeto == null) {
            throw new CampoObrigatorioException("Projeto invalido.");
        }

        UsuarioService usuarioService = new UsuarioService();
        List<Projeto> projetos = usuarioService.listarProjetosDoUsuario(usuario.getId());

        for (Projeto projeto : projetos) {
            if (idProjeto.equals(projeto.getId())) {
                return projeto;
            }
        }

        throw new CampoObrigatorioException("O utilizador nao faz parte deste projeto.");
    }

    public List<Conteudo> listarConteudosDoProjeto(Usuario usuario, Integer idProjeto) {
        buscarProjetoDoUsuario(usuario, idProjeto);
        ConteudoRepository conteudoRepository = new ConteudoRepository();
        return conteudoRepository.listarPorProjeto(idProjeto);
    }

    public Conteudo buscarConteudoDoProjeto(Usuario usuario, Integer idProjeto, Integer idConteudo) {
        buscarProjetoDoUsuario(usuario, idProjeto);

        ConteudoRepository conteudoRepository = new ConteudoRepository();
        Conteudo conteudo = conteudoRepository.buscarPorId(idConteudo);

        if (conteudo == null || conteudo.getProjeto() == null || !idProjeto.equals(conteudo.getProjeto().getId())) {
            throw new CampoObrigatorioException("Conteudo invalido.");
        }

        return conteudo;
    }

    public Conteudo criarConteudo(Usuario usuario, Integer idProjeto, String titulo, String tipoConteudo, String texto, Part arquivoPart) {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.validarUsuarioAtivoParaAcao(usuario);

        Projeto projeto = buscarProjetoDoUsuario(usuario, idProjeto);
        validarTipo(tipoConteudo);

        Conteudo conteudo = new Conteudo();
        conteudo.setProjeto(projeto);
        conteudo.setTitulo(normalizarTextoOpcional(titulo));
        conteudo.setTipoConteudo(tipoConteudo);
        conteudo.setOrdemExibicao(new ConteudoRepository().buscarProximaOrdem(idProjeto));
        conteudo.setDataCriacao(LocalDateTime.now());
        conteudo.setDataEdicao(LocalDateTime.now());
        conteudo.setUsuarioCriacao(usuario);
        conteudo.setUsuarioEdicao(usuario);

        preencherConteudo(conteudo, texto, arquivoPart, true);

        ConteudoRepository conteudoRepository = new ConteudoRepository();
        Integer idCriado = conteudoRepository.salvar(conteudo);
        conteudo.setId(idCriado);
        return conteudo;
    }

    public void atualizarConteudos(Usuario usuario, Integer idProjeto, List<Conteudo> conteudosAtualizados, List<Integer> idsOrdenados) {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.validarUsuarioAtivoParaAcao(usuario);

        buscarProjetoDoUsuario(usuario, idProjeto);

        if (conteudosAtualizados == null || conteudosAtualizados.isEmpty()) {
            throw new CampoObrigatorioException("Nao existem conteudos para atualizar.");
        }

        ConteudoRepository conteudoRepository = new ConteudoRepository();
        for (Conteudo conteudoAtualizado : conteudosAtualizados) {
            Conteudo existente = conteudoRepository.buscarPorId(conteudoAtualizado.getId());

            if (existente == null || existente.getProjeto() == null || !idProjeto.equals(existente.getProjeto().getId())) {
                throw new CampoObrigatorioException("Conteudo invalido.");
            }

            existente.setTitulo(normalizarTextoOpcional(conteudoAtualizado.getTitulo()));
            existente.setConteudo(conteudoAtualizado.getConteudo());
            existente.setNomeArquivo(conteudoAtualizado.getNomeArquivo());
            existente.setTipoMime(conteudoAtualizado.getTipoMime());
            existente.setArquivo(conteudoAtualizado.getArquivo());
            existente.setOrdemExibicao(conteudoAtualizado.getOrdemExibicao());
            existente.setDataEdicao(LocalDateTime.now());
            existente.setUsuarioEdicao(usuario);
            conteudoRepository.atualizar(existente);
        }

        List<Integer> idsNormalizados = new ArrayList<>(new LinkedHashSet<>(idsOrdenados));
        conteudoRepository.atualizarOrdens(idProjeto, idsNormalizados, usuario.getId());
    }

    public Conteudo montarAtualizacao(Usuario usuario, Integer idProjeto, Integer idConteudo, String titulo, String texto, Part arquivoPart) {
        Conteudo existente = buscarConteudoDoProjeto(usuario, idProjeto, idConteudo);
        Conteudo atualizado = new Conteudo();
        atualizado.setId(existente.getId());
        atualizado.setProjeto(existente.getProjeto());
        atualizado.setTipoConteudo(existente.getTipoConteudo());
        atualizado.setTitulo(titulo);
        atualizado.setOrdemExibicao(existente.getOrdemExibicao());

        if (isTexto(existente.getTipoConteudo())) {
            if (texto == null || texto.isBlank()) {
                throw new CampoObrigatorioException("O texto do conteudo e obrigatorio.");
            }
            atualizado.setConteudo(texto.trim());
            atualizado.setNomeArquivo(null);
            atualizado.setTipoMime(null);
            atualizado.setArquivo(null);
            return atualizado;
        }

        atualizado.setConteudo(null);

        if (arquivoPart != null && arquivoPart.getSize() > 0) {
            atualizado.setNomeArquivo(extrairNomeArquivo(arquivoPart));
            atualizado.setTipoMime(arquivoPart.getContentType());
            atualizado.setArquivo(lerBytes(arquivoPart));
        } else {
            atualizado.setNomeArquivo(existente.getNomeArquivo());
            atualizado.setTipoMime(existente.getTipoMime());
            atualizado.setArquivo(existente.getArquivo());
        }

        if (atualizado.getArquivo() == null || atualizado.getArquivo().length == 0) {
            throw new CampoObrigatorioException("O ficheiro do conteudo e obrigatorio.");
        }

        return atualizado;
    }

    public void removerConteudo(Usuario usuario, Integer idProjeto, Integer idConteudo) {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.validarUsuarioAtivoParaAcao(usuario);

        buscarProjetoDoUsuario(usuario, idProjeto);
        Conteudo existente = buscarConteudoDoProjeto(usuario, idProjeto, idConteudo);

        ConteudoRepository conteudoRepository = new ConteudoRepository();
        conteudoRepository.remover(idProjeto, existente.getId());

        List<Conteudo> restantes = conteudoRepository.listarPorProjeto(idProjeto);
        List<Integer> idsOrdenados = new ArrayList<>();
        for (Conteudo conteudo : restantes) {
            idsOrdenados.add(conteudo.getId());
        }

        if (!idsOrdenados.isEmpty()) {
            conteudoRepository.atualizarOrdens(idProjeto, idsOrdenados, usuario.getId());
        }
    }

    private void preencherConteudo(Conteudo conteudo, String texto, Part arquivoPart, boolean criacao) {
        if (isTexto(conteudo.getTipoConteudo())) {
            if (texto == null || texto.isBlank()) {
                throw new CampoObrigatorioException("O texto do conteudo e obrigatorio.");
            }
            conteudo.setConteudo(texto.trim());
            conteudo.setNomeArquivo(null);
            conteudo.setTipoMime(null);
            conteudo.setArquivo(null);
            return;
        }

        if (arquivoPart == null || arquivoPart.getSize() == 0) {
            throw new CampoObrigatorioException("Selecione um ficheiro para o conteudo.");
        }

        conteudo.setConteudo(null);
        conteudo.setNomeArquivo(extrairNomeArquivo(arquivoPart));
        conteudo.setTipoMime(arquivoPart.getContentType());
        conteudo.setArquivo(lerBytes(arquivoPart));

        if (conteudo.getArquivo() == null || conteudo.getArquivo().length == 0) {
            throw new CampoObrigatorioException("Nao foi possivel ler o ficheiro enviado.");
        }
    }

    private String extrairNomeArquivo(Part arquivoPart) {
        String submitted = arquivoPart.getSubmittedFileName();
        return submitted == null || submitted.isBlank() ? "arquivo" : submitted.trim();
    }

    private byte[] lerBytes(Part arquivoPart) {
        try (InputStream input = arquivoPart.getInputStream()) {
            return input.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler ficheiro enviado", e);
        }
    }

    private void validarTipo(String tipoConteudo) {
        if (!isTexto(tipoConteudo) && !isImagem(tipoConteudo)) {
            throw new CampoObrigatorioException("Tipo de conteudo invalido.");
        }
    }

    public boolean isTexto(String tipoConteudo) {
        return "TEXTO".equalsIgnoreCase(tipoConteudo);
    }

    public boolean isImagem(String tipoConteudo) {
        return "IMAGEM".equalsIgnoreCase(tipoConteudo);
    }

    private String normalizarTextoOpcional(String valor) {
        return valor == null || valor.isBlank() ? null : valor.trim();
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            throw new CampoObrigatorioException("Utilizador invalido.");
        }
    }
}
