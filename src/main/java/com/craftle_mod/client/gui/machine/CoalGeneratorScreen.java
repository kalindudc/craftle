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

    private static final ResourceLocation BACKGROUND_TEXTURE =
            new ResourceLocation(Craftle.MODID,
                                 "textures/gui/coal_generator.png");

    public CoalGeneratorScreen(CoalGeneratorContainer screenContainer,
                               PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.guiLeft = 0;
        this.guiTop  = 0;
        this.xSize   = 175;
        this.ySize   = 165;
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

        int energy = ((CoalGeneratorTileEntity) this.getContainer().getEntity())
                .getEnergyContainer().getEnergyStored();


        Craftle.logInfo("DRAW GUI: %d %d", energy,
                        this.getContainer().getEnergy());
        Craftle.logInfo("HASH: " +
                        ((CoalGeneratorTileEntity) this.getContainer()
                                                       .getEntity())
                                .hashCode());

        if (getBounds(mouseX, mouseY, 112 + offsetX, 12 + offsetY,
                      135 + offsetX, 73 + offsetY)) this.font
                .drawString(energy + "mJ/t", mouseX - offsetX + 8,
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
        this.blit(x, y, 0, 0, this.xSize, this.ySize);
    }
}
