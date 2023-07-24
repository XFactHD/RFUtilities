package xfacthd.rfutilities.common.capability;

import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class ForwardingEnergyStorage implements IEnergyStorage
{
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        return getForwardTarget()
                .map(target -> target.receiveEnergy(maxReceive, simulate))
                .orElse(0);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        return getForwardTarget()
                .map(target -> target.extractEnergy(maxExtract, simulate))
                .orElse(0);
    }

    @Override
    public int getEnergyStored()
    {
        return getForwardTarget()
                .map(IEnergyStorage::getEnergyStored)
                .orElse(0);
    }

    @Override
    public int getMaxEnergyStored()
    {
        return getForwardTarget()
                .map(IEnergyStorage::getMaxEnergyStored)
                .orElse(0);
    }

    @Override
    public boolean canExtract()
    {
        return getForwardTarget()
                .map(IEnergyStorage::canExtract)
                .orElse(false);
    }

    @Override
    public boolean canReceive()
    {
        return getForwardTarget()
                .map(IEnergyStorage::canReceive)
                .orElse(false);
    }

    public abstract LazyOptional<IEnergyStorage> getForwardTarget();
}
