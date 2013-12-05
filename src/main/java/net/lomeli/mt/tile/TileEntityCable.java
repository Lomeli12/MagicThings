package net.lomeli.mt.tile;

import net.lomeli.mt.api.tile.ITileEnergy;

import net.minecraft.tileentity.TileEntity;

public class TileEntityCable extends TileEntity implements ITileEnergy {
    private int energy;

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.worldObj.isRemote) {
            for (int x = this.xCoord - 1; x <= this.xCoord + 1; x++)
                for (int y = this.xCoord - 1; y <= this.yCoord + 1; y++)
                    for (int z = this.zCoord - 1; z <= this.zCoord + 1; z++) {
                        if (this.isActive()) {

                        }
                    }
        }
    }

    @Override
    public int getMaxEnergy() {
        return (32 * (1 + this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord)));
    }

    @Override
    public int getEnergy() {
        return this.energy;
    }

    @Override
    public int getAmountNeeded() {
        return this.getMaxEnergy() - this.getEnergy();
    }

    @Override
    public boolean isActive() {
        return this.getEnergy() > (this.getMaxEnergy() / 2);
    }

    @Override
    public void setMaxEnergy(int max) {
    }

    @Override
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    @Override
    public int addEnergy(int energy, boolean simulated) {
        if (simulated && !worldObj.isRemote) {
            this.energy += energy;
            if (this.energy > this.getMaxEnergy())
                this.energy = this.getMaxEnergy();
            return this.energy;
        } else {
            int j = this.energy;
            return (j + energy);
        }
    }

    @Override
    public boolean useEnergy(int energy, boolean simulated) {
        if (simulated && !worldObj.isRemote) {
            if (useEnergy(energy, false)) {
                this.energy -= energy;
                return true;
            } else
                return false;
        } else {
            return (this.energy > 0 && this.energy >= energy);
        }
    }

}
