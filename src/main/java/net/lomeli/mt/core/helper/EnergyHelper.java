package net.lomeli.mt.core.helper;

import net.lomeli.mt.api.tile.ITileEnergy;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.transport.IPipe;

import universalelectricity.prefab.tile.TileEntityElectrical;

import ic2.api.energy.tile.IEnergySink;

import cofh.api.energy.IEnergyHandler;

public class EnergyHelper {
    public static TileEntity[] getAdjacentTiles(World world, int x, int y, int z) {
        TileEntity[] tiles = new TileEntity[6];
        tiles[0] = world.getBlockTileEntity(x + 1, y, z);
        tiles[1] = world.getBlockTileEntity(x - 1, y, z);
        tiles[2] = world.getBlockTileEntity(x, y + 1, z);
        tiles[3] = world.getBlockTileEntity(x, y - 1, z);
        tiles[4] = world.getBlockTileEntity(x, y, z - 1);
        tiles[5] = world.getBlockTileEntity(x, y, z - 1);
        return tiles;
    }

    public static boolean[] areValidConnections(World world, int x, int y, int z) {
        boolean[] valid = new boolean[6];
        TileEntity[] tiles = getAdjacentTiles(world, x, y, z);
        for (int i = 0; i < valid.length; i++) {
            valid[i] = isValidMachine(tiles[i]);
        }
        return valid;
    }

    public static boolean isValidMachine(TileEntity tile) {
        return (tile != null)
                && (tile instanceof ITileEnergy || tile instanceof IEnergySink || (tile instanceof IPowerReceptor && !(tile instanceof IPipe)) || tile instanceof IEnergyHandler || tile instanceof TileEntityElectrical);
    }
}
