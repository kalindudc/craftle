package com.craftlemod.client.screen;

import com.craftlemod.common.blockentity.factory.FactoryBlockEntity;
import com.craftlemod.common.screen.FactoryControllerScreenHandler;
import com.craftlemod.common.util.ColourUtil;
import com.craftlemod.common.util.ColourUtil.Colour;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

public class FactoryControllerScreen extends FactoryScreen {


    public FactoryControllerScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawForeground(matrices, mouseX, mouseY);

        int screenX = 10 + 1;
        int screenY = 19 + 1;
        int screenWidth = 158;
        int screenHeight = 105;

        if (!(handler instanceof FactoryControllerScreenHandler factoryControllerScreenHandler)) {
            return;
        }

        if (!(factoryControllerScreenHandler.getInventory() instanceof FactoryBlockEntity entity)) {
            return;
        }

        Colour labelColour = ColourUtil.createColour(180, 180, 180);
        Colour defaultColour = ColourUtil.createColour(210, 180, 78);
        Colour errorColour = ColourUtil.createColour(210, 82, 82);
        Colour activeColour = ColourUtil.createColour(30, 255, 100);
        if (!entity.isFactoryActive()) {
            activeColour = errorColour;
        }

        if (entity.getErrorString().length() > 0) {
            drawWrappedText(matrices, entity.getErrorString(), screenX, screenY + (4 * 12), screenWidth, errorColour.encode());
        }

        drawWrappedText(matrices, "Factory Information", screenX, screenY, screenWidth, defaultColour.encode());
        screenY = screenY + 15;

        drawTextPairs(matrices, "Factory active: ", String.valueOf(entity.isFactoryActive()), screenX, screenY, 1, labelColour.encode(), activeColour.encode());
        if (entity.getFactoryConfig() == null) {
            return;
        }
        drawTextPairs(matrices, "Height: ", String.valueOf(entity.getFactoryConfig().height()), screenX, screenY, 2, labelColour.encode(), defaultColour.encode());
        drawTextPairs(matrices, "Volume: ", String.valueOf(entity.getFactoryVolume()), screenX, screenY, 3, labelColour.encode(), defaultColour.encode());
        drawTextPairs(matrices, "# of intakes: ", String.valueOf(entity.getFactoryConfig().intakes().size()), screenX, screenY, 4, labelColour.encode(), defaultColour.encode());
        drawTextPairs(matrices, "# of exhausts: ", String.valueOf(entity.getFactoryConfig().exhausts().size()), screenX, screenY, 5, labelColour.encode(), defaultColour.encode());
    }
}
