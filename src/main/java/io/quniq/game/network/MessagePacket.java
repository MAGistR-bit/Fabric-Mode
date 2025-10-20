package io.quniq.game.network;

import io.quniq.game.MessageOuterClass;
import io.quniq.game.entity.MessageEntity;
import io.quniq.game.service.MessageService;
import net.minecraft.server.level.ServerPlayer;

public class MessagePacket {

    /**
     * Обработка полученного сообщения на сервере
     */
    public static void handleServer(ServerPlayer player, byte[] messageBytes) {
        try {
            MessageOuterClass.Message message = MessageOuterClass.Message.parseFrom(messageBytes);
            String text = message.getText();

            System.out.println("Received message from " + player.getName().getString() +
                    " (UUID: " + player.getUUID() + "): " + text);

            MessageService messageService = new MessageService();
            MessageEntity savedMessage = messageService.saveMessage(player, text);

            if (savedMessage != null) {
                System.out.println("Message successfully saved to database with ID: " + savedMessage.getId());
            } else {
                System.err.println("Failed to save message to database");
            }

        } catch (Exception e) {
            System.err.println("Failed to handle message payload: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
