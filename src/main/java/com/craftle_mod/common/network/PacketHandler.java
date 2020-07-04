package com.craftle_mod.common.network;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.network.packet.EnergyContainerCreatePacket;
import com.craftle_mod.common.network.packet.EnergyContainerUpdatePacket;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/**
 * PacketHandler for CLIENT to SERVER synchronization.
 *
 * <p>Implementation derived from https://mcforge.readthedocs.io/en/latest/networking/simpleimpl/.
 * </p>
 */
public class PacketHandler {

    public static final SimpleChannel channel = NetworkRegistry.ChannelBuilder
        .named(new ResourceLocation(Craftle.MODID))
        .clientAcceptedVersions(getProtocolVersion()::equals)
        .serverAcceptedVersions(getProtocolVersion()::equals)
        .networkProtocolVersion(PacketHandler::getProtocolVersion).simpleChannel();

    public static String getProtocolVersion() {
        return Craftle.getInstance() == null ? "-1.-1.-1"
            : Craftle.getInstance().getVersion().toString();
    }

    private int index;

    public PacketHandler() {
        index = 0;
    }


    /**
     * Register the packets.
     *
     * @param type is the actual packet class MSG.
     * @param encoder is a BiConsumer<MSG, PacketBuffer> responsible for encoding the message into
     * the provided PacketBuffer
     * @param decoder is a Function<PacketBuffer, MSG> responsible for decoding the message from the
     * provided PacketBuffer
     * @param consumer is a BiConsumer<MSG, Supplier<NetworkEvent.Context>> responsible for handling
     * the message itself
     * @param <MSG> the MSG is the implicit first argument.
     */
    public <MSG> void register(Class<MSG> type, BiConsumer<MSG, PacketBuffer> encoder,
        Function<PacketBuffer, MSG> decoder, BiConsumer<MSG, Supplier<Context>> consumer) {
        channel.registerMessage(index++, type, encoder, decoder, consumer);
    }

    public void init() {

        register(EnergyContainerUpdatePacket.class, EnergyContainerUpdatePacket::encode,
            EnergyContainerUpdatePacket::decode, EnergyContainerUpdatePacket::handle);

        register(EnergyContainerCreatePacket.class, EnergyContainerCreatePacket::encode,
            EnergyContainerCreatePacket::decode, EnergyContainerCreatePacket::handle);
    }

    public <MSG> void sendToClient(MSG packet, @Nonnull ServerPlayerEntity player) {
        channel.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public <MSG> void sendToServer(MSG packet) {
        channel.sendToServer(packet);
    }
}
