package pt.com.ctrl.vault.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import pt.com.ctrl.vault.exception.CampoObrigatorioException;
import pt.com.ctrl.vault.model.Contato;
import pt.com.ctrl.vault.model.Projeto;
import pt.com.ctrl.vault.model.Usuario;
import pt.com.ctrl.vault.repository.ContatoRepository;
import pt.com.ctrl.vault.repository.ProjetoRepository;

/**
 * Classe com logica de negocio referente aos contatos
 * @author aliceslombardi
 * @since 08/03/2026
 */
public class ContatoService {

    public Contato enviarContato(Usuario usuario, Integer idProjeto, String mensagem) {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.validarUsuarioAtivoParaAcao(usuario);

        if (usuario == null || usuario.getId() == null) {
            throw new CampoObrigatorioException("Utilizador invalido.");
        }

        if (idProjeto == null) {
            throw new CampoObrigatorioException("O projeto do contato e obrigatorio.");
        }

        if (mensagem == null || mensagem.isBlank()) {
            throw new CampoObrigatorioException("A mensagem do contato e obrigatoria.");
        }

        ProjetoRepository projetoRepository = new ProjetoRepository();
        Projeto projeto = projetoRepository.buscarPorId(idProjeto);

        if (projeto == null || projeto.getId() == null) {
            throw new CampoObrigatorioException("Projeto invalido.");
        }

        Contato contato = new Contato();
        contato.setProjeto(projeto);
        contato.setUsuario(usuario);
        contato.setMensagem(mensagem.trim());
        contato.setFlgLida(false);
        contato.setDataEnvio(LocalDateTime.now());

        ContatoRepository contatoRepository = new ContatoRepository();
        Integer idContato = contatoRepository.salvar(contato);
        contato.setId(idContato);

        return contato;
    }

    public boolean usuarioTemContatosPendentes(Usuario usuario) {
        validarUsuarioParticipante(usuario);

        ContatoRepository contatoRepository = new ContatoRepository();
        return contatoRepository.usuarioTemContatosPendentes(usuario.getId());
    }

    public List<Contato> listarContatosRecebidos(Usuario usuario) {
        validarUsuarioParticipante(usuario);

        ContatoRepository contatoRepository = new ContatoRepository();
        return contatoRepository.listarContatosRecebidosDoUsuario(usuario.getId());
    }

    public void marcarContatosComoLidos(Usuario usuario, List<Integer> idsContato) {
        validarUsuarioParticipante(usuario);
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.validarUsuarioAtivoParaAcao(usuario);

        if (idsContato == null || idsContato.isEmpty()) {
            throw new CampoObrigatorioException("Selecione pelo menos um contato.");
        }

        List<Integer> idsNormalizados = new ArrayList<>(new LinkedHashSet<>(idsContato));

        ContatoRepository contatoRepository = new ContatoRepository();
        contatoRepository.marcarContatosComoLidos(usuario.getId(), idsNormalizados);
    }

    private void validarUsuarioParticipante(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            throw new CampoObrigatorioException("Utilizador invalido.");
        }
    }
}
