package xfacthd.rfutilities.common.net;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import xfacthd.rfutilities.RFUtilities;
import xfacthd.rfutilities.common.net.packets.PacketSyncEnergyMeter;

public class Network
{
    private static final String PROTOCOL_VERSION = "1";
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(RFUtilities.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    @SuppressWarnings("UnusedAssignment")
    public static void init()
    {
        int id = 0;

        CHANNEL.messageBuilder(PacketSyncEnergyMeter.class, id++)
                .encoder(PacketSyncEnergyMeter::encode)
                .decoder(PacketSyncEnergyMeter::decode)
                .consumerMainThread(PacketSyncEnergyMeter::handle)
                .add();
    }

    public static void sendToClientsTracking(Object packet, BlockEntity be)
    {
        //noinspection ConstantConditions
        sendToClientsTracking(packet, be.getLevel().getChunkAt(be.getBlockPos()));
    }

    public static void sendToClientsTracking(Object packet, LevelChunk chunk)
    {
        CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), packet);
    }
}
