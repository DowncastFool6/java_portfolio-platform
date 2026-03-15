package pt.com.ctrl.vault.repository;

import java.sql.*;
import pt.com.ctrl.vault.util.ConnectionFactory;
import pt.com.ctrl.vault.model.Usuario;

/**
 * Classe responsável pela leitura e manipulação dos dados de um usuário
 * @author aliceslombardi
 * @since 28/02/2026
 */
public class UsuarioRepository {

    public Usuario buscarPorEmail(String email) {

        String sql = "SELECT * FROM tb_usuario WHERE LOWER(email) = LOWER(?)";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setAtivo(rs.getBoolean("ativo"));
                usuario.setDataCriacao(rs.getTimestamp("data_criacao").toLocalDateTime());

                return usuario;
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário", e);
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
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
            throw new RuntimeException("Erro ao salvar usuário", e);
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }
    }
}