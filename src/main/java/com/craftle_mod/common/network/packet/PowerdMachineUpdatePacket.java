package com.craftle_mod.common.network.packet;

import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import com.craftle_mod.common.tile.storage.EnergyTankTileEntity;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PowerdMachineUpdatePacket {

    private final CompoundNBT tag;
    private final BlockPos pos;

    public PowerdMachineUpdatePacket(PoweredMachineTileEntity tileEntity) {
        this.tag = tileEntity.getTileUpdateTag();
        this.pos = tileEntity.getPos();
    }

    public PowerdMachineUpdatePacket(@Nonnull PacketBuffer buffer) {
        tag = buffer.readCompoundTag();
        pos = buffer.readBlockPos();
    }

    public void encodeFromBuffer(@Nonnull PacketBuffer buffer) {
        buffer.writeCompoundTag(tag);
        buffer.writeBlockPos(pos);
    }

    @Nonnull
    public static PowerdMachineUpdatePacket decode(PacketBuffer buffer) {
        return new PowerdMachineUpdatePacket(buffer);
    }

    public static void encode(@Nonnull PowerdMachineUpdatePacket energyContainerUpdatePacket,
        PacketBuffer buffer) {
        energyContainerUpdatePacket.encodeFromBuffer(buffer);
    }

    public static void handle(PowerdMachineUpdatePacket msg, @Nonnull Supplier<Context> context) {

        Context ctx = context.get();

        ctx.enqueueWork(() -> {

            // on the client need to send the updated storage to container
            INetHandler handler = ctx.getNetworkManager().getNetHandler();
            if (handler instanceof ClientPlayNetHandler) {

                if (Minecraft.getInstance().world != null) {
                    TileEntity tile = Minecraft.getInstance().world.getTileEntity(msg.pos);

                    if (tile instanceof EnergyTankTileEntity) {
                        msg.handlePacket((EnergyTankTileEntity) tile);
                    }
                }

            }
        });
        ctx.setPacketHandled(true);
    }

    private void handlePacket(EnergyTankTileEntity tile) {
        tile.handlePacket(tag);
    }

}
