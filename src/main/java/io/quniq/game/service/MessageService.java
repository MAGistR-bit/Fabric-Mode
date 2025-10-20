package io.quniq.game.service;

import io.quniq.game.entity.MessageEntity;
import io.quniq.game.repository.MessageRepository;
import io.quniq.game.config.DatabaseConfig;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.UUID;

public class MessageService {
    
    private final MessageRepository messageRepository;
    
    public MessageService() {
        this.messageRepository = new MessageRepository(DatabaseConfig.getEntityManager());
    }
    
    /**
     * Сохранить сообщение от игрока
     * @param player игрок, отправивший сообщение
     * @param text текст сообщения
     * @return сохраненное сообщение
     */
    public MessageEntity saveMessage(ServerPlayer player, String text) {
        try {
            // Создаем новое сообщение
            MessageEntity message = new MessageEntity(player.getUUID(), text);
            
            // Сохраняем в БД
            messageRepository.save(message);
            
            System.out.println("Message saved to database: " + message);
            return message;
            
        } catch (Exception e) {
            System.err.println("Failed to save message: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Получить все сообщения от игрока
     * @param playerUuid UUID игрока
     * @return список сообщений
     */
    public List<MessageEntity> getMessagesByPlayer(UUID playerUuid) {
        try {
            return messageRepository.findByUuid(playerUuid);
        } catch (Exception e) {
            System.err.println("Failed to get messages by player: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
    
    /**
     * Найти сообщения по тексту
     * @param searchText текст для поиска
     * @return список найденных сообщений
     */
    public List<MessageEntity> searchMessages(String searchText) {
        try {
            return messageRepository.findByTextContaining(searchText);
        } catch (Exception e) {
            System.err.println("Failed to search messages: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
    
    /**
     * Получить количество сообщений от игрока
     * @param playerUuid UUID игрока
     * @return количество сообщений
     */
    public long getMessageCount(UUID playerUuid) {
        try {
            return messageRepository.countByUuid(playerUuid);
        } catch (Exception e) {
            System.err.println("Failed to get message count: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * Получить все сообщения
     * @return список всех сообщений
     */
    public List<MessageEntity> getAllMessages() {
        try {
            return messageRepository.findAll();
        } catch (Exception e) {
            System.err.println("Failed to get all messages: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
}
