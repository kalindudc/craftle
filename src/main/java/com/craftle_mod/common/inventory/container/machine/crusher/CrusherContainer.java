package com.craftle_mod.common.inventory.container.machine.crusher;


import com.craftle_mod.common.inventory.container.base.CraftleContainer;
import com.craftle_mod.common.registries.CraftleBlocks;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.MachineTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;

public class CrusherContainer extends CraftleContainer {

    private int numRows;
    private int numCols;


    public CrusherContainer(ContainerType<CrusherContainer> container, final int windowId,
                            final PlayerInventory playerInventory, final MachineTileEntity entity) {

        super(container, windowId, playerInventory, entity);
        initSlots();
    }

    public CrusherContainer(ContainerType<CrusherContainer> container, final int windowId,
                            final PlayerInventory playerInventory, final PacketBuffer data) {

        super(container, windowId, playerInventory, data);
        initSlots();
    }


    private int[] initSlotsHelper(CraftleBaseTier tier) {
        switch (tier) {

            case TIER_1:
                this.numRows = 2;
                this.numCols = 2;
                return new int[]{46, 17, 46, 53, 104, 25};
            case TIER_2:
                this.numRows = 2;
                this.numCols = 4;
                return new int[]{36, 17, 36, 53, 90, 25};
            case TIER_3:
                this.numRows = 4;
                this.numCols = 4;
                return new int[]{36, 17, 36, 53, 90, 7};
            case TIER_4:
            case UNLIMITED:
                this.numRows = 4;
                this.numCols = 6;
                return new int[]{12, 17, 12, 53, 62, 7};
            case BASIC:
            default:
                this.numRows = 0;
                this.numCols = 0;
                return new int[]{56, 17, 56, 53, 116, 35};
        }
    }

    @Override
    public void initSlots() {

        if (!(getEntity() instanceof MachineTileEntity)) {
            throw new IllegalStateException("Tile entity is not correct. ");
        }

        int[] slotsConfig =
                initSlotsHelper(((MachineTileEntity) getEntity()).getCraftleMachineTier());
        int totalSlotSize = 18;
        // Main Inventory
        //    base crusher slots
        //    inventory

        boolean base = numRows == 0 && numCols == 0;
        addContainerSlot(0, slotsConfig[0], slotsConfig[1]);
        // TODO: replace slot with custom fuel slot for machines
        addContainerSlot(1, slotsConfig[2], slotsConfig[3]);
        // TODO: replace the chest inventory with custom machine result slots

        if (base) {
            addContainerSlot(2, slotsConfig[4], slotsConfig[5]);
        } else {
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    // extra 9 + is to account for the hotbar
                    addContainerSlot((2 + (row * numCols)) + col,
                            slotsConfig[4] + (col * totalSlotSize),
                            slotsConfig[5] + (row * totalSlotSize));
                }
            }
        }

        // Main Player Inventory
        int startPlayerInvX = 8;
        int startPlayerInvY = 84;
        addPlayerInventorySlots(startPlayerInvX, startPlayerInvY, totalSlotSize);
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {

        if (!(getEntity() instanceof MachineTileEntity)) {
            throw new IllegalStateException("Tile entity is not correct. ");
        }

        switch (((MachineTileEntity) getEntity()).getCraftleMachineTier()) {

            case TIER_1:
                return isWithinUsableDistance(getCanInteractWithCallable(), playerIn,
                        CraftleBlocks.CRUSHER_TIER_1.get());
            case TIER_2:
                return isWithinUsableDistance(getCanInteractWithCallable(), playerIn,
                        CraftleBlocks.CRUSHER_TIER_2.get());
            case TIER_3:
                return isWithinUsableDistance(getCanInteractWithCallable(), playerIn,
                        CraftleBlocks.CRUSHER_TIER_3.get());
            case TIER_4:
            case UNLIMITED:
                return isWithinUsableDistance(getCanInteractWithCallable(), playerIn,
                        CraftleBlocks.CRUSHER_TIER_4.get());
            case BASIC:
            default:
                return isWithinUsableDistance(getCanInteractWithCallable(), playerIn,
                        CraftleBlocks.CRUSHER_BASIC.get());
        }
    }
}
