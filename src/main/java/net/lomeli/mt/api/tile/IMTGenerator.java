package net.lomeli.mt.api.tile;

public interface IMTGenerator extends ITileEnergy {
    public float drainEnergy(float energy, boolean simulated);
}
