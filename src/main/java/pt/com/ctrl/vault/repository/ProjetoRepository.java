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
import pt.com.ctrl.vault.model.TipoUsuario;
import pt.com.ctrl.vault.model.Usuario;
import pt.com.ctrl.vault.util.ConnectionFactory;

/**
 * Repositorio responsavel pela leitura de projetos.
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 */
public class ProjetoRepository {

    public Projeto buscarPorId(Integer idProjeto) {
        String sql = "SELECT id, titulo, descricao, data_inicio, data_fim, data_criacao FROM tb_projeto WHERE id = ?";

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
                projeto.setTitulo(rs.getString("titulo"));
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
        String sql = "SELECT id, titulo, descricao, data_inicio, data_fim, data_criacao FROM tb_projeto ORDER BY descricao";

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
                projeto.setTitulo(rs.getString("titulo"));
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
    
    
    public List<Projeto> listarProjetosDoUsuario(Integer idUsuario) {
        String sql =
                "SELECT p.id, p.titulo, p.descricao, p.data_inicio, p.data_fim, p.data_criacao " +
                "FROM tb_usuario_projeto up " +
                "INNER JOIN tb_projeto p ON p.id = up.id_projeto " +
                "WHERE up.id_usuario = ? " +
                "ORDER BY p.descricao";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Projeto> projetos = new ArrayList<>();

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Projeto projeto = new Projeto();
                projeto.setId(rs.getInt("id"));
                projeto.setTitulo(rs.getString("titulo"));
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
            throw new RuntimeException("Erro ao listar projetos do usuario", e);
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }
    }
    
    public Projeto buscarProjetoDoUsuario(Integer idUsuario) {

        String sql =
                "SELECT p.id, p.titulo, p.descricao, p.data_inicio, p.data_fim, p.data_criacao " +
                "FROM tb_usuario_projeto up " +
                "INNER JOIN tb_projeto p ON p.id = up.id_projeto " +
                "WHERE up.id_usuario = ? " +
                "ORDER BY up.id DESC LIMIT 1";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Projeto projeto = new Projeto();
                projeto.setId(rs.getInt("id"));
                projeto.setTitulo(rs.getString("titulo"));
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
            throw new RuntimeException("Erro ao buscar projeto do usuario", e);
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }
    }
    
    public List<Usuario> listarUsuariosDoProjeto(Integer idProjeto) {
        String sql =
                "SELECT DISTINCT u.id, u.nome, u.email, u.senha, u.ativo, u.data_criacao, " +
                "tu.id AS tipo_id, tu.descricao AS tipo_descricao " +
                "FROM tb_usuario_projeto up " +
                "INNER JOIN tb_usuario u ON u.id = up.id_usuario " +
                "LEFT JOIN tb_tipo_usuario tu ON tu.id = u.id_tipo_usuario " +
                "WHERE up.id_projeto = ? " +
                "ORDER BY u.nome";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> usuarios = new ArrayList<>();

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idProjeto);
            rs = stmt.executeQuery();

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }

            return usuarios;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuarios do projeto", e);
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }
    }
    
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));
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

    public void atualizarDataFimProjeto(Integer idProjeto, Date dataFim) {
        String sql = "UPDATE tb_projeto SET data_fim = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setDate(1, dataFim);
            stmt.setInt(2, idProjeto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar status do projeto", e);
        } finally {
            ConnectionFactory.close(conn, stmt);
        }
    }
    
}
