package com.craftle_mod.client.gui.machine.crusher;

import com.craftle_mod.common.inventory.container.machine.crusher.CrusherContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/*
TODO: Factorize the screen gui.
 */
@OnlyIn(Dist.CLIENT)
public class CrusherScreen extends ContainerScreen<CrusherContainer> {

    // TODO: make static, store in a config and load through the factory
    private ResourceLocation backgroundTexture;

    public CrusherScreen(CrusherContainer screenContainer, PlayerInventory inv,
                         ITextComponent titleIn, ResourceLocation resourceLocation) {
        super(screenContainer, inv, titleIn);
        this.guiLeft           = 0;
        this.guiTop            = 0;
        this.xSize             = 175;
        this.ySize             = 165;
        this.backgroundTexture = resourceLocation;
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
        //        this.font
        //                .drawString(this.title.getFormattedText(), 8.0f, 6
        //                .0f, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(backgroundTexture);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.blit(x, y, 0, 0, this.xSize, this.ySize);
    }
}
