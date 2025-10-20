package io.quniq.game.config;

import io.quniq.game.entity.MessageEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
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
            
            // Настройки подключения к PostgreSQL
            config.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            config.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/minecraft_messages");
            config.setProperty("hibernate.connection.username", "minecraft_user");
            config.setProperty("hibernate.connection.password", "minecraft_password");
            
            // Настройки Hibernate
            config.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            config.setProperty("hibernate.hbm2ddl.auto", "update");
            config.setProperty("hibernate.show_sql", "true");
            config.setProperty("hibernate.format_sql", "true");
            
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
}
