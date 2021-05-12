package com.craftle_mod.common.inventory.slot;

import com.craftle_mod.api.ColorData;
import com.craftle_mod.api.constants.GUIConstants;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;

public class SlotConfig {

    public enum SlotType {
        DEFAULT(ColorData.GREY, GUIConstants.SLOT_DEFAULT, null),
        INJECT(ColorData.DARK_GREEN, GUIConstants.SLOT_INJECT, GUIConstants.SLOT_OVERLAY_INJECT),
        EXTRACT(ColorData.DARK_RED, GUIConstants.SLOT_EXTRACT, GUIConstants.SLOT_OVERLAY_EXTRACT),
        ENERGY(ColorData.YELLOW, GUIConstants.SLOT_ENERGY, GUIConstants.SLOT_OVERLAY_ENERGY);

        private final ColorData color;
        private final ResourceLocation slotResource;
        private final ResourceLocation overlayResource;

        SlotType(ColorData color, ResourceLocation slotResource, ResourceLocation overlayResource) {
            this.color = color;
            this.slotResource = slotResource;
            this.overlayResource = overlayResource;
        }

        public ColorData getColor() {
            return color;
        }

        public ResourceLocation getOverlayResource() {
            return overlayResource;
        }

        public ResourceLocation getSlotResource() {
            return slotResource;
        }
    }


    private int numCols;
    private int numRows;
    private int startingIndex;
    private IInventory inventory;
    private int startX;
    private int startY;
    private int slotSize;
    private SlotType slotType;
    private Slot slot;

    protected SlotConfig(int numCols, int numRows, IInventory inventory, int startingIndex,
        int startX, int startY, int slotSize) {
        this.numCols = numCols;
        this.numRows = numRows;
        this.startingIndex = startingIndex;
        this.inventory = inventory;
        this.startX = startX;
        this.startY = startY;
        this.slotSize = slotSize;
        this.slotType = SlotType.DEFAULT;
        this.slot = null;
    }

    public SlotConfig(int startX, int startY, int slotSize) {
        this(0, 0, null, 0, startX, startY, slotSize);
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public int getSlotSize() {
        return slotSize;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getStartingIndex() {
        return startingIndex;
    }

    public IInventory getInventory() {
        return inventory;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getIndex(int row, int col) {
        return (startingIndex + (row * numCols)) + col;
    }

    public int getX(int col) {
        return startX + (col * slotSize);
    }

    public int getY(int row) {
        return startY + (row * slotSize);
    }

    public void setInventory(IInventory inventory) {
        this.inventory = inventory;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public void setStartingIndex(int startingIndex) {
        this.startingIndex = startingIndex;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public void setSlotSize(int slotSize) {
        this.slotSize = slotSize;
    }

    public SlotType getSlotType() {
        return slotType;
    }

    public void setSlotType(SlotType type) {
        this.slotType = type;
    }

    public boolean isDefaultSlot() {
        return this.slotType == SlotType.DEFAULT;
    }
}
