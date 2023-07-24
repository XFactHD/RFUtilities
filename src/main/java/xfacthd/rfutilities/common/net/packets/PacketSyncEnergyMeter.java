package xfacthd.rfutilities.common.net.packets;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;
import xfacthd.rfutilities.client.net.ClientNetwork;

import java.util.function.Supplier;

public record PacketSyncEnergyMeter(BlockPos pos, int current, long accumulated)
{
    public void encode(FriendlyByteBuf buf)
    {
        buf.writeBlockPos(pos);
        buf.writeInt(current);
        buf.writeLong(accumulated);
    }

    public static PacketSyncEnergyMeter decode(FriendlyByteBuf buf)
    {
        return new PacketSyncEnergyMeter(buf.readBlockPos(), buf.readInt(), buf.readLong());
    }

    @SuppressWarnings("unused")
    public void handle(Supplier<NetworkEvent.Context> ctx)
    {
        if (FMLEnvironment.dist.isClient())
        {
            ClientNetwork.handleSyncEnergyMeter(this);
        }
    }
}
