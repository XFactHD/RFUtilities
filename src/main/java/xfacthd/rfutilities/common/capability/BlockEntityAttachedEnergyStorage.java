package xfacthd.rfutilities.common.capability;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.EnergyStorage;

import java.util.Objects;

@SuppressWarnings("unused")
public class BlockEntityAttachedEnergyStorage extends EnergyStorage
{
    private final BlockEntity owner;

    public BlockEntityAttachedEnergyStorage(BlockEntity owner, int capacity, int maxTransfer)
    {
        super(capacity, maxTransfer);
        this.owner = Objects.requireNonNull(owner);
    }

    public BlockEntityAttachedEnergyStorage(BlockEntity owner, int capacity, int maxReceive, int maxExtract)
    {
        super(capacity, maxReceive, maxExtract);
        this.owner = Objects.requireNonNull(owner);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        int result = super.receiveEnergy(maxReceive, simulate);
        if (!simulate && result > 0)
        {
            owner.setChanged();
        }
        return result;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        int result = super.extractEnergy(maxExtract, simulate);
        if (!simulate && result > 0)
        {
            owner.setChanged();
        }
        return result;
    }

    public int getMaxReceive() { return maxReceive; }

    public int getMaxExtract() { return maxExtract; }
}
