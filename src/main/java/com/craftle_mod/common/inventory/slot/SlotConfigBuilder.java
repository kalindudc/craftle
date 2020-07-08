package com.craftle_mod.common.inventory.slot;

import com.craftle_mod.api.constants.ContainerConstants;
import com.craftle_mod.common.inventory.slot.SlotConfig.SlotType;
import net.minecraft.inventory.IInventory;

public class SlotConfigBuilder {

    public static SlotConfigBuilder create() {
        return new SlotConfigBuilder();
    }

    private final SlotConfig config;

    private SlotConfigBuilder() {
        this.config = new SlotConfig(1, 1, null, 0, 0, 0, ContainerConstants.TOTAL_SLOT_SIZE);
    }

    public SlotConfig build() {
        return config;
    }

    public SlotConfigBuilder inventory(IInventory entity) {
        this.config.setInventory(entity);
        return this;
    }

    public SlotConfigBuilder numCols(int numCols) {
        this.config.setNumCols(numCols);
        return this;
    }

    public SlotConfigBuilder numRows(int numRows) {
        this.config.setNumRows(numRows);
        return this;
    }

    public SlotConfigBuilder startingIndex(int startingIndex) {
        this.config.setStartingIndex(startingIndex);
        return this;
    }

    public SlotConfigBuilder startX(int startX) {
        this.config.setStartX(startX);
        return this;
    }

    public SlotConfigBuilder startY(int startY) {
        this.config.setStartY(startY);
        return this;
    }

    public SlotConfigBuilder slotSize(int slotSize) {
        this.config.setSlotSize(slotSize);
        return this;
    }

    public SlotConfigBuilder slotType(SlotType type) {
        this.config.setSlotType(type);
        return this;
    }

}
