package xfacthd.rfutilities.common.blockentity;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import xfacthd.rfutilities.common.RFUContent;
import xfacthd.rfutilities.common.capability.ForwardingEnergyStorage;
import xfacthd.rfutilities.common.capability.OneWayEnergyStorage;
import xfacthd.rfutilities.common.net.Network;
import xfacthd.rfutilities.common.net.packets.PacketSyncEnergyMeter;
import xfacthd.rfutilities.common.util.Utils;

public class EnergyMeterBlockEntity extends BlockEntity
{
    private final MonitoredEnergyStorage storage;
    private final Direction sideInput;
    private final Direction sideOutput;
    private LazyOptional<IEnergyStorage> lazyInputStorage = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyOutputStorage = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> neighborStorage = LazyOptional.empty();
    private boolean recording = false;
    private int currentPower = 0;
    private int lastTickPower = 0;
    private long accumulatedPower = 0;

    public EnergyMeterBlockEntity(BlockPos pos, BlockState state)
    {
        super(RFUContent.BLOCK_ENTITY_ENERGY_METER.get(), pos, state);
        this.storage = new MonitoredEnergyStorage();
        Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        this.sideInput = facing.getClockWise();
        this.sideOutput = facing.getCounterClockWise();
    }

    public void tick()
    {
        boolean needSync = false;

        if (recording && currentPower > 0)
        {
            accumulatedPower += currentPower;
            setChanged();
            needSync = true;
        }

        if (currentPower != lastTickPower)
        {
            lastTickPower = currentPower;
            needSync = true;
        }
        currentPower = 0;

        if (needSync)
        {
            Network.sendToClientsTracking(new PacketSyncEnergyMeter(worldPosition, lastTickPower, accumulatedPower), this);
        }
    }

    public void updateFromPacket(PacketSyncEnergyMeter packet)
    {
        currentPower = packet.current();
        accumulatedPower = packet.accumulated();
    }

    public void checkNeighborStorage(BlockPos neighbor)
    {
        BlockPos pos = worldPosition.relative(sideOutput);
        if (neighbor != null && !neighbor.equals(pos)) { return; }

        //noinspection ConstantConditions
        neighborStorage = Utils.tryGetNeighboringEnergyStorage(level, pos, sideOutput, this::clearNeighborRef);
    }

    private void clearNeighborRef() { neighborStorage = LazyOptional.empty(); }

    public void toggleRecording()
    {
        recording = !recording;
        if (!recording)
        {
            accumulatedPower = 0;
        }

        //noinspection ConstantConditions
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        setChanged();
    }

    public void clearAccumulated()
    {
        if (recording)
        {
            accumulatedPower = 0;

            //noinspection ConstantConditions
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            setChanged();
        }
    }

    public int getCurrentPower()
    {
        //noinspection ConstantConditions
        Preconditions.checkState(level.isClientSide());
        return currentPower;
    }

    public long getAccumulatedPower()
    {
        //noinspection ConstantConditions
        Preconditions.checkState(level.isClientSide());
        return accumulatedPower;
    }

    public boolean isRecording() { return recording; }

    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side)
    {
        if (cap == ForgeCapabilities.ENERGY)
        {
            if (side == sideInput)
            {
                return lazyInputStorage.cast();
            }
            else if (side == sideOutput)
            {
                return lazyOutputStorage.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad()
    {
        super.onLoad();
        lazyInputStorage = LazyOptional.of(() -> new OneWayEnergyStorage(storage, true));
        lazyOutputStorage = LazyOptional.of(() -> new OneWayEnergyStorage(storage, false));
        checkNeighborStorage(null);
    }

    @Override
    public void setRemoved()
    {
        super.setRemoved();
        lazyInputStorage.invalidate();
        lazyInputStorage = LazyOptional.empty();
        lazyOutputStorage.invalidate();
        lazyOutputStorage = LazyOptional.empty();
        neighborStorage = LazyOptional.empty();
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() { return ClientboundBlockEntityDataPacket.create(this); }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
    {
        CompoundTag tag = pkt.getTag();
        if (tag != null)
        {
            handleUpdateTag(tag);
        }
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("recording", recording);
        tag.putLong("accumulated", accumulatedPower);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag)
    {
        recording = tag.getBoolean("recording");
        accumulatedPower = tag.getLong("accumulated");
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.putBoolean("recording", recording);
        tag.putLong("accumulated", accumulatedPower);
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        recording = tag.getBoolean("recording");
        accumulatedPower = tag.getLong("accumulated");
    }



    private final class MonitoredEnergyStorage extends ForwardingEnergyStorage
    {
        @Override
        public int receiveEnergy(int maxReceive, boolean simulate)
        {
            int result = super.receiveEnergy(maxReceive, simulate);
            if (!simulate && result > 0)
            {
                currentPower = result;
            }
            return result;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) { return 0; }

        @Override //Extraction is disabled for simplicity
        public boolean canExtract() { return false; }

        @Override
        public LazyOptional<IEnergyStorage> getForwardTarget()
        {
            return neighborStorage;
        }
    }
}
