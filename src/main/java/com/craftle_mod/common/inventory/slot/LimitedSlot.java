package com.craftle_mod.common.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;

public class LimitedSlot extends Slot {

    private final int stackSize;

    public LimitedSlot(IInventory inventoryIn, int index, int xPosition, int yPosition,
        int stackSize) {
        super(inventoryIn, index, xPosition, yPosition);
        this.stackSize = stackSize;
    }

    @Override
    public int getSlotStackLimit() {
        return stackSize;
    }
}
