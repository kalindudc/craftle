package com.craftle_mod.client.gui;

import com.craftle_mod.api.CraftleExceptions.CraftleContainerException;
import com.craftle_mod.api.constants.GUIConstants;
import com.craftle_mod.client.util.CraftleGuiUtils;
import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.inventory.container.base.EnergyContainer;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import com.craftle_mod.common.util.EnergyUtils.EnergyConverter;
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
        CraftleGuiUtils.drawExtendedGUI(GUIConstants.BASE_2, 6, 6,
            getGuiLeft() + GUIConstants.INFO_SCREEN_OFFSET_X,
            getGuiTop() + GUIConstants.INFO_SCREEN_OFFSET_Y,
            ((PoweredMachineTileEntity) this.getEntity()).getInfoScreenWidth(),
            ((PoweredMachineTileEntity) this.getEntity()).getInfoScreenHeight());

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

            EnergyConverter energyStoredConverter = new EnergyConverter(
                this.getContainer().getEnergyContainer().getEnergy());
            EnergyConverter capacityConverter = new EnergyConverter(
                this.getContainer().getEnergyContainer().getCapacity());
            EnergyConverter inputConverter = new EnergyConverter(
                this.getContainer().getInjectRate());
            EnergyConverter outputConverter = new EnergyConverter(
                this.getContainer().getExtractRate());

            double percent =
                (this.getContainer().getEnergyContainer().getEnergy() / this.getContainer()
                    .getEnergyContainer().getCapacity()) * 100D;

            // setup icons
            Minecraft.getInstance().getTextureManager().bindTexture(GUIConstants.ICONS);

            blit(12, 15, 0, 0, GUIConstants.ICON_SIZE, GUIConstants.ICON_SIZE, 256, 256);
            blit(12, 28, 10, 0, GUIConstants.ICON_SIZE, GUIConstants.ICON_SIZE, 256, 256);
            blit(12, 41, 20, 0, GUIConstants.ICON_SIZE, GUIConstants.ICON_SIZE, 256, 256);
            blit(12, 54, 30, 0, GUIConstants.ICON_SIZE, GUIConstants.ICON_SIZE, 256, 256);

            // draw text
            this.font.drawString(String
                    .format("%.02f %s", capacityConverter.getEnergy(), capacityConverter.getUnit()),
                25.0f, 16.0f, 13816530);
            this.font.drawString(String.format("%.02f %s", energyStoredConverter.getEnergy(),
                energyStoredConverter.getUnit()), 25.0f, 29.0f, 6805014);
            this.font.drawString(
                String.format("%.02f %s", inputConverter.getEnergy(), inputConverter.getUnit()),
                25.0f, 42.0f, 6805014);
            this.font.drawString(
                String.format("%.02f %s", outputConverter.getEnergy(), outputConverter.getUnit()),
                25.0f, 55.0f, 14823215);

            if (CraftleGuiUtils.isWithinBounds(mouseX, mouseY, GUIConstants.ENERGY_BAR_X + offsetX,
                GUIConstants.ENERGY_BAR_Y + offsetY,
                GUIConstants.ENERGY_BAR_X + GUIConstants.ENERGY_BAR_WIDTH + offsetX,
                GUIConstants.ENERGY_BAR_Y + GUIConstants.ENERGY_BAR_HEIGHT + offsetY)) {
                GuiUtils.drawHoveringText(new ArrayList<String>() {{
                                              add(String.format("%.02f %s", percent, "%"));
                                          }}, mouseX - offsetX, mouseY - offsetY, this.xSize, this.ySize, this.xSize,
                    this.font);
            }


        } else {
            Craftle.LOGGER
                .warn("Invalid Screen", new CraftleContainerException("Invalid tile entity."));
        }
    }
}
