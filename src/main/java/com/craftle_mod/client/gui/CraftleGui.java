package com.craftle_mod.client.gui;

import com.craftle_mod.api.constants.GUIConstants;
import com.craftle_mod.client.util.CraftleGuiUtils;
import com.craftle_mod.common.inventory.container.base.CraftleContainer;
import com.craftle_mod.common.inventory.slot.SlotConfig;
import com.craftle_mod.common.tile.base.CraftleTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
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

        // Render all the other slots
        for (SlotConfig config : entity.getSlotData()) {

            Minecraft.getInstance().textureManager
                .bindTexture(config.getSlotType().getSlotResource());

            for (int row = 0; row < config.getNumRows(); row++) {
                for (int col = 0; col < config.getNumCols(); col++) {

                    blit(calculateX(config.getX(col) - 1), calculateY(config.getY(row) - 1), 0, 0,
                        config.getSlotSize(), config.getSlotSize(), config.getSlotSize(),
                        config.getSlotSize());
                }
            }
        }

        Minecraft.getInstance().textureManager
            .bindTexture(entity.getMainInventorySlotConfig().getSlotType().getSlotResource());

        // player inventory slots
        int startX = entity.getMainInventorySlotConfig().getStartX();
        int startY = entity.getMainInventorySlotConfig().getStartY();
        int totalSlotSpaceSize = entity.getMainInventorySlotConfig().getSlotSize();
        // Main Player Inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                blit(calculateX(entity.getMainInventorySlotConfig().getX(col) - 1),
                    calculateY(entity.getMainInventorySlotConfig().getY(row) - 1), 0, 0,
                    totalSlotSpaceSize, totalSlotSpaceSize, totalSlotSpaceSize, totalSlotSpaceSize);
            }
        }

        // Hot Bar
        int hotbarY = startY + (totalSlotSpaceSize * 3) + 4;
        for (int col = 0; col < 9; col++) {
            blit(calculateX(entity.getMainInventorySlotConfig().getX(col) - 1),
                calculateY(hotbarY - 1), 0, 0, totalSlotSpaceSize, totalSlotSpaceSize,
                totalSlotSpaceSize, totalSlotSpaceSize);
        }
    }

    public int calculateX(int x) {
        return ((this.width - this.xSize) / 2) + x;
    }

    public int calculateY(int y) {
        return ((this.height - this.ySize) / 2) + y;
    }
}
