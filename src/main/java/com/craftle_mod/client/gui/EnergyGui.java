package com.craftle_mod.client.gui;

import com.craftle_mod.api.CraftleExceptions.CraftleContainerException;
import com.craftle_mod.api.constants.GUIConstants;
import com.craftle_mod.client.util.CraftleGuiUtils;
import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.inventory.container.base.EnergyContainer;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import com.craftle_mod.common.util.EnergyUtils;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class EnergyGui<T extends EnergyContainer> extends CraftleGui<T> {

    public EnergyGui(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        // info screen
        CraftleGuiUtils.drawExtendedGUI(GUIConstants.BASE_ALT, 6, 6,
            getGuiLeft() + GUIConstants.INFO_SCREEN_OFFSET_X,
            getGuiTop() + GUIConstants.INFO_SCREEN_OFFSET_Y, GUIConstants.INFO_SCREEN_WIDTH,
            GUIConstants.INFO_SCREEN_HEIGHT);

        // energy container
        Minecraft.getInstance().textureManager.bindTexture(GUIConstants.ENERGY_BAR_VERTICAL);

        // draw the animations and other things
        double energy = this.getContainer().getEnergyContainer().getEnergy();
        double maxEnergy = this.getContainer().getEnergyContainer().getCapacity();
        double energyPercent = energy / maxEnergy;

        int width = GUIConstants.ENERGY_BAR_WIDTH;
        int height = GUIConstants.ENERGY_BAR_HEIGHT;
        int x = calculateX(GUIConstants.ENERGY_BAR_X);
        int y = calculateY(GUIConstants.ENERGY_BAR_Y);
        int textureX = 0;
        int textureY = 0;
        this.blit(x, y, textureX, textureY, width, height);

        blit(x, y, textureX, textureY, width, height, width * 2, height);

        if (energyPercent > 0) {
            // blit something
            // the energy percent
            height = (int) Math.ceil(GUIConstants.ENERGY_BAR_HEIGHT * (energyPercent));
            y = calculateY(GUIConstants.ENERGY_BAR_Y + GUIConstants.ENERGY_BAR_HEIGHT - height);

            textureX = GUIConstants.ENERGY_BAR_WIDTH;
            textureY = GUIConstants.ENERGY_BAR_HEIGHT - height;

            blit(x, y, textureX, textureY, width, height, width * 2,
                GUIConstants.ENERGY_BAR_HEIGHT);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        if (getEntity() instanceof PoweredMachineTileEntity) {
            PoweredMachineTileEntity entity = (PoweredMachineTileEntity) this.getEntity();
            int offsetX = (this.width - this.xSize) / 2;
            int offsetY = (this.height - this.ySize) / 2;

            String units = EnergyUtils.getUnitForTierBlock(entity.getCraftleMachineTier());

            double energy = EnergyUtils.getJoulesForTierBlock(entity.getCraftleMachineTier(),
                this.getContainer().getEnergyContainer().getEnergy());

            double capacity = EnergyUtils.getJoulesForTierBlock(entity.getCraftleMachineTier(),
                this.getContainer().getEnergyContainer().getCapacity());

            double input = this.getContainer().getInjectRate();
            double output = this.getContainer().getExtractRate();

            this.font.drawString("Max: ", 12.0f, 14.0f, 13816530);
            this.font
                .drawString(String.format("%.02f %s", capacity, units), 34.0f, 14.0f, 13816530);

            this.font.drawString("Energy: ", 12.0f, 27.0f, 6805014);
            this.font.drawString(String.format("%.02f %s", energy, units), 52.0f, 27.0f, 6805014);

            this.font.drawString("In: ", 12.0f, 41.0f, 6805014);
            this.font.drawString(String.format("%.02f %s", (input), units), 32.0f, 41.0f, 6805014);

            this.font.drawString("Out: ", 12.0f, 54.0f, 14823215);
            this.font
                .drawString(String.format("%.02f %s", (output), units), 32.0f, 54.0f, 14823215);

            if (CraftleGuiUtils.isWithinBounds(mouseX, mouseY, GUIConstants.ENERGY_BAR_X + offsetX,
                GUIConstants.ENERGY_BAR_Y + offsetY,
                GUIConstants.ENERGY_BAR_X + GUIConstants.ENERGY_BAR_WIDTH + offsetX,
                GUIConstants.ENERGY_BAR_Y + GUIConstants.ENERGY_BAR_HEIGHT + offsetY)) {
                GuiUtils.drawHoveringText(new ArrayList<String>() {{
                                              add(String.format("Energy: %.02f kJ", entity.getEnergyContainer().getEnergy()));
                                              add(String.format("Input: %.02f kJ", input));
                                              add(String.format("Output: %.02f kJ", output));

                                          }}, mouseX - offsetX, mouseY - offsetY, this.xSize, this.ySize, this.xSize,
                    this.font);
            }
        } else {
            Craftle.LOGGER
                .warn("Invalid Screen", new CraftleContainerException("Invalid tile entity."));
        }
    }
}
