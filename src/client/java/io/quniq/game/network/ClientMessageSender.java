package io.quniq.game.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import io.quniq.game.MessageOuterClass;

public class ClientMessageSender {
    private ClientMessageSender() {}

    /**
     * Отправляет сообщение на сервер, используется для GUI
     * @param text сообщение, которое нужно отправить на сервер
     */
    public static void sendToServer(String text) {
        try {
            MessageOuterClass.Message msg = MessageOuterClass.Message.newBuilder()
                    .setText(text)
                    .build();
            ClientPlayNetworking.send(new MessagePayload(msg.toByteArray()));
        } catch (Exception e) {
            System.err.println("Failed to send message payload: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


