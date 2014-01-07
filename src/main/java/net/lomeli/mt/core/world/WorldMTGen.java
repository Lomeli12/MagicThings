package net.lomeli.mt.core.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import cpw.mods.fml.common.IWorldGenerator;

public class WorldMTGen implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.dimensionId) {
        case -1:
            generateNether(world, random, chunkX, chunkZ);
        case 0:
            generateSurface(world, random, chunkX, chunkZ);
        case 1:
            generateEnd(world, random, chunkX, chunkZ);
        }

    }

    private void generateNether(World world, Random random, int chunkX, int chunkZ) {
    }

    private void generateSurface(World world, Random random, int chunkX, int chunkZ) {
        int xCoord = chunkX + random.nextInt(16);
        int zCoord = chunkZ + random.nextInt(16);
    }

    private void generateEnd(World world, Random random, int chunkX, int chunkZ) {
    }

}
