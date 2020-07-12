package com.craftle_mod.common.network.packet;

import com.craftle_mod.common.item.EnergyItem;
import com.craftle_mod.common.tile.storage.EnergyTankTileEntity;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class EnergyItemUpdatePacket {

    private final ItemStack stack;
    private final BlockPos pos;

    public EnergyItemUpdatePacket(ItemStack stack, BlockPos pos) {
        this.stack = stack;
        this.pos = pos;
    }

    public EnergyItemUpdatePacket(@Nonnull PacketBuffer buffer) {
        stack = buffer.readItemStack();
        pos = buffer.readBlockPos();
    }

    public void encodeFromBuffer(@Nonnull PacketBuffer buffer) {
        buffer.writeItemStack(stack);
        buffer.writeBlockPos(pos);
    }

    @Nonnull
    public static EnergyItemUpdatePacket decode(PacketBuffer buffer) {
        return new EnergyItemUpdatePacket(buffer);
    }

    public static void encode(@Nonnull EnergyItemUpdatePacket energyContainerUpdatePacket,
        PacketBuffer buffer) {
        energyContainerUpdatePacket.encodeFromBuffer(buffer);
    }

    public static void handle(EnergyItemUpdatePacket msg, @Nonnull Supplier<Context> context) {

        Context ctx = context.get();

        ctx.enqueueWork(() -> {

            // on the client need to send the updated storage to container
            INetHandler handler = ctx.getNetworkManager().getNetHandler();
            if (handler instanceof ClientPlayNetHandler) {

                if (Minecraft.getInstance().world != null) {
                    TileEntity tile = Minecraft.getInstance().world.getTileEntity(msg.pos);

                    if (tile instanceof EnergyTankTileEntity) {
                        msg.handlePacket((EnergyTankTileEntity) tile,
                            Minecraft.getInstance().player);
                    }
                }

            }
        });
        ctx.setPacketHandled(true);
    }

    private void handlePacket(EnergyTankTileEntity tile, PlayerEntity playerEntity) {
        ItemStack clientStack1 = tile.getContainerContents().get(0);
        ItemStack clientStack2 = tile.getContainerContents().get(1);

        // energy inject
        if (!clientStack1.isEmpty()) {
            if (clientStack1.getItem() instanceof EnergyItem) {
                ((EnergyItem) clientStack1.getItem()).handlePacketData(stack, stack.getItem());
            }
        }

        // energy extract
        if (!clientStack2.isEmpty()) {
            if (clientStack2.getItem() instanceof EnergyItem) {
                ((EnergyItem) clientStack2.getItem()).handlePacketData(stack, stack.getItem());
            }
        }

    }


}
