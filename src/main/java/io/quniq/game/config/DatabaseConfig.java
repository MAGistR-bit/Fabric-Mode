package io.quniq.game.config;

import io.quniq.game.entity.MessageEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;

public class DatabaseConfig {
    
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    
    /**
     * Инициализация подключения к БД
     */
    public static void initialize() {
        try {
            Configuration config = new Configuration();
            
            // Получаем переменные окружения с fallback значениями
            String dbHost = getEnvOrDefault("DB_HOST", "localhost");
            String dbPort = getEnvOrDefault("DB_PORT", "5432");
            String dbName = getEnvOrDefault("DB_NAME", "minecraft_messages");
            String dbUsername = getEnvOrDefault("DB_USERNAME", "minecraft_user");
            String dbPassword = getEnvOrDefault("DB_PASSWORD", "minecraft_password");
            
            // Настройки подключения к PostgreSQL (Docker)
            config.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            config.setProperty("hibernate.connection.url", 
                String.format("jdbc:postgresql://%s:%s/%s", dbHost, dbPort, dbName));
            config.setProperty("hibernate.connection.username", dbUsername);
            config.setProperty("hibernate.connection.password", dbPassword);
            
            // Дополнительные настройки для стабильности
            String poolSize = getEnvOrDefault("HIBERNATE_CONNECTION_POOL_SIZE", "5");
            config.setProperty("hibernate.connection.pool_size", poolSize);
            config.setProperty("hibernate.connection.autocommit", "false");
            config.setProperty("hibernate.connection.provider_disables_autocommit", "true");
            
            // Настройки Hibernate из переменных окружения
            config.setProperty("hibernate.dialect", 
                getEnvOrDefault("HIBERNATE_DIALECT", "org.hibernate.dialect.PostgreSQLDialect"));
            config.setProperty("hibernate.hbm2ddl.auto", 
                getEnvOrDefault("HIBERNATE_HBM2DDL_AUTO", "update"));
            config.setProperty("hibernate.show_sql", 
                getEnvOrDefault("HIBERNATE_SHOW_SQL", "true"));
            config.setProperty("hibernate.format_sql", 
                getEnvOrDefault("HIBERNATE_FORMAT_SQL", "true"));
            
            // Регистрация Entity
            config.addAnnotatedClass(MessageEntity.class);
            
            entityManagerFactory = config.buildSessionFactory();
            entityManager = entityManagerFactory.createEntityManager();
            
            System.out.println("Database connection initialized successfully!");
            
        } catch (Exception e) {
            System.err.println("Failed to initialize database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Получить EntityManager
     */
    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            initialize();
        }
        return entityManager;
    }
    
    /**
     * Закрыть подключение к БД
     */
    public static void shutdown() {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
    
    /**
     * Получить переменную окружения или значение по умолчанию
     * @param key ключ переменной окружения
     * @param defaultValue значение по умолчанию
     * @return значение переменной или значение по умолчанию
     */
    private static String getEnvOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        return value != null ? value : defaultValue;
    }
}
