package com.craftle_mod.common.inventory.container.machine;

import com.craftle_mod.common.inventory.container.base.EnergyContainer;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ProducerContainer extends EnergyContainer {


    public ProducerContainer(ContainerType<?> container, int windowId,
        PlayerInventory playerInventory, PoweredMachineTileEntity entity) {
        super(container, windowId, playerInventory, entity);
    }

    public ProducerContainer(ContainerType<?> container, int windowId,
        PlayerInventory playerInventory, PacketBuffer data) {
        super(container, windowId, playerInventory, data);
    }

    @OnlyIn(Dist.CLIENT)
    public double getProducingPercentage() {
        return 0;
    }
}
