package com.craftle_mod.client.gui.machine;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.inventory.container.machine.coal_generator.CoalGeneratorContainer;
import com.craftle_mod.common.tile.machine.CoalGeneratorTileEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CoalGeneratorScreen
        extends ContainerScreen<CoalGeneratorContainer> {

    private static final ResourceLocation        BACKGROUND_TEXTURE =
            new ResourceLocation(Craftle.MODID,
                                 "textures/gui/coal_generator.png");
    private              CoalGeneratorTileEntity entity;

    public CoalGeneratorScreen(CoalGeneratorContainer screenContainer,
                               PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.guiLeft = 0;
        this.guiTop  = 0;
        this.xSize   = 175;
        this.ySize   = 165;
        this.entity  = (CoalGeneratorTileEntity) screenContainer.getEntity();
        Craftle.logInfo("--------------\n--------------\n--------\n------ %d",
                        screenContainer.getEnergy());

    }

    @Override
    public void render(final int mouseX, final int mouseY,
                       final float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.font
                .drawString(this.title.getFormattedText(), 8.0f, 6.0f, 4210752);

        int offsetX = (this.width - this.xSize) / 2;
        int offsetY = (this.height - this.ySize) / 2;

        int energy = this.entity.getEnergyContainer().getEnergyStored();

        if (getBounds(mouseX, mouseY, 112 + offsetX, 12 + offsetY,
                      135 + offsetX, 73 + offsetY)) this.font
                .drawString(energy + "J/t", mouseX - offsetX + 8,
                            mouseY - offsetY - 5, 13785144);
    }


    public boolean getBounds(int x, int y, int xMin, int yMin, int xMax,
                             int yMax) {
        return x <= xMax && x >= xMin && y <= yMax && y >= yMin;
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks,
                                                   int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        // Screen #blit draws a part of the current texture
        // The parameters are (x, y, u, v, width, height)
        this.blit(x, y, 0, 0, this.xSize, this.ySize);

        // draw the animations and other things
        int energy    = this.entity.getEnergyContainer().getEnergyStored();
        int maxEnergy = this.entity.getEnergyContainer().getMaxEnergyStored();

        float cookPercent = this.entity.getCookTime() / 200F;
        //float cookPercent   = 1;
        float energyPercent = ((float) energy) / ((float) maxEnergy);

        int textureX;
        int textureY;
        int width;
        int height;

        if (cookPercent > 0) {
            width  = (int) Math.ceil(22 * (cookPercent)) + 1;
            height = 16;

            x        = ((this.width - this.xSize) / 2) + 80;
            y        = ((this.height - this.ySize) / 2) + 34;
            textureX = 177;
            textureY = 14;

            this.blit(x, y, textureX, textureY, width, height);

            // blit something
            Craftle.logInfo("%d %d %d %d", textureX, textureY, width, height);
        }

        if (energyPercent > 0) {
            // blit something
            // the energy percent
            width  = 23 + 1;
            height = (int) Math.ceil(61 * (energyPercent)) + 1;

            x        = ((this.width - this.xSize) / 2) + 112;
            y        = ((this.height - this.ySize) / 2) + (74 - height);
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
