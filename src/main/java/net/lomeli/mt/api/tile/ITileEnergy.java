package net.lomeli.mt.api.tile;

public interface ITileEnergy extends IMTTile {
    public float getMaxEnergy();

    public float getEnergy();

    public float getAmountNeeded();

    public boolean isActive();

    public void setMaxEnergy(float max);

    public void setEnergy(float energy);

    /**
     * Adds to the tile's energy buffer
     * 
     * @param energy
     * @param simulated
     *            - If TRUE, the charge will only be simulated
     * @return energy after it's been added
     */
    public float addEnergy(float energy, boolean simulated);

    /**
     * Uses some of the Tile's energy buffer
     * 
     * @param energy
     *            - amount of energy to be used
     * @param simulated
     *            - If TRUE, the charge will only be simulated
     * @return if tile had enough energy
     */
    public boolean useEnergy(float energy, boolean simulated);

    public float getEnergyScaled(int i);
}
