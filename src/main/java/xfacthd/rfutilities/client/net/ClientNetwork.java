package xfacthd.rfutilities.client.net;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import xfacthd.rfutilities.common.blockentity.EnergyMeterBlockEntity;
import xfacthd.rfutilities.common.net.packets.PacketSyncEnergyMeter;

import java.util.Objects;

public class ClientNetwork
{
    public static void handleSyncEnergyMeter(PacketSyncEnergyMeter packet)
    {
        Level level = Objects.requireNonNull(Minecraft.getInstance().level);
        if (level.isLoaded(packet.pos()) && level.getBlockEntity(packet.pos()) instanceof EnergyMeterBlockEntity be)
        {
            be.updateFromPacket(packet);
        }
    }
}
