package com.craftlemod.common.screen;

import com.craftlemod.common.CraftleMod;
import com.craftlemod.common.block.machine.MachineBlock;
import com.craftlemod.common.blockentity.factory.FactoryBlockEntity;
import com.craftlemod.common.util.ColourUtil;
import com.craftlemod.common.util.ColourUtil.Colour;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Optional;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
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
        if (handler instanceof FactoryScreenHandler) {
            Inventory inv = ((FactoryScreenHandler) handler).getInventory();
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

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawForeground(matrices, mouseX, mouseY);

        int screenX = 10 + 1;
        int screenY = 19 + 1;
        int screenWidth = 158;
        int screenHeight = 105;

        if (!(handler instanceof FactoryScreenHandler factoryScreenHandler)) {
            CraftleMod.LOGGER.error("break 1");
            return;
        }

        if (!(factoryScreenHandler.getInventory() instanceof FactoryBlockEntity entity)) {
            CraftleMod.LOGGER.error("break 2");
            CraftleMod.LOGGER.error(factoryScreenHandler.getInventory());
            return;
        }

        if (entity.getFactoryConfig() == null) {
            CraftleMod.LOGGER.error(entity);
            return;
        }

        Colour labelColour = ColourUtil.createColour(180, 180, 180);
        Colour defaultColour = ColourUtil.createColour(210, 180, 78);
        Colour activeColour = ColourUtil.createColour(30, 255, 100);
        if (!entity.isFactoryActive()) {
            activeColour = ColourUtil.createColour(255, 45, 80);
        }

        drawTextPairs(matrices, "Factory active: ", String.valueOf(entity.isFactoryActive()), screenX, screenY, 1, labelColour.encode(), activeColour.encode());
        drawTextPairs(matrices, "Height: ", String.valueOf(entity.getFactoryConfig().height()), screenX, screenY, 2, labelColour.encode(), defaultColour.encode());
    }

    public void drawTextPairs(MatrixStack matrices, String key, String value, int startX, int startY, int rowPos, int color1, int color2) {
        textRenderer.draw(matrices, key, startX, startY + ((rowPos - 1) * 12), color1);
        textRenderer.draw(matrices, value, startX + (key.length() * 5), startY + ((rowPos - 1) * 12), color2);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
}
