package hepl.DACSC.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigServer {
    private Properties properties;

    // Constructeur qui charge le fichier de configuration
    public ConfigServer(String configFilePath) throws IOException {
        properties = new Properties();
        try (InputStream input = new FileInputStream(configFilePath)) {
            properties.load(input);
        }
    }

    // Méthode alternative pour charger depuis le classpath
    public ConfigServer() throws IOException {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("serversetup.properties")) {
            if (input == null) {
                throw new IOException("Fichier config.properties introuvable");
            }
            properties.load(input);
        }
    }

    // Getters pour chaque propriété
    public int getPortConsultation() {
        return Integer.parseInt(properties.getProperty("PORT_CONSULTATION"));
    }

    public int getPoolSize() {
        return Integer.parseInt(properties.getProperty("POOL_SIZE"));
    }

    public String getDbUser() {
        return properties.getProperty("DB_USR");
    }

    public String getDbPassword() {
        return properties.getProperty("DB_PWD");
    }

    public String getDbLink() {
        return properties.getProperty("LINK_DB");
    }

    public String getDbDriver() {
        return properties.getProperty("DB_DRIVER");
    }

    // Getter générique
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}