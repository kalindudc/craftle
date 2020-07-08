package com.craftle_mod.client.gui;

import com.craftle_mod.api.constants.GUIConstants;
import com.craftle_mod.client.util.CraftleGuiUtils;
import com.craftle_mod.common.inventory.container.base.CraftleContainer;
import com.craftle_mod.common.inventory.slot.SlotConfig;
import com.craftle_mod.common.tile.base.CraftleTileEntity;
import com.craftle_mod.common.tile.base.IHasExtraContainerSlots;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CraftleGui<T extends CraftleContainer> extends ContainerScreen<T> {

    private final CraftleTileEntity entity;

    public CraftleGui(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {

        super(screenContainer, inv, titleIn);
        this.entity = screenContainer.getEntity();
    }

    @Override
    public void init() {
        super.init();
        initHelper();
    }

    private void initHelper() {
    }

    public CraftleTileEntity getEntity() {
        return entity;
    }

    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

        CraftleGuiUtils
            .drawExtendedGUI(GUIConstants.BASE, 6, 6, getGuiLeft(), getGuiTop(), getXSize(),
                getYSize());

        // render extra container slots
        if (this.entity instanceof IHasExtraContainerSlots) {
            SlotConfig config = ((IHasExtraContainerSlots) entity).getExtraContainerSlotsConfig();

            int top = getGuiTop() + GUIConstants.EXTRA_CONTAINER_TOP_OFFSET;
            int left = getGuiLeft() + getXSize() + 1;
            int borderSize = 6;
            int width = ((borderSize + 1) * 2) + (config.getNumCols() * config.getSlotSize());
            int height = ((borderSize + 1) * 2) + (config.getNumRows() * config.getSlotSize());

            CraftleGuiUtils
                .drawExtendedGUI(GUIConstants.BASE, borderSize, borderSize, left, top, width,
                    height);

            renderSlotsFromConfig(config);
        }

        // Render all the other slots
        for (SlotConfig config : entity.getSlotData()) {

            renderSlotsFromConfig(config);
        }
    }

    private void renderSlotsFromConfig(SlotConfig config) {
        Minecraft.getInstance().textureManager.bindTexture(config.getSlotType().getSlotResource());

        for (int row = 0; row < config.getNumRows(); row++) {
            for (int col = 0; col < config.getNumCols(); col++) {

                blit(calculateX(config.getX(col) - 1), calculateY(config.getY(row) - 1), 0, 0,
                    config.getSlotSize(), config.getSlotSize(), config.getSlotSize(),
                    config.getSlotSize());
            }
        }
    }

    public int calculateX(int x) {
        return ((this.width - this.xSize) / 2) + x;
    }

    public int calculateY(int y) {
        return ((this.height - this.ySize) / 2) + y;
    }

    public void drawProgressVerticalBar(ResourceLocation resource, int width, int height, int x,
        int y, double percentage) {
        
    }
}
