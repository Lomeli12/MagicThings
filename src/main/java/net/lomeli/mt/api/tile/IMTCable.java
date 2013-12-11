package net.lomeli.mt.api.tile;

public interface IMTCable extends ITileEnergy{
    /**
     * Amount of energy a cable can carry, extract, and insert from machines
     * @return
     */
    public float getEnergyCarry();
    
    /**
     * Is next to generator or battery. If this is true, make sure to ONLY drain from this cable.
     * @return
     */
    public boolean source();
    
    /**
     * Is next to machine? If true, ONLY give energy to this cable
     * @return
     */
    public boolean machine();
}
