package net.lomeli.mt.core.world;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;

import net.lomeli.lomlib.worldgen.WorldGenSubmergedOre;

import net.lomeli.mt.block.ModBlocks;
import net.lomeli.mt.lib.BlockInfo;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

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
        new WorldGenMinable(ModBlocks.stamaticOre.blockID, BlockInfo.stamaticOreGen * 2).generate(world, random, xCoord, random.nextInt(70), zCoord);
        new WorldGenMinable(ModBlocks.igniousOre.blockID, BlockInfo.igniousOreGen * 2).generate(world, random, xCoord, random.nextInt(50), zCoord);
        new WorldGenSubmergedOre(ModBlocks.aquaticOre.blockID, BlockInfo.aquaticOreGen).generate(world, random, xCoord + 8,
                Math.abs(world.getTopSolidOrLiquidBlock(xCoord, zCoord)), zCoord + 8);
        new WorldGenMinable(ModBlocks.neoOre.blockID, BlockInfo.neoOreGen * 2).generate(world, random, xCoord, random.nextInt(30), zCoord);
    }

    private void generateEnd(World world, Random random, int chunkX, int chunkZ) {
    }

}
