package com.craftle_mod.common.inventory.container.base;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.capability.energy.ICraftleEnergyStorage;
import com.craftle_mod.common.network.packet.EnergyContainerCreatePacket;
import com.craftle_mod.common.network.packet.EnergyContainerUpdatePacket;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnergyContainer extends CraftleContainer {


    private final ICraftleEnergyStorage storage;
    private double extractRate;
    private double injectRate;

    public EnergyContainer(ContainerType<?> container, int windowId,
        PlayerInventory playerInventory, PoweredMachineTileEntity entity) {
        super(container, windowId, playerInventory, entity);

        storage = entity.getEnergyContainer().copy();
        //sendPacketToClient(new EnergyContainerUpdatePacket(this.windowId, this.storage));
        injectRate = entity.getEnergyInjectRate();
        extractRate = entity.getEnergyExtractRate();
    }

    public EnergyContainer(ContainerType<?> container, int windowId,
        PlayerInventory playerInventory, PacketBuffer data) {
        super(container, windowId, playerInventory, data);

        if (!(getEntity() instanceof PoweredMachineTileEntity)) {
            throw new IllegalStateException("Tile entity is not correct. ");
        }

        storage = ((PoweredMachineTileEntity) getEntity()).getEnergyContainer().copy();
        injectRate = ((PoweredMachineTileEntity) getEntity()).getEnergyInjectRate();
        extractRate = ((PoweredMachineTileEntity) getEntity()).getEnergyExtractRate();
    }

    @Override
    public void init() {
        super.init();
        sendPacketToServer(new EnergyContainerCreatePacket(windowId, getEntity().getPos()));
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (!listeners.isEmpty() && this.getEntity() instanceof PoweredMachineTileEntity) {

            boolean dirtyData = false;

            PoweredMachineTileEntity tile = (PoweredMachineTileEntity) this.getEntity();
            ICraftleEnergyStorage container = tile.getEnergyContainer();

            if (!this.storage.equals(container)) {
                // We are out of sync and need to sync and update the client
                this.storage.copyFrom(container);
                dirtyData = true;
            }

            if (tile.getEnergyInjectRate() != injectRate
                || tile.getEnergyExtractRate() != extractRate) {
                this.injectRate = tile.getEnergyInjectRate();
                this.extractRate = tile.getEnergyExtractRate();
                dirtyData = true;
            }

            if (dirtyData) {
                // send packet to listener
                sendPacketToClient(
                    new EnergyContainerUpdatePacket(this.windowId, this.storage, this.injectRate,
                        this.extractRate));
            }
        }
    }

    private void sendPacketToClient(EnergyContainerUpdatePacket packet) {
        for (IContainerListener listener : listeners) {
            if (listener instanceof ServerPlayerEntity) {
                Craftle.packetHandler.sendToClient(packet, (ServerPlayerEntity) listener);
            }
        }
    }

    private void sendPacketToServer(EnergyContainerCreatePacket packet) {
        Craftle.packetHandler.sendToServer(packet);
    }

    public void handlePacket(ICraftleEnergyStorage data) {
        storage.copyFrom(data);
        ((PoweredMachineTileEntity) this.getEntity()).synchronizeEnergyContainer(storage);
    }

    public void handlePacket(double injectRate, double extractRate) {
        this.injectRate = injectRate;
        this.extractRate = extractRate;
    }

    @OnlyIn(Dist.CLIENT)
    public ICraftleEnergyStorage getEnergyContainer() {
        return this.storage;
    }

    @OnlyIn(Dist.CLIENT)
    public double getInjectRate() {
        return this.injectRate;
    }

    @OnlyIn(Dist.CLIENT)
    public double getExtractRate() {
        return this.extractRate;
    }


}
