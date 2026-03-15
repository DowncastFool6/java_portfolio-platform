package pt.com.ctrl.vault.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Garante a existencia das estruturas de dados necessarias para o modulo de conteudos.
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 */
public final class DatabaseInitializer {

    private static volatile boolean contentSchemaReady;

    private DatabaseInitializer() {}

    public static void ensureContentSchema() {
        if (contentSchemaReady) {
            return;
        }

        synchronized (DatabaseInitializer.class) {
            if (contentSchemaReady) {
                return;
            }

            Connection conn = null;

            try {
                conn = ConnectionFactory.getConnection();
                criarTabelaConteudo(conn);
                garantirColuna(conn, "tb_conteudo", "titulo",
                        "ALTER TABLE tb_conteudo ADD COLUMN titulo VARCHAR(255) NULL AFTER id_projeto");
                garantirColuna(conn, "tb_conteudo", "nome_arquivo",
                        "ALTER TABLE tb_conteudo ADD COLUMN nome_arquivo VARCHAR(255) NULL AFTER conteudo");
                garantirColuna(conn, "tb_conteudo", "tipo_mime",
                        "ALTER TABLE tb_conteudo ADD COLUMN tipo_mime VARCHAR(120) NULL AFTER nome_arquivo");
                garantirColuna(conn, "tb_conteudo", "arquivo",
                        "ALTER TABLE tb_conteudo ADD COLUMN arquivo LONGBLOB NULL AFTER tipo_mime");
                garantirColuna(conn, "tb_conteudo", "ordem_exibicao",
                        "ALTER TABLE tb_conteudo ADD COLUMN ordem_exibicao INT NOT NULL DEFAULT 0 AFTER arquivo");
                garantirColuna(conn, "tb_conteudo", "data_criacao",
                        "ALTER TABLE tb_conteudo ADD COLUMN data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER ordem_exibicao");
                garantirColuna(conn, "tb_conteudo", "data_edicao",
                        "ALTER TABLE tb_conteudo ADD COLUMN data_edicao DATETIME NULL AFTER data_criacao");
                garantirColuna(conn, "tb_conteudo", "id_usuario_criacao",
                        "ALTER TABLE tb_conteudo ADD COLUMN id_usuario_criacao INT NULL AFTER data_edicao");
                garantirColuna(conn, "tb_conteudo", "id_usuario_edicao",
                        "ALTER TABLE tb_conteudo ADD COLUMN id_usuario_edicao INT NULL AFTER id_usuario_criacao");
                contentSchemaReady = true;

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao inicializar tabela de conteudos", e);
            } finally {
                ConnectionFactory.close(conn, null);
            }
        }
    }

    private static void criarTabelaConteudo(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS tb_conteudo (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "id_projeto INT NOT NULL, " +
                "titulo VARCHAR(255) NULL, " +
                "tipo_conteudo VARCHAR(20) NOT NULL, " +
                "conteudo LONGTEXT NULL, " +
                "nome_arquivo VARCHAR(255) NULL, " +
                "tipo_mime VARCHAR(120) NULL, " +
                "arquivo LONGBLOB NULL, " +
                "ordem_exibicao INT NOT NULL DEFAULT 0, " +
                "data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                "data_edicao DATETIME NULL, " +
                "id_usuario_criacao INT NULL, " +
                "id_usuario_edicao INT NULL, " +
                "INDEX idx_conteudo_projeto_ordem (id_projeto, ordem_exibicao), " +
                "CONSTRAINT fk_conteudo_projeto FOREIGN KEY (id_projeto) REFERENCES tb_projeto(id), " +
                "CONSTRAINT fk_conteudo_usuario_criacao FOREIGN KEY (id_usuario_criacao) REFERENCES tb_usuario(id), " +
                "CONSTRAINT fk_conteudo_usuario_edicao FOREIGN KEY (id_usuario_edicao) REFERENCES tb_usuario(id))";

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.execute();
        } finally {
            ConnectionFactory.close(null, stmt);
        }
    }

    private static void garantirColuna(Connection conn, String tabela, String coluna, String alterSql) throws SQLException {
        if (colunaExiste(conn, tabela, coluna)) {
            return;
        }

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(alterSql);
            stmt.execute();
        } finally {
            ConnectionFactory.close(null, stmt);
        }
    }

    private static boolean colunaExiste(Connection conn, String tabela, String coluna) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM information_schema.columns " +
                    "WHERE table_schema = DATABASE() AND table_name = ? AND column_name = ?");
            stmt.setString(1, tabela);
            stmt.setString(2, coluna);
            rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } finally {
            ConnectionFactory.close(null, stmt, rs);
        }
    }
}
