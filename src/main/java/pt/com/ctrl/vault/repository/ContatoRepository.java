package pt.com.ctrl.vault.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import pt.com.ctrl.vault.model.Contato;
import pt.com.ctrl.vault.model.Projeto;
import pt.com.ctrl.vault.model.Usuario;
import pt.com.ctrl.vault.util.ConnectionFactory;

/**
 * Classe responsavel pela escrita dos contatos enviados no sistema
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 * @since 08/03/2026
 */
public class ContatoRepository {

    public Integer salvar(Contato contato) {
        String sql =
                "INSERT INTO tb_contato " +
                "(id_projeto, id_usuario, mensagem, data_envio) " +
                "VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, contato.getProjeto().getId());
            stmt.setInt(2, contato.getUsuario().getId());
            stmt.setString(3, contato.getMensagem());
            stmt.setTimestamp(4, Timestamp.valueOf(contato.getDataEnvio()));
            
            stmt.executeUpdate();            
            rs = stmt.getGeneratedKeys();
            
            if (rs.next()) {
            	return rs.getInt(1);
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar contato", e);
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }
    }

    public boolean usuarioTemContatosPendentes(Integer idUsuario) {
        String sql =
                "SELECT count(1) " +
                "FROM tb_contato c " +
                "INNER JOIN tb_usuario_projeto up ON up.id_projeto = c.id_projeto " +
                "WHERE up.id_usuario = ? AND c.flg_lida = false ";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            rs = stmt.executeQuery();
            rs.next();
            int contatosPendentes = rs.getInt(1);
            return contatosPendentes > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar contatos pendentes", e);
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }
    }

    public List<Contato> listarContatosRecebidosDoUsuario(Integer idUsuario) {
        String sql =
                "SELECT c.id, c.mensagem, c.flg_lida, c.data_envio, " +
                "p.id AS projeto_id, p.titulo AS projeto_titulo, " +
                "u.id AS usuario_id, u.nome AS usuario_nome, u.email AS usuario_email " +
                "FROM tb_usuario_projeto up " +
                "INNER JOIN tb_contato c ON up.id_projeto = c.id_projeto " +
                "INNER JOIN tb_projeto p ON p.id = c.id_projeto " +
                "INNER JOIN tb_usuario u ON u.id = c.id_usuario " +
                "WHERE up.id_usuario = ? " +
                "ORDER BY c.flg_lida ASC, c.data_envio DESC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Contato> contatosList = new ArrayList<>();

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            rs = stmt.executeQuery();

            while (rs.next()) {
                contatosList.add(mapearContatoRecebido(rs));
            }

            return contatosList;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contatos recebidos", e);
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }
    }

    public void marcarContatosComoLidos(List<Integer> idsContato) {
        String sql =
                "UPDATE tb_contato c " +
                "SET c.flg_lida = true " +
                "WHERE c.id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);

            for (Integer idContato : idsContato) {
                stmt.setInt(1, idContato);
                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao marcar contatos como lidos", e);
        } finally {
            ConnectionFactory.close(conn, stmt);
        }
    }

    public void removerContatos(List<Integer> idsContato) {
        String sql =
                "DELETE FROM tb_contato " +
                "WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);

            for (Integer idContato : idsContato) {
                stmt.setInt(1, idContato);
                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover contatos", e);
        } finally {
            ConnectionFactory.close(conn, stmt);
        }
    }

    private Contato mapearContatoRecebido(ResultSet rs) throws SQLException {
        Contato contato = new Contato();
        contato.setId(rs.getInt("id"));
        contato.setMensagem(rs.getString("mensagem"));
        contato.setFlgLida(rs.getBoolean("flg_lida"));

        Timestamp dataEnvio = rs.getTimestamp("data_envio");
        if (dataEnvio != null) {
            contato.setDataEnvio(dataEnvio.toLocalDateTime());
        }

        Projeto projeto = new Projeto();
        projeto.setId(rs.getInt("projeto_id"));
        projeto.setTitulo(rs.getString("projeto_titulo"));
        contato.setProjeto(projeto);

        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("usuario_id"));
        usuario.setNome(rs.getString("usuario_nome"));
        usuario.setEmail(rs.getString("usuario_email"));
        contato.setUsuario(usuario);

        return contato;
    }
}
