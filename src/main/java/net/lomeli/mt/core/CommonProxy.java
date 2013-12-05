package net.lomeli.mt.core;

import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.mt.tile.TileEntityAquaticManipulator;
import net.lomeli.mt.tile.TileEntityClearTank;
import net.lomeli.mt.tile.TileEntityCompactCobGen;
import net.lomeli.mt.tile.TileEntityMagmaFurnace;
import net.lomeli.mt.tile.TileEntityUnwinder;

public class CommonProxy {
    public void registerTickHandlers() {
    }

    public void registerTileEntites() {
        String tile = "net.lomeli.mt.tile.";
        GameRegistry.registerTileEntity(TileEntityCompactCobGen.class, tile + "CobGenCompact");
        GameRegistry.registerTileEntity(TileEntityAquaticManipulator.class, tile + "AquaManip");
        GameRegistry.registerTileEntity(TileEntityClearTank.class, tile + "ClearTank");
        GameRegistry.registerTileEntity(TileEntityMagmaFurnace.class, tile + "MagmaFurnace");
        GameRegistry.registerTileEntity(TileEntityUnwinder.class, tile + "Unwinder");
    }

    public void registerRenders() {
    }

    public void registerSounds() {
    }

    public void registerIcons() {
    }
}
