package com.craftle_mod.common.inventory.container.base;

import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;

public abstract class EnergyContainer extends CraftleContainer {


    private long energy;

    public EnergyContainer(ContainerType<?> container, int windowId,
        PlayerInventory playerInventory, PoweredMachineTileEntity entity) {
        super(container, windowId, playerInventory, entity);

        this.energy = entity.getEnergyContainer().getEnergyStored();
    }

    public EnergyContainer(ContainerType<?> container, int windowId,
        PlayerInventory playerInventory, PacketBuffer data) {
        super(container, windowId, playerInventory, data);

        if (!(getEntity() instanceof PoweredMachineTileEntity)) {
            throw new IllegalStateException("Tile entity is not correct. ");
        }

        this.energy =
            ((PoweredMachineTileEntity) getEntity()).getEnergyContainer().getEnergyStored();
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        if (this.getEntity() instanceof PoweredMachineTileEntity) {

            long energy = ((PoweredMachineTileEntity) this.getEntity()).getEnergyContainer()
                .getEnergyStored();

            if (this.energy != energy) {
                this.energy = energy;
            }
        }
    }

    public long getEnergy() {
        return energy;
    }
}
