package net.lomeli.mt.api.tile;

import net.minecraftforge.common.ForgeDirection;

public interface IMTBattery extends ITileEnergy{
    public boolean canDrawFromDirection(ForgeDirection direction);
    
    public void drawPower(ITileEnergy tile, float power);
    
    public void inputPower(ITileEnergy tile, float power);
}
