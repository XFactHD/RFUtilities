package xfacthd.rfutilities.common.capability;

import net.minecraftforge.energy.IEnergyStorage;

public record OneWayEnergyStorage(IEnergyStorage wrapped, boolean input) implements IEnergyStorage
{
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        return input ? wrapped.receiveEnergy(maxReceive, simulate) : 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        return input ? 0 : wrapped.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored() { return wrapped.getEnergyStored(); }

    @Override
    public int getMaxEnergyStored() { return wrapped.getMaxEnergyStored(); }

    @Override
    public boolean canExtract() { return !input && wrapped.canExtract(); }

    @Override
    public boolean canReceive() { return input && wrapped.canReceive(); }
}
