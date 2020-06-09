package com.craftle_mod.common.inventory.container.storage.energy_matrix;

import com.craftle_mod.common.inventory.container.base.EnergyContainer;
import com.craftle_mod.common.registries.CraftleBlocks;
import com.craftle_mod.common.tile.base.MachineTileEntity;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;

public class EnergyMatrixContainer extends EnergyContainer {

    public EnergyMatrixContainer(ContainerType<?> container, int windowId,
                                 PlayerInventory playerInventory, PoweredMachineTileEntity entity) {
        super(container, windowId, playerInventory, entity);
    }

    public EnergyMatrixContainer(ContainerType<?> container, int windowId,
                                 PlayerInventory playerInventory, PacketBuffer data) {
        super(container, windowId, playerInventory, data);
    }

    @Override
    public void initSlots() {
        // twi slots
        addContainerSlot(0, 114, 23);
        addContainerSlot(1, 114, 58);
        //addContainerBlankSlot(0, 112, 12, 135 - 112, 73 - 12);

        // Main Player Inventory
        int startPlayerInvX = 8;
        int startPlayerInvY = 84;
        addPlayerInventorySlots(startPlayerInvX, startPlayerInvY, 18);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {

        if (!(getEntity() instanceof MachineTileEntity))
            throw new IllegalStateException("Tile entity is not correct. ");

        switch (((MachineTileEntity) getEntity()).getCraftleMachineTier()) {

            case TIER_1:
                return isWithinUsableDistance(getCanInteractWithCallable(), playerIn,
                                              CraftleBlocks.ENERGY_MATRIX_TIER_1.get());
            case TIER_2:
                return isWithinUsableDistance(getCanInteractWithCallable(), playerIn,
                                              CraftleBlocks.ENERGY_MATRIX_TIER_2.get());
            case TIER_3:
                return isWithinUsableDistance(getCanInteractWithCallable(), playerIn,
                                              CraftleBlocks.ENERGY_MATRIX_TIER_3.get());
            case TIER_4:
            case UNLIMITED:
                return isWithinUsableDistance(getCanInteractWithCallable(), playerIn,
                                              CraftleBlocks.ENERGY_MATRIX_TIER_4.get());
            case BASIC:
            default:
                return isWithinUsableDistance(getCanInteractWithCallable(), playerIn,
                                              CraftleBlocks.ENERGY_MATRIX_BASIC.get());
        }
    }


}
