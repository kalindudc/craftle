package com.craftle_mod.client.gui.machine;

import com.craftle_mod.api.constants.GUIConstants;
import com.craftle_mod.client.gui.EnergyGui;
import com.craftle_mod.common.inventory.container.base.EnergyContainer;
import com.craftle_mod.common.inventory.container.machine.GeneratorContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GeneratorGui<T extends EnergyContainer> extends EnergyGui<T> {

    public GeneratorGui(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        double burnPercent = ((GeneratorContainer) this.getContainer()).getBurnPercentage();

        // burn progress
        Minecraft.getInstance().textureManager.bindTexture(GUIConstants.BURN_BAR);

        int width = GUIConstants.BURN_BAR_WIDTH;
        int height = GUIConstants.BURN_BAR_HEIGHT;
        int x = calculateX(GUIConstants.BURN_BAR_X);
        int y = calculateY(GUIConstants.BURN_BAR_Y);
        int textureX = 0;
        int textureY = 0;
        blit(x, y, textureX, textureY, width, height, width, GUIConstants.BURN_BAR_HEIGHT * 2);

        if (burnPercent > 0) {
            // blit something
            // the energy percent
            height = (int) Math.ceil(GUIConstants.BURN_BAR_HEIGHT * (1D - burnPercent));
            y = calculateY(GUIConstants.BURN_BAR_Y + GUIConstants.BURN_BAR_HEIGHT - height);

            textureY = (GUIConstants.BURN_BAR_HEIGHT * 2) - height;

            blit(x, y, textureX, textureY, width, height, width, GUIConstants.BURN_BAR_HEIGHT * 2);
        }
    }
}
