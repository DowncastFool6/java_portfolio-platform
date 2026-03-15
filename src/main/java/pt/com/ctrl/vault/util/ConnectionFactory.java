package pt.com.ctrl.vault.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe responsavel pela criacao e fecho de ligacoes a base de dados.
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 */
public class ConnectionFactory {

    private static final String URL =
            "jdbc:mysql://localhost:3306/ctrlvault?useSSL=false&serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL não encontrado", e);
        }
    }

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }

    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) {

        try {
            if (rs != null) rs.close();
        } catch (SQLException ignored) {}

        try {
            if (stmt != null) stmt.close();
        } catch (SQLException ignored) {}

        try {
            if (conn != null) conn.close();
        } catch (SQLException ignored) {}

    }

    public static void close(Connection conn, Statement stmt) {
        close(conn, stmt, null);
    }

}