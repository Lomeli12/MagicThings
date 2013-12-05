package net.lomeli.mt.api.tile;

public interface ITileEnergy extends IMTTile {
    public int getMaxEnergy();

    public int getEnergy();

    public int getAmountNeeded();

    public boolean isActive();

    public void setMaxEnergy(int max);

    public void setEnergy(int energy);

    public int addEnergy(int energy, boolean simulated);

    public boolean useEnergy(int energy, boolean simulated);
}
