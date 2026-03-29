package pt.com.ctrl.vault.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
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
    private static final String URL = getRequiredConfig("DB_URL");
    private static final String DB_USER = getRequiredConfig("DB_USER");
    private static final String DB_PASSWORD = getRequiredConfigAllowingEmpty("DB_PASSWORD");

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL não encontrado", e);
        }
    }

    public static Connection getConnection() {

        try {
        	// 🔍 DEBUG (temporary)
            /*System.out.println("URL = " + URL);
            System.out.println("USER = " + DB_USER);
            System.out.println("PASS = " + DB_PASSWORD);*/
            return DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
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

    private static String getRequiredConfig(String key) {
        String value = getConfig(key, null);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Configuracao obrigatoria ausente: " + key);
        }

        return value;
    }

    private static String getRequiredConfigAllowingEmpty(String key) {
        String envValue = System.getenv(key);
        if (envValue != null) {
            return envValue;
        }

        if (ENV_FILE_VALUES.containsKey(key)) {
            return ENV_FILE_VALUES.get(key);
        }

        throw new IllegalStateException("Configuracao obrigatoria ausente: " + key);
    }

    private static Map<String, String> loadEnvFile() {
        Path envFilePath = resolveEnvFilePath();
        if (envFilePath == null) {
            return Map.of();
        }

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
        Path configuredPath = resolveConfiguredEnvFilePath();
        if (configuredPath != null) {
            return configuredPath;
        }

        Path pathFromWorkingDirectory = findEnvInParents(Paths.get("").toAbsolutePath().normalize(), 6);
        if (pathFromWorkingDirectory != null) {
            return pathFromWorkingDirectory;
        }

        Path pathFromClasses = resolveFromClassesLocation();
        if (pathFromClasses != null) {
            return pathFromClasses;
        }

        Path pathFromCatalinaBase = resolveFromSystemDirectory("catalina.base");
        if (pathFromCatalinaBase != null) {
            return pathFromCatalinaBase;
        }

        Path pathFromCatalinaHome = resolveFromSystemDirectory("catalina.home");
        if (pathFromCatalinaHome != null) {
            return pathFromCatalinaHome;
        }

        return null;
    }

    private static Path resolveConfiguredEnvFilePath() {
        String configuredPath = System.getenv("CTRL_VAULT_ENV_FILE");
        if (configuredPath == null || configuredPath.isBlank()) {
            configuredPath = System.getProperty("ctrl.vault.env.file");
        }

        if (configuredPath == null || configuredPath.isBlank()) {
            return null;
        }

        Path path = Paths.get(configuredPath).toAbsolutePath().normalize();
        return Files.exists(path) ? path : null;
    }

    private static Path resolveFromClassesLocation() {
        try {
            URL location = ConnectionFactory.class.getProtectionDomain().getCodeSource().getLocation();
            if (location == null) {
                return null;
            }

            Path classesPath = Paths.get(location.toURI()).toAbsolutePath().normalize();
            return findEnvInParents(classesPath, 8);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    private static Path resolveFromSystemDirectory(String propertyName) {
        String directory = System.getProperty(propertyName);
        if (directory == null || directory.isBlank()) {
            return null;
        }

        return findEnvInParents(Paths.get(directory).toAbsolutePath().normalize(), 4);
    }

    private static Path findEnvInParents(Path startPath, int maxLevels) {
        Path current = startPath;

        for (int level = 0; current != null && level <= maxLevels; level++) {
            Path candidate = current.resolve(".env");
            if (Files.exists(candidate)) {
                return candidate;
            }

            current = current.getParent();
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
