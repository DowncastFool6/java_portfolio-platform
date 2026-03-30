package pt.com.ctrl.vault.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import pt.com.ctrl.vault.model.TipoUsuario;
import pt.com.ctrl.vault.model.Usuario;
import pt.com.ctrl.vault.util.ConnectionFactory;

/**
 * Classe responsavel pela leitura e manipulacao dos dados de um usuario
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 * @since 28/02/2026
 */
public class UsuarioRepository {

    public Usuario buscarPorEmail(String email) {

        String sql =
                "SELECT u.id, u.nome, u.email, u.senha, u.ativo, u.data_criacao, " +
                "tu.id AS tipo_id, tu.descricao AS tipo_descricao " +
                "FROM tb_usuario u " +
                "LEFT JOIN tb_tipo_usuario tu ON tu.id = u.id_tipo_usuario " +
                "WHERE LOWER(u.email) = LOWER(?)";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearUsuario(rs, true);
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuario", e);
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }
    }

    public Usuario buscarPorId(Integer idUsuario) {

        String sql =
                "SELECT u.id, u.nome, u.email, u.senha, u.ativo, u.data_criacao, " +
                "tu.id AS tipo_id, tu.descricao AS tipo_descricao " +
                "FROM tb_usuario u " +
                "LEFT JOIN tb_tipo_usuario tu ON tu.id = u.id_tipo_usuario " +
                "WHERE u.id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearUsuario(rs, false);
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuario por id", e);
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }
    }

    public List<Usuario> listarUsuariosSemAcesso() {

        String sql =
                "SELECT u.id, u.nome, u.email, u.senha, u.ativo, u.data_criacao, " +
                "tu.id AS tipo_id, tu.descricao AS tipo_descricao " +
                "FROM tb_usuario u " +
                "LEFT JOIN tb_tipo_usuario tu ON tu.id = u.id_tipo_usuario " +
                "LEFT JOIN tb_usuario_projeto up ON up.id_usuario = u.id " +
                "WHERE u.id_tipo_usuario IS NULL OR up.id_projeto IS NULL " +
                "ORDER BY u.data_criacao DESC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> usuariosList = new ArrayList<>();

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                usuariosList.add(mapearUsuario(rs, false));
            }

            return usuariosList;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuarios sem acesso", e);
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }
    }

    public void atualizarAcessoUsuario(Integer idUsuario, List<Integer> idsProjetos, Integer idTipoUsuario) {

        String sqlAtualizaTipo = "UPDATE tb_usuario SET id_tipo_usuario = ? WHERE id = ?";
        String sqlLimpaProjetos = "DELETE FROM tb_usuario_projeto WHERE id_usuario = ?";
        String sqlInsereProjeto = "INSERT INTO tb_usuario_projeto (id_usuario, id_projeto) VALUES (?, ?)";

        Connection conn = null;
        PreparedStatement stmtAtualizaTipo = null;
        PreparedStatement stmtLimpaProjetos = null;
        PreparedStatement stmtInsereProjeto = null;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            stmtAtualizaTipo = conn.prepareStatement(sqlAtualizaTipo);
            stmtAtualizaTipo.setInt(1, idTipoUsuario);
            stmtAtualizaTipo.setInt(2, idUsuario);
            stmtAtualizaTipo.executeUpdate();

            stmtLimpaProjetos = conn.prepareStatement(sqlLimpaProjetos);
            stmtLimpaProjetos.setInt(1, idUsuario);
            stmtLimpaProjetos.executeUpdate();

            stmtInsereProjeto = conn.prepareStatement(sqlInsereProjeto);
            for (Integer idProjeto : idsProjetos) {
                stmtInsereProjeto.setInt(1, idUsuario);
                stmtInsereProjeto.setInt(2, idProjeto);
                stmtInsereProjeto.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ignored) {}
            }
            throw new RuntimeException("Erro ao atualizar acesso do usuario", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException ignored) {}
            }
            ConnectionFactory.close(null, stmtInsereProjeto);
            ConnectionFactory.close(null, stmtLimpaProjetos);
            ConnectionFactory.close(conn, stmtAtualizaTipo);
        }
    }

    public Integer salvarNovoUsuario(Usuario usuario) {

        String sql =
                "INSERT INTO tb_usuario " +
                "(nome, email, senha, ativo, data_criacao) " +
                "VALUES (?, LOWER(?), ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setBoolean(4, usuario.getAtivo());
            stmt.setTimestamp(5, Timestamp.valueOf(usuario.getDataCriacao()));

            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();

            Integer idUsuarioCriado = null;

            if (rs.next()) {
                idUsuarioCriado = rs.getInt(1);
            }

            return idUsuarioCriado;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar usuario", e);
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }
    }
    
    public void atualizarStatusUsuario(Integer idUsuario, boolean ativo) {
        String sql = "UPDATE tb_usuario SET ativo = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setBoolean(1, ativo);
            stmt.setInt(2, idUsuario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar status do usuario", e);
        } finally {
            ConnectionFactory.close(conn, stmt);
        }
    }

    private Usuario mapearUsuario(ResultSet rs, boolean carregaSenha) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        if(carregaSenha) {        	
        	usuario.setSenha(rs.getString("senha"));
        }
        usuario.setAtivo(rs.getBoolean("ativo"));

        Timestamp dataCriacao = rs.getTimestamp("data_criacao");
        if (dataCriacao != null) {
            usuario.setDataCriacao(dataCriacao.toLocalDateTime());
        }

        Integer idTipo = (Integer) rs.getObject("tipo_id");
        if (idTipo != null) {
            TipoUsuario tipoUsuario = new TipoUsuario();
            tipoUsuario.setId(idTipo);
            tipoUsuario.setDescricao(rs.getString("tipo_descricao"));
            usuario.setTipoUsuario(tipoUsuario);
        }

        return usuario;
    }
}
