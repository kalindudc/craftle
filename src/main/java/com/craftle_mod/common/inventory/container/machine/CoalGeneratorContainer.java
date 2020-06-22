package com.craftle_mod.common.inventory.container.machine;

import com.craftle_mod.common.inventory.container.base.EnergyContainer;
import com.craftle_mod.common.registries.CraftleBlocks;
import com.craftle_mod.common.registries.CraftleContainerTypes;
import com.craftle_mod.common.tile.base.MachineTileEntity;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;

public class CoalGeneratorContainer extends EnergyContainer {


    public CoalGeneratorContainer(ContainerType<?> container, int windowId,
        PlayerInventory playerInventory,
        PoweredMachineTileEntity entity) {
        super(container, windowId, playerInventory, entity);
        initSlots();
    }

    public CoalGeneratorContainer(ContainerType<?> container, int windowId,
        PlayerInventory playerInventory, PacketBuffer data) {
        super(container, windowId, playerInventory, data);
        initSlots();
    }

    public CoalGeneratorContainer(int windowId, PlayerInventory playerInventory,
        PacketBuffer packetBuffer) {
        this(CraftleContainerTypes.COAL_GENERATOR.get(), windowId, playerInventory, packetBuffer);
    }

    @Override
    public void initSlots() {
        // one slot
        addContainerSlot(0, 80, 20);
        //addContainerBlankSlot(0, 112, 12, 135 - 112, 73 - 12);

        // Main Player Inventory
        int startPlayerInvX = 8;
        int startPlayerInvY = 84;
        addPlayerInventorySlots(startPlayerInvX, startPlayerInvY, 18);
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {

        if (!(getEntity() instanceof MachineTileEntity)) {
            throw new IllegalStateException("Tile entity is not correct. ");
        }

        return isWithinUsableDistance(getCanInteractWithCallable(), playerIn,
            CraftleBlocks.COAL_GENERATOR.get());
    }
}
