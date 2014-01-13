package net.lomeli.mt.core.world;

import java.util.Random;

import net.lomeli.lomlib.worldgen.OreData;
import net.lomeli.lomlib.worldgen.WorldGenMinableCluster;
import net.lomeli.lomlib.worldgen.WorldGenNether;
import net.lomeli.lomlib.worldgen.WorldGenSubmergedOre;

import net.lomeli.mt.block.ModBlocks;
import net.lomeli.mt.lib.BlockInfo;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import cpw.mods.fml.common.IWorldGenerator;

public class WorldMTGen implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.dimensionId) {
        case -1:
            generateNether(world, random, chunkX, chunkZ, chunkGenerator, chunkProvider);
        case 0:
            generateSurface(world, random, chunkX, chunkZ, chunkGenerator, chunkProvider);
        case 1:
            generateEnd(world, random, chunkX, chunkZ, chunkGenerator, chunkProvider);
        }

    }

    private void generateNether(World world, Random random, int chunkX, int chunkZ, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        new WorldGenNether(new OreData(230, 3, BlockInfo.netherIgniousOreGen, 1, 9, ModBlocks.ingotBlocks[4])).generate(random, chunkX, chunkZ, world, chunkGenerator,
                chunkProvider);
    }

    private void generateSurface(World world, Random random, int chunkX, int chunkZ, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        new WorldGenMinableCluster(new OreData(70, 3, BlockInfo.stamaticOreGen, 1, 7, ModBlocks.ingotBlocks[0])).generate(random, chunkX, chunkZ, world, chunkGenerator,
                chunkProvider);
        new WorldGenMinableCluster(new OreData(50, 3, BlockInfo.igniousOreGen, 1, 5, ModBlocks.ingotBlocks[2])).generate(random, chunkX, chunkZ, world, chunkGenerator,
                chunkProvider);
        new WorldGenMinableCluster(new OreData(30, 3, BlockInfo.neoOreGen, 1, 2, ModBlocks.ingotBlocks[3])).generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);

        new WorldGenSubmergedOre(new OreData(150, 30, BlockInfo.aquaticOreGen, 1, 10, ModBlocks.ingotBlocks[1])).generate(random, chunkX, chunkZ, world, chunkGenerator,
                chunkProvider);
    }

    private void generateEnd(World world, Random random, int chunkX, int chunkZ, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
    }

}
