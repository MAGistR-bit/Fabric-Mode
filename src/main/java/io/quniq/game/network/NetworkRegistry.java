package io.quniq.game.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

public class NetworkRegistry {

    /**
     * Регистрация payload-ов и обработчиков
     */
    public static void registerServerPackets() {
        // Регистрируем тип и кодек для нашего payload
        PayloadTypeRegistry.playC2S().register(MessagePayload.TYPE, MessagePayload.CODEC);

        // Регистрируем обработчик на сервере
        ServerPlayNetworking.registerGlobalReceiver(MessagePayload.TYPE, (payload, context) -> {
            ServerPlayer player = context.player();
            context.server().execute(() -> MessagePacket.handleServer(player, payload.data()));
        });
    }
}
