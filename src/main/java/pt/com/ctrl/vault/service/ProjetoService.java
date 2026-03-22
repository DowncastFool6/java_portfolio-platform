package pt.com.ctrl.vault.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import pt.com.ctrl.vault.exception.CampoObrigatorioException;
import pt.com.ctrl.vault.model.Projeto;
import pt.com.ctrl.vault.model.Usuario;
import pt.com.ctrl.vault.repository.ProjetoRepository;

/**
 * Classe com logica de negocio referente aos projetos.
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 */
public class ProjetoService {
	
    public List<Projeto> listarProjetosDoUsuario(Integer idUsuario) {
        if (idUsuario == null) {
            throw new CampoObrigatorioException("Usuario invalido.");
        }

        ProjetoRepository projetoRepository = new ProjetoRepository();
        return projetoRepository.listarProjetosDoUsuario(idUsuario);
    }
    
    public List<Projeto> listarProjetos() {
        ProjetoRepository projetoRepository = new ProjetoRepository();
        return projetoRepository.listarTodos();
    }
    
    public Projeto buscarProjetoDoUsuario(Integer idUsuario) {
    	ProjetoRepository projetoRepository = new ProjetoRepository();
        return projetoRepository.buscarProjetoDoUsuario(idUsuario);
    }
    
    public Projeto buscarProjetoPorUsuarioEProjeto(Integer idUsuario, Integer idProjeto) {
    	ProjetoRepository projetoRepository = new ProjetoRepository();
        List<Projeto> projetos = projetoRepository.listarProjetosDoUsuario(idUsuario);

        for (Projeto projeto : projetos) {
            if (idProjeto.equals(projeto.getId())) {
                return projeto;
            }
        }
        return null;
    }

    public Projeto buscarProjetoPorId(Integer idProjeto) {
        if (idProjeto == null) {
            throw new CampoObrigatorioException("Projeto invalido.");
        }

        ProjetoRepository projetoRepository = new ProjetoRepository();
        Projeto projeto = projetoRepository.buscarPorId(idProjeto);

        if (projeto == null) {
            throw new CampoObrigatorioException("Projeto invalido.");
        }

        return projeto;
    }
    
    public Projeto verificaSeUsuarioPercenteAoProjeto(Integer idUsuario, Integer idProjeto) {
    	ProjetoRepository projetoRepository = new ProjetoRepository();
        List<Projeto> projetos = projetoRepository.listarProjetosDoUsuario(idUsuario);

        for (Projeto projeto : projetos) {
            if (idProjeto.equals(projeto.getId())) {
                return projeto;
            }
        }

        throw new CampoObrigatorioException("O utilizador nao faz parte deste projeto.");
    }
    
    public List<Usuario> listarUsuariosDoProjeto(Integer idProjeto) {
        if (idProjeto == null) {
            throw new CampoObrigatorioException("Projeto invalido.");
        }

        ProjetoRepository projetoRepository = new ProjetoRepository();
        return projetoRepository.listarUsuariosDoProjeto(idProjeto);
    }
    
    public void atualizarStatusUsuarioNoProjeto(Integer idProjeto, Integer idUsuario, boolean ativo) {
        if (idProjeto == null || idUsuario == null) {
            throw new CampoObrigatorioException("Projeto ou utilizador invalido.");
        }

        verificaSeUsuarioPertenceAoProjeto(idProjeto, idUsuario);

        ProjetoRepository projetoRepository = new ProjetoRepository();
        projetoRepository.atualizarStatusUsuario(idUsuario, ativo);
    }

    public Projeto fecharProjeto(Integer idProjeto) {
        Projeto projeto = buscarProjetoPorId(idProjeto);
        if (projeto.isFechado()) {
            throw new CampoObrigatorioException("O projeto ja se encontra fechado.");
        }

        ProjetoRepository projetoRepository = new ProjetoRepository();
        projetoRepository.atualizarDataFimProjeto(idProjeto, Date.valueOf(LocalDate.now()));
        return buscarProjetoPorId(idProjeto);
    }

    public Projeto reabrirProjeto(Integer idProjeto) {
        Projeto projeto = buscarProjetoPorId(idProjeto);
        if (projeto.isAberto()) {
            throw new CampoObrigatorioException("O projeto ja se encontra aberto.");
        }

        ProjetoRepository projetoRepository = new ProjetoRepository();
        projetoRepository.atualizarDataFimProjeto(idProjeto, null);
        return buscarProjetoPorId(idProjeto);
    }

    public Projeto validarProjetoAberto(Integer idProjeto) {
        Projeto projeto = buscarProjetoPorId(idProjeto);
        if (projeto.isFechado()) {
            throw new CampoObrigatorioException("O projeto esta fechado e apenas pode ser visualizado.");
        }
        return projeto;
    }

	private void verificaSeUsuarioPertenceAoProjeto(Integer idProjeto, Integer idUsuario) {
        ProjetoRepository projetoRepository = new ProjetoRepository();

        List<Usuario> usuariosProjeto = projetoRepository.listarUsuariosDoProjeto(idProjeto);
        boolean pertenceAoProjeto = false;

        for (Usuario usuario : usuariosProjeto) {
            if (idUsuario.equals(usuario.getId())) {
                pertenceAoProjeto = true;
                break;
            }
        }

        if (!pertenceAoProjeto) {
            throw new CampoObrigatorioException("O utilizador nao pertence a este projeto.");
        }
	}
    
}
