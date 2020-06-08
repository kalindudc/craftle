package com.craftle_mod.client.gui.storage;

import com.craftle_mod.api.GUIConstants;
import com.craftle_mod.common.inventory.container.storage.energy_matrix.EnergyMatrixContainer;
import com.craftle_mod.common.tile.storage.energy_matrix.EnergyMatrixTileEntity;
import com.craftle_mod.common.util.EnergyUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class EnergyMatrixScreen extends ContainerScreen<EnergyMatrixContainer>
        implements IHasContainer<EnergyMatrixContainer> {
    private EnergyMatrixTileEntity entity;

    public EnergyMatrixScreen(EnergyMatrixContainer screenContainer, PlayerInventory inv,
                              ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.guiLeft = 0;
        this.guiTop  = 0;
        this.xSize   = 175;
        this.ySize   = 165;
        this.entity  = (EnergyMatrixTileEntity) screenContainer.getEntity();
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

        int energy = this.entity.getEnergyContainer().getEnergyStored();

        float input  = EnergyUtils.joulesToKiloJoules(this.entity.getEnergyReceive());
        float output = EnergyUtils.joulesToKiloJoules(this.entity.getEnergyExtract());


        this.font.drawString("Max: ", 10.0f, 26.0f, 13816530);
        this.font.drawString(String.format("%.02f mJ", EnergyUtils
                                     .joulesToMegaJoules(this.entity.getEnergyContainer().getMaxEnergyStored())), 32.0f,
                             26.0f, 13816530);

        this.font.drawString("Energy: ", 10.0f, 39.0f, 6805014);
        this.font.drawString(String.format("%.02f kJ", EnergyUtils.joulesToKiloJoules(energy)),
                             51.0f, 39.0f, 6805014);

        this.font.drawString("In: ", 10.0f, 52.0f, 6805014);
        this.font.drawString(String.format("%.02f kJ/t", (input)), 24.0f, 52.0f, 6805014);

        this.font.drawString("Out: ", 10.0f, 64.0f, 14823215);
        this.font.drawString(String.format("%.02f kJ/t", (output)), 30.0f, 64.0f, 14823215);
    }


    public boolean getBounds(int x, int y, int xMin, int yMin, int xMax, int yMax) {
        return x <= xMax && x >= xMin && y <= yMax && y >= yMin;
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(GUIConstants.ENERGY_MATRIX);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        
        // Screen #blit draws a part of the current texture
        // The parameters are (x, y, u, v, width, height)
        this.blit(x, y, 0, 0, this.xSize, this.ySize);

        // draw the animations and other things
        int   energy        = this.entity.getEnergyContainer().getEnergyStored();
        int   maxEnergy     = this.entity.getEnergyContainer().getMaxEnergyStored();
        float energyPercent = ((float) energy) / ((float) maxEnergy);

        int textureX;
        int textureY;
        int width;
        int height;

        if (energyPercent > 0) {
            // blit something
            // the energy percent
            width  = 23 + 1;
            height = (int) Math.ceil(61 * (energyPercent)) + 1;

            x        = ((this.width - this.xSize) / 2) + 143;
            y        = ((this.height - this.ySize) / 2) + (75 - height);
            textureX = 177;
            textureY = 94 - height;

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
