package com.craftle_mod.common.inventory.container.base;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class EnergyContainer extends CraftleContainer {


    private double energy;

    public EnergyContainer(ContainerType<?> container, int windowId,
        PlayerInventory playerInventory, PoweredMachineTileEntity entity) {
        super(container, windowId, playerInventory, entity);

        this.energy = entity.getEnergyContainer().getEnergy();
    }

    public EnergyContainer(ContainerType<?> container, int windowId,
        PlayerInventory playerInventory, PacketBuffer data) {
        super(container, windowId, playerInventory, data);

        if (!(getEntity() instanceof PoweredMachineTileEntity)) {
            throw new IllegalStateException("Tile entity is not correct. ");
        }

        this.energy = ((PoweredMachineTileEntity) getEntity()).getEnergyContainer().getEnergy();
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        if (this.getEntity() instanceof PoweredMachineTileEntity) {

            double energy = ((PoweredMachineTileEntity) this.getEntity()).getEnergyContainer()
                .getEnergy();

            if (this.energy != energy) {
                this.energy = energy;
            }

            Craftle.logInfo("AT Test on runtime: %d (%s)", listeners.size(), listeners);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public double getEnergy() {
        return this.energy;
    }
}
