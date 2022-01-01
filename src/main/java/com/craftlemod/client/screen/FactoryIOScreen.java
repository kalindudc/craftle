package com.craftlemod.client.screen;

import com.craftlemod.common.blockentity.factory.FactoryIOBlockEntity;
import com.craftlemod.common.screen.FactoryIOScreenHandler;
import com.craftlemod.common.util.ColourUtil;
import com.craftlemod.common.util.ColourUtil.Colour;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class FactoryIOScreen extends FactoryScreen {

    public FactoryIOScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawForeground(matrices, mouseX, mouseY);

        int screenX = 10 + 1;
        int screenY = 19 + 1;
        int screenWidth = 158;
        int screenHeight = 105;

        if (!(handler instanceof FactoryIOScreenHandler factoryIOScreenHandler)) {
            return;
        }

        if (!(factoryIOScreenHandler.getInventory() instanceof FactoryIOBlockEntity entity)) {
            return;
        }

        Colour labelColour = ColourUtil.createColour(180, 180, 180);
        Colour defaultColour = ColourUtil.createColour(210, 180, 78);
        Colour errorColour = ColourUtil.createColour(210, 82, 82);
        Colour activeColour = ColourUtil.createColour(30, 255, 100);

        String controller = "null";
        if (entity.getEntityControllerPos() == null) {
            activeColour = errorColour;
        } else {
            assert MinecraftClient.getInstance().world != null;
            Block controllerBlock = MinecraftClient.getInstance().world.getBlockState(entity.getEntityControllerPos()).getBlock();
            controller = new TranslatableText(controllerBlock.getTranslationKey()).getString();
        }

        drawWrappedText(matrices, "Factory Information", screenX, screenY, screenWidth, defaultColour.encode());
        screenY = screenY + 15;
        
        drawWrappedText(matrices, "Controller: ", screenX, screenY, screenWidth, labelColour.encode());
        drawWrappedText(matrices, controller, screenX + (12 * 5), screenY, screenWidth - (12 * 5), activeColour.encode());
    }
}
