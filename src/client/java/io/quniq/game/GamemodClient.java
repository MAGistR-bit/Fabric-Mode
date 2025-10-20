package io.quniq.game;

import io.quniq.game.gui.CustomScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class GamemodClient implements ClientModInitializer {

    private static KeyMapping openScreenKey;

    @Override
    public void onInitializeClient() {
        // Экран открывается при нажатии на кнопку M
        openScreenKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.game_mod.open_screen",
                GLFW.GLFW_KEY_M,
                "key.categories.misc"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openScreenKey.consumeClick()) {
                if (client.player != null) {
                    Minecraft.getInstance().setScreen(new CustomScreen());
                }
            }
        });
    }
}