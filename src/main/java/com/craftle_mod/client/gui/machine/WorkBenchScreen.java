package com.craftle_mod.client.gui.machine;

import com.craftle_mod.api.GUIConstants;
import com.craftle_mod.common.inventory.container.machine.WorkBenchContainer;
import com.craftle_mod.common.tile.machine.WorkBenchTileEntity;
import com.craftle_mod.common.util.EnergyUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class WorkBenchScreen extends ContainerScreen<WorkBenchContainer> {

    private WorkBenchTileEntity entity;

    public WorkBenchScreen(WorkBenchContainer screenContainer, PlayerInventory inv,
                           ITextComponent titleIn) {

        super(screenContainer, inv, titleIn);
        this.guiLeft = 0;
        this.guiTop  = 0;
        this.xSize   = 176;
        this.ySize   = 166;
        this.entity  = (WorkBenchTileEntity) screenContainer.getEntity();
    }

    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        this.font.drawString(this.title.getFormattedText(), 8.0f, 6.0f, 4210752);

        int offsetX = (this.width - this.xSize) / 2;
        int offsetY = (this.height - this.ySize) / 2;

        String unit = EnergyUtils.getUnitForTierItem(this.entity.getCraftleMachineTier());

        float energy = EnergyUtils.getJoulesForTierItem(this.entity.getCraftleMachineTier(),
                                                        this.entity.getEnergyContainer()
                                                                   .getEnergyStored());

        float input = EnergyUtils.getJoulesForTierItem(this.entity.getCraftleMachineTier(),
                                                       this.entity.getEnergyReceive());
        float capacity = EnergyUtils.getJoulesForTierItem(this.entity.getCraftleMachineTier(),
                                                          this.entity.getEnergyContainer()
                                                                     .getMaxEnergyStored());

        this.font.drawString("Max: ", 186.0f, 9.0f, 13816530);
        this.font.drawString(String.format("%.02f %s", capacity, unit), 231.0f, 9.0f, 13816530);

        this.font.drawString("Energy: ", 186.0f, 22.0f, 6805014);
        this.font.drawString(String.format("%.02f %s", energy, unit), 231.0f, 22.0f, 6805014);

        this.font.drawString("In: ", 186.0f, 35.0f, 6805014);
        this.font.drawString(String.format("%.02f %s", (input), unit), 231.0f, 35.0f, 6805014);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(GUIConstants.WORKBENCH);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        // Screen #blit draws a part of the current texture
        // The parameters are (x, y, u, v, width, height)
        this.blit(x, y, 0, 0, this.xSize, this.ySize);

        // draw the container
        this.blit(x + this.xSize, y, 30, 166, 104, 79);
        this.blit(x + this.xSize, y + 79, 134, 166, 104, 87);

        // draw the animations and other things
        long energy    = this.entity.getEnergyContainer().getEnergyStored();
        long maxEnergy = this.entity.getEnergyContainer().getMaxEnergyStored();

        float energyPercent = ((float) energy) / ((float) maxEnergy);

        int textureX;
        int textureY;
        int width;
        int height;

        if (energyPercent > 0) {
            // blit something
            // the energy percent
            width  = 8 + 1;
            height = (int) Math.ceil(70 * (energyPercent)) + 1;

            x        = ((this.width - this.xSize) / 2) + 159;
            y        = ((this.height - this.ySize) / 2) + (77 - height);
            textureX = 21;
            textureY = 237 - height;

            this.blit(x, y, textureX, textureY, width, height);
        }
    }

    // @formatter:off
    //Some blit param namings , thank you Mekanism
    //blit(int x, int y, int textureX, int textureY, int width, int height);
    //blit(int x, int y, TextureAtlasSprite icon, int width, int height);
    //blit(int x, int y, int textureX, int textureY, int width, int height, int textureWidth, int textureHeight);
    //blit(int x, int y, int zLevel, float textureX, float textureY, int width, int height, int textureWidth, int textureHeight);
    //blit(int x, int y, int desiredWidth, int desiredHeight, int textureX, int textureY, int width, int height, int textureWidth, int textureHeight);
    //innerBlit(int x, int endX, int y, int endY, int zLevel, int width, int height, float textureX, float textureY, int textureWidth, int textureHeight);
    // @formatter:on
}
