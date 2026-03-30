package pt.com.ctrl.vault.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pt.com.ctrl.vault.model.TipoUsuario;
import pt.com.ctrl.vault.util.ConnectionFactory;

/**
 * Repositorio responsavel pela leitura de tipos de utilizador.
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 */
public class TipoUsuarioRepository {

    public List<TipoUsuario> listarTodos() {
        String sql = "SELECT id, descricao FROM tb_tipo_usuario ORDER BY descricao";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<TipoUsuario> tiposList = new ArrayList<>();

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                TipoUsuario tipoUsuario = new TipoUsuario();
                tipoUsuario.setId(rs.getInt("id"));
                tipoUsuario.setDescricao(rs.getString("descricao"));
                tiposList.add(tipoUsuario);
            }

            return tiposList;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar tipos de usuario", e);
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }
    }
}
