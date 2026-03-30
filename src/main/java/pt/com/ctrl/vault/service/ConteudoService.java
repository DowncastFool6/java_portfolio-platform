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
    private static final long TAMANHO_MAXIMO_IMAGEM_BYTES = 1024 * 1024;

    public List<Conteudo> listarConteudosDoProjeto(Usuario usuario, Integer idProjeto) {
        ProjetoService projetoService = new ProjetoService();
        projetoService.verificaSeUsuarioPercenteAoProjeto(usuario.getId(), idProjeto);
        
        ConteudoRepository conteudoRepository = new ConteudoRepository();
        return conteudoRepository.listarPorProjeto(idProjeto);
    }

    public Conteudo buscarConteudoDoProjeto(Usuario usuario, Integer idProjeto, Integer idConteudo) {
    	ProjetoService projetoService = new ProjetoService();
    	projetoService.verificaSeUsuarioPercenteAoProjeto(usuario.getId(), idProjeto);

        ConteudoRepository conteudoRepository = new ConteudoRepository();
        Conteudo conteudo = conteudoRepository.buscarPorId(idConteudo);

        if (conteudo == null || conteudo.getProjeto() == null || !idProjeto.equals(conteudo.getProjeto().getId())) {
            throw new CampoObrigatorioException("Conteudo invalido.");
        }

        return conteudo;
    }

    public Conteudo criarConteudo(Usuario usuario, Integer idProjeto, String titulo, String tipoConteudo, String texto, Part arquivoPart) {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.validarUsuarioAtivoParaExecutarAcao(usuario);

        ProjetoService projetoService = new ProjetoService();
        projetoService.verificaSeUsuarioPercenteAoProjeto(usuario.getId(), idProjeto);
        projetoService.validarProjetoAberto(idProjeto);
        
        Projeto projeto = projetoService.buscarProjetoPorUsuarioEProjeto(usuario.getId(), idProjeto);
        
        validarTipo(tipoConteudo);

        Conteudo conteudo = new Conteudo();
        conteudo.setProjeto(projeto);
        conteudo.setTitulo(titulo);
        conteudo.setTipoConteudo(tipoConteudo);
        conteudo.setOrdemExibicao(new ConteudoRepository().buscarProximaOrdem(idProjeto));
        conteudo.setDataCriacao(LocalDateTime.now());
        conteudo.setDataEdicao(null);
        conteudo.setUsuarioCriacao(usuario);
        conteudo.setUsuarioEdicao(null);

        preencherConteudo(conteudo, texto, arquivoPart, true);

        ConteudoRepository conteudoRepository = new ConteudoRepository();
        Integer idCriado = conteudoRepository.salvar(conteudo);
        conteudo.setId(idCriado);
        return conteudo;
    }

    public void atualizarConteudos(Usuario usuario, Integer idProjeto, List<Conteudo> conteudosAtualizados, List<Integer> idsOrdenados) {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.validarUsuarioAtivoParaExecutarAcao(usuario);

        ProjetoService projetoService = new ProjetoService();
        projetoService.verificaSeUsuarioPercenteAoProjeto(usuario.getId(), idProjeto);
        projetoService.validarProjetoAberto(idProjeto);

        if (conteudosAtualizados == null || conteudosAtualizados.isEmpty()) {
            throw new CampoObrigatorioException("Nao existem conteudos para atualizar.");
        }

        ConteudoRepository conteudoRepository = new ConteudoRepository();
        for (Conteudo conteudoAtualizado : conteudosAtualizados) {
            Conteudo existente = conteudoRepository.buscarPorId(conteudoAtualizado.getId());

            if (existente == null || existente.getProjeto() == null || !idProjeto.equals(existente.getProjeto().getId())) {
                throw new CampoObrigatorioException("Conteudo invalido.");
            }

            existente.setTitulo(conteudoAtualizado.getTitulo());
            existente.setConteudo(conteudoAtualizado.getConteudo());
            existente.setNomeArquivo(conteudoAtualizado.getNomeArquivo());
            existente.setTipoMime(conteudoAtualizado.getTipoMime());
            existente.setArquivo(conteudoAtualizado.getArquivo());
            existente.setOrdemExibicao(conteudoAtualizado.getOrdemExibicao());
            existente.setDataEdicao(LocalDateTime.now());
            existente.setUsuarioEdicao(usuario);
            conteudoRepository.atualizar(existente);
        }

        List<Integer> idsNormalizadosList = new ArrayList<>(new LinkedHashSet<>(idsOrdenados));
        conteudoRepository.atualizarOrdens(idProjeto, idsNormalizadosList, usuario.getId());
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
            validarTamanhoArquivo(arquivoPart);
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
        usuarioService.validarUsuarioAtivoParaExecutarAcao(usuario);

        ProjetoService projetoService = new ProjetoService();
        projetoService.verificaSeUsuarioPercenteAoProjeto(usuario.getId(), idProjeto);
        projetoService.validarProjetoAberto(idProjeto);
        
        Conteudo existente = buscarConteudoDoProjeto(usuario, idProjeto, idConteudo);

        ConteudoRepository conteudoRepository = new ConteudoRepository();
        conteudoRepository.remover(idProjeto, existente.getId());

        List<Conteudo> restantes = conteudoRepository.listarPorProjeto(idProjeto);
        List<Integer> idsOrdenadosList = new ArrayList<>();
        for (Conteudo conteudo : restantes) {
            idsOrdenadosList.add(conteudo.getId());
        }

        if (!idsOrdenadosList.isEmpty()) {
            conteudoRepository.atualizarOrdens(idProjeto, idsOrdenadosList, usuario.getId());
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

        validarTamanhoArquivo(arquivoPart);

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
        if (isTexto(tipoConteudo) == false && "IMAGEM".equalsIgnoreCase(tipoConteudo) == false) {
            throw new CampoObrigatorioException("Tipo de conteudo invalido.");
        }
    }

    public boolean isTexto(String tipoConteudo) {
        return "TEXTO".equalsIgnoreCase(tipoConteudo);
    }

    private void validarTamanhoArquivo(Part arquivoPart) {
        if (arquivoPart != null && arquivoPart.getSize() > TAMANHO_MAXIMO_IMAGEM_BYTES) {
            throw new CampoObrigatorioException("A imagem deve ter no maximo 1 MB.");
        }
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            throw new CampoObrigatorioException("Utilizador invalido.");
        }
    }
}
