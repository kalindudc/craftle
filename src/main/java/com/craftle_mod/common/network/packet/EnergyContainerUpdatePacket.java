package com.craftle_mod.common.network.packet;

import com.craftle_mod.common.capability.energy.CraftleEnergyStorage;
import com.craftle_mod.common.capability.energy.ICraftleEnergyStorage;
import com.craftle_mod.common.inventory.container.base.EnergyContainer;
import com.craftle_mod.common.tier.CraftleBaseTier;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class EnergyContainerUpdatePacket {

    private final int windowId;
    private final ICraftleEnergyStorage data;


    public EnergyContainerUpdatePacket(int windowId, ICraftleEnergyStorage data) {
        this.windowId = windowId;
        this.data = data;
    }

    public EnergyContainerUpdatePacket(@Nonnull PacketBuffer buffer) {
        windowId = buffer.readInt();
        data = new CraftleEnergyStorage(buffer.readDouble(), buffer.readDouble(),
            buffer.readDouble(), buffer.readDouble(),
            CraftleBaseTier.fromString(buffer.readString()));
    }

    public void encodeFromBuffer(@Nonnull PacketBuffer buffer) {
        buffer.writeInt(windowId);
        buffer.writeDouble(data.getCapacity());
        buffer.writeDouble(data.getMaxInjectRate());
        buffer.writeDouble(data.getMaxExtractRate());
        buffer.writeDouble(data.getEnergy());
        buffer.writeString(data.getTier().getTier());
    }

    @Nonnull
    public static EnergyContainerUpdatePacket decode(PacketBuffer buffer) {
        return new EnergyContainerUpdatePacket(buffer);
    }

    public static void encode(@Nonnull EnergyContainerUpdatePacket energyContainerUpdatePacket,
        PacketBuffer buffer) {
        energyContainerUpdatePacket.encodeFromBuffer(buffer);
    }

    public static void handle(EnergyContainerUpdatePacket msg, @Nonnull Supplier<Context> context) {

        Context ctx = context.get();

        ctx.enqueueWork(() -> {

            // on the client need to send the updated storage to container
            INetHandler handler = ctx.getNetworkManager().getNetHandler();
            if (handler instanceof ClientPlayNetHandler) {
                PlayerEntity player = Minecraft.getInstance().player;

                if (player != null && player.openContainer instanceof EnergyContainer
                    && player.openContainer.windowId == msg.windowId) {
                    msg.handlePacket((EnergyContainer) player.openContainer);
                }
            }
        });
        ctx.setPacketHandled(true);
    }

    private void handlePacket(EnergyContainer openContainer) {
        openContainer.handlePacket(data);
    }
}
