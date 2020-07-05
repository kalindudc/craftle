package com.craftle_mod.common.network.packet;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.inventory.container.base.EnergyContainer;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class EnergyContainerCreatePacket {

    private final int windowId;
    private final BlockPos pos;

    public EnergyContainerCreatePacket(int windowId, BlockPos pos) {
        this.windowId = windowId;
        this.pos = pos;
    }

    public EnergyContainerCreatePacket(@Nonnull PacketBuffer buffer) {
        windowId = buffer.readInt();
        pos = buffer.readBlockPos();
    }

    public void encodeFromBuffer(@Nonnull PacketBuffer buffer) {
        buffer.writeInt(windowId);
        buffer.writeBlockPos(pos);
    }

    @Nonnull
    public static EnergyContainerCreatePacket decode(PacketBuffer buffer) {
        return new EnergyContainerCreatePacket(buffer);
    }

    public static void encode(@Nonnull EnergyContainerCreatePacket energyContainerCreatePacket,
        PacketBuffer buffer) {
        energyContainerCreatePacket.encodeFromBuffer(buffer);
    }

    public static void handle(EnergyContainerCreatePacket msg, @Nonnull Supplier<Context> context) {

        Context ctx = context.get();

        ctx.enqueueWork(() -> {
            // on the client need to send the updated storage to container
            ServerPlayerEntity player = ctx.getSender();

            if (player != null && player.openContainer instanceof EnergyContainer
                && player.openContainer.windowId == msg.windowId) {

                TileEntity entity = player.getEntityWorld().getTileEntity(msg.pos);

                if (entity instanceof PoweredMachineTileEntity) {

                    Craftle.packetHandler.sendToClient(new EnergyContainerUpdatePacket(msg.windowId,
                        ((PoweredMachineTileEntity) entity).getEnergyContainer(),
                        ((PoweredMachineTileEntity) entity).getEnergyInjectRate(),
                        ((PoweredMachineTileEntity) entity).getEnergyExtractRate()), player);
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
