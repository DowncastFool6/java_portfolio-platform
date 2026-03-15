package pt.com.ctrl.vault.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import pt.com.ctrl.vault.model.Projeto;
import pt.com.ctrl.vault.util.ConnectionFactory;

/**
 * Repositorio responsavel pela leitura de projetos.
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 */
public class ProjetoRepository {

    public Projeto buscarPorId(Integer idProjeto) {
        String sql = "SELECT id, descricao, data_inicio, data_fim, data_criacao FROM tb_projeto WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idProjeto);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Projeto projeto = new Projeto();
                projeto.setId(rs.getInt("id"));
                projeto.setDescricao(rs.getString("descricao"));

                Date dataInicio = rs.getDate("data_inicio");
                if (dataInicio != null) {
                    projeto.setDataInicio(dataInicio.toLocalDate());
                }

                Date dataFim = rs.getDate("data_fim");
                if (dataFim != null) {
                    projeto.setDataFim(dataFim.toLocalDate());
                }

                Timestamp dataCriacao = rs.getTimestamp("data_criacao");
                if (dataCriacao != null) {
                    projeto.setDataCriacao(dataCriacao.toLocalDateTime());
                }

                return projeto;
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar projeto por id", e);
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }
    }

    public List<Projeto> listarTodos() {
        String sql = "SELECT id, descricao, data_inicio, data_fim, data_criacao FROM tb_projeto ORDER BY descricao";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Projeto> projetos = new ArrayList<>();

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Projeto projeto = new Projeto();
                projeto.setId(rs.getInt("id"));
                projeto.setDescricao(rs.getString("descricao"));

                Date dataInicio = rs.getDate("data_inicio");
                if (dataInicio != null) {
                    projeto.setDataInicio(dataInicio.toLocalDate());
                }

                Date dataFim = rs.getDate("data_fim");
                if (dataFim != null) {
                    projeto.setDataFim(dataFim.toLocalDate());
                }

                Timestamp dataCriacao = rs.getTimestamp("data_criacao");
                if (dataCriacao != null) {
                    projeto.setDataCriacao(dataCriacao.toLocalDateTime());
                }

                projetos.add(projeto);
            }

            return projetos;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar projetos", e);
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }
    }
}
