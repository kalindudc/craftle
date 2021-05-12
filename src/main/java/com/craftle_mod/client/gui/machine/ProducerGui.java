package com.craftle_mod.client.gui.machine;

import com.craftle_mod.api.constants.GUIConstants;
import com.craftle_mod.client.gui.EnergyGui;
import com.craftle_mod.common.inventory.container.base.EnergyContainer;
import com.craftle_mod.common.inventory.container.machine.ProducerContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ProducerGui<T extends EnergyContainer> extends EnergyGui<T> {

    public ProducerGui(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        // producer progress
        double percentage = ((ProducerContainer) this.getContainer()).getProducingPercentage();

        // burn progress
        Minecraft.getInstance().textureManager.bindTexture(GUIConstants.ENERGY_PRODUCE_BAR);

        int width = GUIConstants.ENERGY_PRODUCE_BAR_WIDTH;
        int height = GUIConstants.ENERGY_PRODUCE_BAR_HEIGHT;
        int x = calculateX(GUIConstants.ENERGY_PRODUCE_BAR_X);
        int y = calculateY(GUIConstants.ENERGY_PRODUCE_BAR_Y);
        int textureX = 0;
        int textureY = 0;
        blit(x, y, textureX, textureY, width, height, width,
            GUIConstants.ENERGY_PRODUCE_BAR_HEIGHT * 2);

        if (percentage > 0) {
            // blit something
            // the energy percent
            height = (int) Math.ceil(GUIConstants.ENERGY_PRODUCE_BAR_HEIGHT * (1D - percentage));
            y = calculateY(
                GUIConstants.ENERGY_PRODUCE_BAR_Y + GUIConstants.ENERGY_PRODUCE_BAR_HEIGHT
                    - height);

            textureY = (GUIConstants.ENERGY_PRODUCE_BAR_HEIGHT * 2) - height;

            blit(x, y, textureX, textureY, width, height, width,
                GUIConstants.ENERGY_PRODUCE_BAR_HEIGHT * 2);
        }
    }
}
