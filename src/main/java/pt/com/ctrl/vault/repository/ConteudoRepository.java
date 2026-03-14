package pt.com.ctrl.vault.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import pt.com.ctrl.vault.model.Conteudo;
import pt.com.ctrl.vault.model.Projeto;
import pt.com.ctrl.vault.model.Usuario;
import pt.com.ctrl.vault.util.ConnectionFactory;
import pt.com.ctrl.vault.util.DatabaseInitializer;

public class ConteudoRepository {

    public ConteudoRepository() {
        DatabaseInitializer.ensureContentSchema();
    }

    public List<Conteudo> listarPorProjeto(Integer idProjeto) {
        String sql =
                "SELECT c.id, c.id_projeto, c.titulo, c.tipo_conteudo, c.conteudo, c.nome_arquivo, " +
                "c.tipo_mime, c.ordem_exibicao, c.data_criacao, c.data_edicao, " +
                "c.id_usuario_criacao, uc.nome AS usuario_criacao_nome, " +
                "c.id_usuario_edicao, ue.nome AS usuario_edicao_nome, " +
                "CASE WHEN c.arquivo IS NULL THEN 0 ELSE 1 END AS arquivo_disponivel " +
                "FROM tb_conteudo c " +
                "LEFT JOIN tb_usuario uc ON uc.id = c.id_usuario_criacao " +
                "LEFT JOIN tb_usuario ue ON ue.id = c.id_usuario_edicao " +
                "WHERE c.id_projeto = ? " +
                "ORDER BY c.ordem_exibicao, c.id";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Conteudo> conteudos = new ArrayList<>();

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idProjeto);
            rs = stmt.executeQuery();

            while (rs.next()) {
                conteudos.add(mapearConteudo(rs, false));
            }

            return conteudos;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar conteudos do projeto", e);
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }
    }

    public Conteudo buscarPorId(Integer idConteudo) {
        String sql =
                "SELECT c.id, c.id_projeto, c.titulo, c.tipo_conteudo, c.conteudo, c.nome_arquivo, " +
                "c.tipo_mime, c.arquivo, c.ordem_exibicao, c.data_criacao, c.data_edicao, " +
                "c.id_usuario_criacao, uc.nome AS usuario_criacao_nome, " +
                "c.id_usuario_edicao, ue.nome AS usuario_edicao_nome, " +
                "CASE WHEN c.arquivo IS NULL THEN 0 ELSE 1 END AS arquivo_disponivel " +
                "FROM tb_conteudo c " +
                "LEFT JOIN tb_usuario uc ON uc.id = c.id_usuario_criacao " +
                "LEFT JOIN tb_usuario ue ON ue.id = c.id_usuario_edicao " +
                "WHERE c.id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idConteudo);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearConteudo(rs, true);
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar conteudo por id", e);
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }
    }

    public Integer salvar(Conteudo conteudo) {
        String sql =
                "INSERT INTO tb_conteudo " +
                "(id_projeto, titulo, tipo_conteudo, conteudo, nome_arquivo, tipo_mime, arquivo, ordem_exibicao, " +
                "data_criacao, data_edicao, id_usuario_criacao, id_usuario_edicao) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preencherComum(stmt, conteudo, false);
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar conteudo", e);
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }
    }

    public void atualizar(Conteudo conteudo) {
        String sql =
                "UPDATE tb_conteudo SET titulo = ?, conteudo = ?, nome_arquivo = ?, tipo_mime = ?, " +
                "arquivo = ?, ordem_exibicao = ?, data_edicao = ?, id_usuario_edicao = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, conteudo.getTitulo());
            stmt.setString(2, conteudo.getConteudo());
            stmt.setString(3, conteudo.getNomeArquivo());
            stmt.setString(4, conteudo.getTipoMime());
            stmt.setBytes(5, conteudo.getArquivo());
            stmt.setInt(6, conteudo.getOrdemExibicao() == null ? 0 : conteudo.getOrdemExibicao());
            stmt.setTimestamp(7, conteudo.getDataEdicao() == null ? null : Timestamp.valueOf(conteudo.getDataEdicao()));
            if (conteudo.getUsuarioEdicao() != null && conteudo.getUsuarioEdicao().getId() != null) {
                stmt.setInt(8, conteudo.getUsuarioEdicao().getId());
            } else {
                stmt.setObject(8, null);
            }
            stmt.setInt(9, conteudo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar conteudo", e);
        } finally {
            ConnectionFactory.close(conn, stmt);
        }
    }

    public void atualizarOrdens(Integer idProjeto, List<Integer> idsConteudoOrdenados, Integer idUsuario) {
        String sql =
                "UPDATE tb_conteudo SET ordem_exibicao = ?, data_edicao = CURRENT_TIMESTAMP, id_usuario_edicao = ? " +
                "WHERE id = ? AND id_projeto = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(sql);

            for (int i = 0; i < idsConteudoOrdenados.size(); i++) {
                stmt.setInt(1, i + 1);
                stmt.setInt(2, idUsuario);
                stmt.setInt(3, idsConteudoOrdenados.get(i));
                stmt.setInt(4, idProjeto);
                stmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ignored) {}
            }
            throw new RuntimeException("Erro ao atualizar ordem dos conteudos", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException ignored) {}
            }
            ConnectionFactory.close(conn, stmt);
        }
    }

    public Integer buscarProximaOrdem(Integer idProjeto) {
        String sql = "SELECT COALESCE(MAX(ordem_exibicao), 0) + 1 AS proxima_ordem FROM tb_conteudo WHERE id_projeto = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idProjeto);
            rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("proxima_ordem");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao calcular ordem do conteudo", e);
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }
    }

    public void remover(Integer idProjeto, Integer idConteudo) {
        String sql = "DELETE FROM tb_conteudo WHERE id = ? AND id_projeto = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idConteudo);
            stmt.setInt(2, idProjeto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover conteudo", e);
        } finally {
            ConnectionFactory.close(conn, stmt);
        }
    }

    private void preencherComum(PreparedStatement stmt, Conteudo conteudo, boolean atualizacao) throws SQLException {
        stmt.setInt(1, conteudo.getProjeto().getId());
        stmt.setString(2, conteudo.getTitulo());
        stmt.setString(3, conteudo.getTipoConteudo());
        stmt.setString(4, conteudo.getConteudo());
        stmt.setString(5, conteudo.getNomeArquivo());
        stmt.setString(6, conteudo.getTipoMime());
        stmt.setBytes(7, conteudo.getArquivo());
        stmt.setInt(8, conteudo.getOrdemExibicao() == null ? 0 : conteudo.getOrdemExibicao());
        stmt.setTimestamp(9, conteudo.getDataCriacao() == null ? null : Timestamp.valueOf(conteudo.getDataCriacao()));
        stmt.setTimestamp(10, conteudo.getDataEdicao() == null ? null : Timestamp.valueOf(conteudo.getDataEdicao()));
        if (conteudo.getUsuarioCriacao() != null && conteudo.getUsuarioCriacao().getId() != null) {
            stmt.setInt(11, conteudo.getUsuarioCriacao().getId());
        } else {
            stmt.setObject(11, null);
        }
        if (conteudo.getUsuarioEdicao() != null && conteudo.getUsuarioEdicao().getId() != null) {
            stmt.setInt(12, conteudo.getUsuarioEdicao().getId());
        } else {
            stmt.setObject(12, null);
        }
    }

    private Conteudo mapearConteudo(ResultSet rs, boolean incluirArquivo) throws SQLException {
        Conteudo conteudo = new Conteudo();
        Projeto projeto = new Projeto();
        projeto.setId(rs.getInt("id_projeto"));
        conteudo.setProjeto(projeto);
        conteudo.setId(rs.getInt("id"));
        conteudo.setTitulo(rs.getString("titulo"));
        conteudo.setTipoConteudo(rs.getString("tipo_conteudo"));
        conteudo.setConteudo(rs.getString("conteudo"));
        conteudo.setNomeArquivo(rs.getString("nome_arquivo"));
        conteudo.setTipoMime(rs.getString("tipo_mime"));
        conteudo.setArquivoDisponivel(rs.getInt("arquivo_disponivel") == 1);
        if (incluirArquivo) {
            conteudo.setArquivo(rs.getBytes("arquivo"));
        }
        conteudo.setOrdemExibicao(rs.getInt("ordem_exibicao"));

        Timestamp dataCriacao = rs.getTimestamp("data_criacao");
        if (dataCriacao != null) {
            conteudo.setDataCriacao(dataCriacao.toLocalDateTime());
        }

        Timestamp dataEdicao = rs.getTimestamp("data_edicao");
        if (dataEdicao != null) {
            conteudo.setDataEdicao(dataEdicao.toLocalDateTime());
        }

        int idUsuarioCriacao = rs.getInt("id_usuario_criacao");
        if (!rs.wasNull()) {
            Usuario usuarioCriacao = new Usuario();
            usuarioCriacao.setId(idUsuarioCriacao);
            usuarioCriacao.setNome(rs.getString("usuario_criacao_nome"));
            conteudo.setUsuarioCriacao(usuarioCriacao);
        }

        int idUsuarioEdicao = rs.getInt("id_usuario_edicao");
        if (!rs.wasNull()) {
            Usuario usuarioEdicao = new Usuario();
            usuarioEdicao.setId(idUsuarioEdicao);
            usuarioEdicao.setNome(rs.getString("usuario_edicao_nome"));
            conteudo.setUsuarioEdicao(usuarioEdicao);
        }

        return conteudo;
    }
}
