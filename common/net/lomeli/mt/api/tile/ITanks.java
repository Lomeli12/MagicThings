package net.lomeli.mt.api.tile;

import net.minecraftforge.fluids.FluidTank;

public interface ITanks extends IMTTile {
    public FluidTank getMainTank();

    public boolean hasMultipleTanks();

    public FluidTank[] getTanks();
}
