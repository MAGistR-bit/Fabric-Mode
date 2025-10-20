package io.quniq.game.gui;

import io.quniq.game.network.ClientMessageSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class CustomScreen extends Screen {

    private EditBox input;
    private Button sendButton;

    public CustomScreen() {
        super(Component.literal("Message Sender"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        input = new EditBox(this.font, centerX - 100, centerY - 10,
                200, 20, Component.literal("Enter text"));
        input.setMaxLength(256);
        input.setFocused(true);
        addRenderableWidget(input);

        sendButton = Button.builder(Component.literal("Send"), b -> onSend())
                .bounds(centerX - 40, centerY + 20, 80, 20)
                .build();
        addRenderableWidget(sendButton);
    }

    /**
     * Отправка сообщения на сервер при нажатии на кнопку {@code Send}
     */
    private void onSend() {
        String text = input.getValue().trim();
        if (!text.isEmpty()) {
            ClientMessageSender.sendToServer(text);
            Minecraft.getInstance().setScreen(null);
        }
    }

    /**
     * Вызывается для каждого кадра.
     * <p>Из этого метода можно получить доступ к контексту отрисовки и положению мыши.</p>
     * @param graphics контекст отрисовки
     * @param mouseX положение курсора мыши по оси Х
     * @param mouseY положение курсора мыши по оси Y
     * @param delta разница
     */
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(graphics, mouseX, mouseY, delta);
        super.render(graphics, mouseX, mouseY, delta);
        graphics.drawString(this.font, Component.literal("Enter message:"),
                this.width / 2 - 100,
                this.height / 2 - 25,
                0xFFFFFF,
                false);
    }
}


