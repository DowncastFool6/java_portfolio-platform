package pt.com.ctrl.vault.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe responsavel pela criacao e fecho de ligacoes a base de dados.
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 */
public class ConnectionFactory {

    private static final Map<String, String> ENV_FILE_VALUES = loadEnvFile();
    private static final String URL = getConfig(
            "DB_URL",
            "jdbc:mysql://localhost:3306/ctrlvault?useSSL=false&serverTimezone=UTC"
    );
    private static final String USER = getConfig("DB_USER", "root");
    private static final String PASSWORD = getConfig("DB_PASSWORD", "");

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

    private static String getConfig(String key, String defaultValue) {
        String envValue = System.getenv(key);
        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        String fileValue = ENV_FILE_VALUES.get(key);
        if (fileValue != null && !fileValue.isBlank()) {
            return fileValue;
        }

        return defaultValue;
    }

    /**
     * 
     * @return
     */
    private static Map<String, String> loadEnvFile() {
        Path envFilePath = resolveEnvFilePath();

        Map<String, String> values = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(envFilePath);
            for (String line : lines) {
                String trimmedLine = line.trim();

                if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
                    continue;
                }

                int separatorIndex = trimmedLine.indexOf('=');
                if (separatorIndex <= 0) {
                    continue;
                }

                String key = trimmedLine.substring(0, separatorIndex).trim();
                String value = trimmedLine.substring(separatorIndex + 1).trim();
                values.put(key, stripQuotes(value));
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo .env", e);
        }

        return Map.copyOf(values);
    }

    private static Path resolveEnvFilePath() {
        String configuredPath = System.getenv("CTRL_VAULT_ENV_FILE");
        if (configuredPath != null && !configuredPath.isBlank()) {
            Path path = Paths.get(configuredPath).toAbsolutePath().normalize();
            if (Files.exists(path)) {
                return path;
            }
        }

        Path currentDirectory = Paths.get("").toAbsolutePath().normalize();
        Path[] candidates = new Path[] {
                currentDirectory.resolve(".env"),
                currentDirectory.getParent() != null ? currentDirectory.getParent().resolve(".env") : null,
                currentDirectory.getParent() != null && currentDirectory.getParent().getParent() != null
                        ? currentDirectory.getParent().getParent().resolve(".env")
                        : null
        };

        for (Path candidate : candidates) {
            if (candidate != null && Files.exists(candidate)) {
                return candidate;
            }
        }

        return null;
    }

    private static String stripQuotes(String value) {
        if (value.length() >= 2) {
            boolean doubleQuoted = value.startsWith("\"") && value.endsWith("\"");
            boolean singleQuoted = value.startsWith("'") && value.endsWith("'");
            if (doubleQuoted || singleQuoted) {
                return value.substring(1, value.length() - 1);
            }
        }

        return value;
    }

}
