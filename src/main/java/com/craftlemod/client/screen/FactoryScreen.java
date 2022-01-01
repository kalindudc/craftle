package com.craftlemod.client.screen;

import com.craftlemod.common.block.machine.MachineBlock;
import com.craftlemod.common.screen.FactoryControllerScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.font.TextHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class FactoryScreen extends HandledScreen<ScreenHandler> {

    private static final Identifier TEXTURE = new Identifier("craftle", "textures/gui/container/factory.png");

    public FactoryScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, getPositionText(handler).orElse(title));
        this.backgroundHeight = 222;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    private static Optional<Text> getPositionText(ScreenHandler handler) {
        if (handler instanceof FactoryControllerScreenHandler) {
            Inventory inv = ((FactoryControllerScreenHandler) handler).getInventory();
            if (!(inv instanceof MachineBlock block)) {
                return Optional.empty();
            }

            return Optional.of(new LiteralText(block.getTranslationKey()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    public void drawTextPairs(MatrixStack matrices, String key, String value, int startX, int startY, int rowPos, int color1, int color2) {
        textRenderer.draw(matrices, key, startX, startY + ((rowPos - 1) * 12), color1);
        textRenderer.draw(matrices, value, startX + (key.length() * 5), startY + ((rowPos - 1) * 12), color2);
    }

    public void drawWrappedText(MatrixStack matrices, String string, int startX, int startY, int maxWidth, int color) {
        TextHandler handler = textRenderer.getTextHandler();
        List<StringVisitable> segments = handler.wrapLines(string, maxWidth, Style.EMPTY);
        for (int i = 0; i < segments.size(); i++) {
            int textY = startY + (i * 12);
            textRenderer.draw(matrices, segments.get(i).getString(), startX, textY, color);
        }
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawForeground(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

}
