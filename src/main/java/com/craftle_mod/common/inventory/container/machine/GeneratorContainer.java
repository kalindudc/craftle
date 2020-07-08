package com.craftle_mod.common.inventory.container.machine;

import com.craftle_mod.common.inventory.container.base.EnergyContainer;
import com.craftle_mod.common.registries.CraftleContainerTypes;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GeneratorContainer extends EnergyContainer {


    private final IIntArray generatorData;

    public GeneratorContainer(ContainerType<?> container, int windowId,
        PlayerInventory playerInventory, PoweredMachineTileEntity entity,
        IIntArray generatorDataIn) {
        super(container, windowId, playerInventory, entity);
        generatorData = generatorDataIn;

        this.trackIntArray(generatorDataIn);
    }

    public GeneratorContainer(ContainerType<?> container, int windowId,
        PlayerInventory playerInventory, PacketBuffer data, IIntArray generatorDataIn) {
        super(container, windowId, playerInventory, data);
        initSlots();
        generatorData = generatorDataIn;

        this.trackIntArray(generatorDataIn);
    }

    public GeneratorContainer(int windowId, PlayerInventory playerInventory,
        PacketBuffer packetBuffer) {
        this(CraftleContainerTypes.COAL_GENERATOR.get(), windowId, playerInventory, packetBuffer,
            new IntArray(2));
    }

    @OnlyIn(Dist.CLIENT)
    public double getBurnPercentage() {
        if (generatorData.get(0) == 0 || generatorData.get(1) == 0) {
            return 0;
        }
        return (1D - ((double) generatorData.get(0) / (double) generatorData.get(1)));
    }
}
