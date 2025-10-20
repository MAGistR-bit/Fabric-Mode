package io.quniq.game.repository;

import io.quniq.game.entity.MessageEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

public class MessageRepository {
    
    private final EntityManager entityManager;
    
    public MessageRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Сохранить сообщение в БД
     * @param message сообщение, которое нужно сохранить
     */
    public void save(MessageEntity message) {
        entityManager.getTransaction().begin();
        entityManager.persist(message);
        entityManager.getTransaction().commit();
    }

    /**
     * Найти сообщение по ID
     * @param id идентификатор сообщения
     * @return сообщение, который имеет необходимый идентификатор
     */
    public MessageEntity findById(Long id) {
        return entityManager.find(MessageEntity.class, id);
    }

    /**
     * Найти сообщения от конкретного игрока
     * @param uuid идентификатор игрока
     * @return список сообщений, которые отправлял игрок
     */
    public List<MessageEntity> findByUuid(UUID uuid) {
        TypedQuery<MessageEntity> query = entityManager.createQuery(
            "SELECT m FROM MessageEntity m WHERE m.uuid = :uuid", 
            MessageEntity.class
        );
        query.setParameter("uuid", uuid);
        return query.getResultList();
    }

    /**
     * Найти сообщения по тексту (поиск)
     * @param text текст, который требуется найти
     * @return список сообщений, содержащий указанный текст
     */
    public List<MessageEntity> findByTextContaining(String text) {
        TypedQuery<MessageEntity> query = entityManager.createQuery(
            "SELECT m FROM MessageEntity m WHERE LOWER(m.text) LIKE LOWER(:text)", 
            MessageEntity.class
        );
        query.setParameter("text", "%" + text + "%");
        return query.getResultList();
    }

    /**
     * Подсчитать количество сообщений от игрока
     * @param uuid уникальный идентификатор игрока
     * @return количество сообщений, который отправил игрок
     */
    public long countByUuid(UUID uuid) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(m) FROM MessageEntity m WHERE m.uuid = :uuid", 
            Long.class
        );
        query.setParameter("uuid", uuid);
        return query.getSingleResult();
    }

    /**
     * Получить все сообщения
     * @return список, содержащий все сообщения
     */
    public List<MessageEntity> findAll() {
        TypedQuery<MessageEntity> query = entityManager.createQuery(
            "SELECT m FROM MessageEntity m", 
            MessageEntity.class
        );
        return query.getResultList();
    }
}
