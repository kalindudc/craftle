package com.craftle_mod.common.inventory.slot;

import net.minecraft.inventory.IInventory;

public class SlotConfig {


    private final int numCols;
    private final int numRows;
    private final int startingIndex;
    private IInventory entity;
    private final int startX;
    private final int startY;
    private final int slotSize;

    public SlotConfig(int numCols, int numRows, IInventory entity, int startingIndex, int startX,
        int startY, int slotSize) {
        this.numCols = numCols;
        this.numRows = numRows;
        this.startingIndex = startingIndex;
        this.entity = entity;
        this.startX = startX;
        this.startY = startY;
        this.slotSize = slotSize;
    }

    public SlotConfig(int startX, int startY, int slotSize) {
        this(0, 0, null, 0, startX, startY, slotSize);
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

    public IInventory getEntity() {
        return entity;
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

    public void setEntity(IInventory entity) {
        this.entity = entity;
    }
}
