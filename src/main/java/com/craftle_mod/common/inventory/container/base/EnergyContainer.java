package com.craftle_mod.common.inventory.container.base;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.capability.energy.ICraftleEnergyStorage;
import com.craftle_mod.common.network.packet.EnergyContainerUpdatePacket;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class EnergyContainer extends CraftleContainer {


    private final ICraftleEnergyStorage storage;

    public EnergyContainer(ContainerType<?> container, int windowId,
        PlayerInventory playerInventory, PoweredMachineTileEntity entity) {
        super(container, windowId, playerInventory, entity);

        storage = entity.getEnergyContainer().copy();
    }

    public EnergyContainer(ContainerType<?> container, int windowId,
        PlayerInventory playerInventory, PacketBuffer data) {
        super(container, windowId, playerInventory, data);

        if (!(getEntity() instanceof PoweredMachineTileEntity)) {
            throw new IllegalStateException("Tile entity is not correct. ");
        }

        this.storage = ((PoweredMachineTileEntity) getEntity()).getEnergyContainer().copy();
    }

    private void synchronizeEnergyContainer() {
        if (!listeners.isEmpty() && this.getEntity() instanceof PoweredMachineTileEntity) {

            ICraftleEnergyStorage container = ((PoweredMachineTileEntity) this.getEntity())
                .getEnergyContainer();

            if (!this.storage.equals(container)) {
                // We are out of sync and need to sync and update the client
                this.storage.copyFrom(container);

                // send packet to listener
                sendPacket(new EnergyContainerUpdatePacket(this.windowId, this.storage));
            }
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        synchronizeEnergyContainer();
    }

    private void sendPacket(EnergyContainerUpdatePacket packet) {
        for (IContainerListener listener : listeners) {
            if (listener instanceof ServerPlayerEntity) {
                Craftle.packetHandler.sendToClient(packet, (ServerPlayerEntity) listener);
            }
        }
    }

    public void handlePacket(ICraftleEnergyStorage data) {
        storage.copyFrom(data);
        ((PoweredMachineTileEntity) this.getEntity()).synchronizeEnergyContainer(storage);
    }

    @OnlyIn(Dist.CLIENT)
    public ICraftleEnergyStorage getEnergyContainer() {
        return this.storage;
    }


}
