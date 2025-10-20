package io.quniq.game.network;

import io.quniq.game.Gamemod;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record MessagePayload(byte[] data) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Gamemod.MOD_ID,
            "message");

    public static final CustomPacketPayload.Type<MessagePayload> TYPE =
            new CustomPacketPayload.Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, MessagePayload> CODEC = new StreamCodec<>() {
        @Override
        public @NotNull MessagePayload decode(RegistryFriendlyByteBuf buf) {
            byte[] bytes = buf.readByteArray();
            return new MessagePayload(bytes);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, MessagePayload payload) {
            buf.writeByteArray(payload.data);
        }
    };

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}


