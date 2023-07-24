package xfacthd.rfutilities.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import xfacthd.rfutilities.common.RFUContent;
import xfacthd.rfutilities.common.data.*;
import xfacthd.rfutilities.common.capability.BlockEntityAttachedEnergyStorage;
import xfacthd.rfutilities.common.capability.OneWayEnergyStorage;
import xfacthd.rfutilities.common.util.ServerConfig;
import xfacthd.rfutilities.common.util.Utils;

public class CapacitorBlockEntity extends BlockEntity
{
    private final CapacitorEnergyStorage storage;
    private final Direction sideInput;
    private final Direction sideOutput;
    private LazyOptional<IEnergyStorage> lazyStorage = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyInputStorage = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyOutputStorage = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> neighborStorage = LazyOptional.empty();

    public CapacitorBlockEntity(BlockPos pos, BlockState state, CapacitorType type)
    {
        super(RFUContent.getCapacitorBlockEntity(type), pos, state);
        this.storage = CapacitorEnergyStorage.create(this, type);
        Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        this.sideInput = facing.getClockWise();
        this.sideOutput = facing.getCounterClockWise();
    }

    public void tick()
    {
        neighborStorage.ifPresent(neighbor ->
        {
            int maxTransfer = storage.extractEnergy(storage.getMaxExtract(), true);
            int actualTransfer = neighbor.receiveEnergy(maxTransfer, false);
            storage.extractEnergy(actualTransfer, false);
        });
    }

    public void checkNeighborStorage(BlockPos neighbor)
    {
        BlockPos pos = worldPosition.relative(sideOutput);
        if (neighbor != null && !neighbor.equals(pos)) { return; }

        //noinspection ConstantConditions
        neighborStorage = Utils.tryGetNeighboringEnergyStorage(level, pos, sideOutput, this::clearNeighborRef);
    }

    private void clearNeighborRef() { neighborStorage = LazyOptional.empty(); }

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
            else if (side == null)
            {
                return lazyStorage.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad()
    {
        super.onLoad();
        lazyStorage = LazyOptional.of(() -> storage);
        lazyInputStorage = LazyOptional.of(() -> new OneWayEnergyStorage(storage, true));
        lazyOutputStorage = LazyOptional.of(() -> new OneWayEnergyStorage(storage, false));
        checkNeighborStorage(null);
    }

    @Override
    public void setRemoved()
    {
        super.setRemoved();
        lazyStorage.invalidate();
        lazyStorage = LazyOptional.empty();
        lazyInputStorage.invalidate();
        lazyInputStorage = LazyOptional.empty();
        lazyOutputStorage.invalidate();
        lazyOutputStorage = LazyOptional.empty();
        neighborStorage = LazyOptional.empty();
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.put("energy", storage.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        storage.deserializeNBT(tag.get("energy"));
    }



    private static final class CapacitorEnergyStorage extends BlockEntityAttachedEnergyStorage
    {
        private final boolean creative;

        public CapacitorEnergyStorage(CapacitorBlockEntity be, int capacity, int maxReceive, int maxExtract)
        {
            super(be, capacity, maxReceive, maxExtract);
            this.creative = capacity == -1;
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate)
        {
            return creative ? 0 : super.receiveEnergy(maxReceive, simulate);
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate)
        {
            if (creative)
            {
                return Math.min(maxExtract, this.maxExtract);
            }
            return super.extractEnergy(maxExtract, simulate);
        }

        @Override
        public int getEnergyStored() { return creative ? capacity : super.getEnergyStored(); }



        public static CapacitorEnergyStorage create(CapacitorBlockEntity be, CapacitorType type)
        {
            int capacity = ServerConfig.getCapacitorCapacity(type);
            int input = ServerConfig.getCapacitorInput(type);
            int output = ServerConfig.getCapacitorOutput(type);

            return new CapacitorEnergyStorage(be, capacity, input, output);
        }
    }
}
