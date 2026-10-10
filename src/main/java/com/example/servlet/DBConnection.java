package com.example.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBConnection {

    private static final String ENV_FILE_PROPERTY = "campus.env.file";
    private static final String ENV_FILE_VARIABLE = "CAMPUS_ENV_FILE";

    public static Connection getConnection() throws SQLException {
        Map<String, String> dotEnv = loadDotEnv();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC driver is missing.", e);
        }

        String url = setting("DB_URL", dotEnv);
        String user = setting("DB_USER", dotEnv);
        String password = setting("DB_PASSWORD", dotEnv);

        return DriverManager.getConnection(url, user, password);
    }

    private static String setting(String name, Map<String, String> dotEnv)
            throws SQLException {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            value = dotEnv.get(name);
        }
        if (value == null || value.isBlank()) {
            throw new SQLException(
                "Missing required database configuration: " + name
                + ". Set it as an environment variable or in the local .env file.");
        }
        return value;
    }

    private static Map<String, String> loadDotEnv() throws SQLException {
        String configuredPath = System.getProperty(ENV_FILE_PROPERTY);
        if (configuredPath == null || configuredPath.isBlank()) {
            configuredPath = System.getenv(ENV_FILE_VARIABLE);
        }
        Path envFile = configuredPath == null || configuredPath.isBlank()
            ? Path.of(".env")
            : Path.of(configuredPath);

        if (!Files.exists(envFile)) {
            return Map.of();
        }
        if (!Files.isRegularFile(envFile)) {
            throw new SQLException("The configured .env path is not a file.");
        }

        try {
            List<String> lines = Files.readAllLines(envFile, StandardCharsets.UTF_8);
            Map<String, String> values = new HashMap<>();
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                int separator = line.indexOf('=');
                if (separator <= 0) {
                    throw new SQLException(
                        "Invalid .env entry on line " + (i + 1)
                        + "; expected KEY=VALUE.");
                }

                String name = line.substring(0, separator).trim();
                String value = line.substring(separator + 1).trim();
                if (name.isEmpty()) {
                    throw new SQLException(
                        "Invalid .env entry on line " + (i + 1)
                        + "; expected KEY=VALUE.");
                }
                if (value.length() >= 2
                        && ((value.startsWith("\"") && value.endsWith("\""))
                            || (value.startsWith("'") && value.endsWith("'")))) {
                    value = value.substring(1, value.length() - 1);
                }
                values.put(name, value);
            }
            return values;
        } catch (java.io.IOException e) {
            throw new SQLException("Unable to read the configured .env file.", e);
        }
    }
}