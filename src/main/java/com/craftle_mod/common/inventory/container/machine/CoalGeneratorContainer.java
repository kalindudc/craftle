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
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CoalGeneratorContainer extends EnergyContainer {


    private final IIntArray generatorData;

    public CoalGeneratorContainer(ContainerType<?> container, int windowId,
        PlayerInventory playerInventory, PoweredMachineTileEntity entity,
        IIntArray generatorDataIn) {
        super(container, windowId, playerInventory, entity);
        initSlots();
        generatorData = generatorDataIn;

        this.trackIntArray(generatorDataIn);
    }

    public CoalGeneratorContainer(ContainerType<?> container, int windowId,
        PlayerInventory playerInventory, PacketBuffer data, IIntArray generatorDataIn) {
        super(container, windowId, playerInventory, data);
        initSlots();
        generatorData = generatorDataIn;

        this.trackIntArray(generatorDataIn);
    }

    public CoalGeneratorContainer(int windowId, PlayerInventory playerInventory,
        PacketBuffer packetBuffer) {
        this(CraftleContainerTypes.COAL_GENERATOR.get(), windowId, playerInventory, packetBuffer,
            new IntArray(2));
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

    @OnlyIn(Dist.CLIENT)
    public int getBurnPercentage() {
        if (generatorData.get(0) == 0 || generatorData.get(1) == 0) {
            return 0;
        }
        return (int) (100D * (1D - ((double) generatorData.get(0) / (double) generatorData
            .get(1))));
    }
}
