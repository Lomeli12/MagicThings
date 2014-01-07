package net.lomeli.mt.block;

import net.lomeli.mt.block.BlockDecor.ItemDecor;
import net.lomeli.mt.block.BlockFancyGlass.ItemGlassBlock;
import net.lomeli.mt.block.BlockTreatedWool.ItemTreatedWool;
import net.lomeli.mt.block.func.BlockAquaticManipulator;
import net.lomeli.mt.block.func.BlockBrightAir;
import net.lomeli.mt.block.func.BlockClearTank;
import net.lomeli.mt.block.func.BlockCobbleGen;
import net.lomeli.mt.block.func.BlockFertileBush;
import net.lomeli.mt.block.func.BlockMagicSand;
import net.lomeli.mt.block.func.BlockMagmaFurnace;
import net.lomeli.mt.block.func.BlockBrightAir.ItemAirBlock;
import net.lomeli.mt.lib.BlockInfo;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
    public static Block ingotBlock, infectedBlock, cobGen, fertileBush, magicSand, aquaticManip, clearTank, machineFrame, magmaFurnace, treatedWool, decor, glass, air;

    public static ItemStack[] treatedWoolColors, decorBlocks, ingotBlocks, frames;

    public static void loadBlocks() {
        initBlocks();
        registerBlocks();
    }

    private static void initBlocks() {
        treatedWoolColors = new ItemStack[16];
        decorBlocks = new ItemStack[4];
        frames = new ItemStack[2];
        ingotBlocks = new ItemStack[9];

        ingotBlock = new BlockMetaData(BlockInfo.ingotBlockID, Material.rock, "ingotBlock_", 9).setHardness(0.5F).setResistance(20F).setHardness(3.0F).setResistance(5.0F)
                .setUnlocalizedName("ingotBlocks").setStepSound(Block.soundStoneFootstep);
        machineFrame = new BlockItem(BlockInfo.machineFrameID, "frame_", 2).setUnlocalizedName("machineFrame");

        infectedBlock = new BlockInfected(BlockInfo.infectedBlockID).setResistance(20F).setUnlocalizedName("InfectedBlock");
        cobGen = new BlockCobbleGen(BlockInfo.compactGenID).setUnlocalizedName("CobGen");
        fertileBush = new BlockFertileBush(BlockInfo.fertileBushID).setUnlocalizedName("FertileBush");
        magicSand = new BlockMagicSand(BlockInfo.magicSandID).setUnlocalizedName("MagicSand");
        aquaticManip = new BlockAquaticManipulator(BlockInfo.aquaticManipID).setUnlocalizedName("AquaticManipulator");
        clearTank = new BlockClearTank(BlockInfo.clearTankID).setUnlocalizedName("ClearTank");
        magmaFurnace = new BlockMagmaFurnace(BlockInfo.magmaFurnaceID).setUnlocalizedName("MagmaFurnace");
        treatedWool = new BlockTreatedWool(BlockInfo.treatedWoolID);
        decor = new BlockDecor(BlockInfo.decorID);
        glass = new BlockFancyGlass(BlockInfo.glassID);
        air = new BlockBrightAir(BlockInfo.airID);
    }

    private static void registerBlock(Block blk, String name) {
        GameRegistry.registerBlock(blk, name);
    }

    private static void registerBlocks() {
        registerBlock(infectedBlock, "Infected Block");
        registerBlock(cobGen, "Compact CobGen");
        registerBlock(fertileBush, "Fertile Bush");
        registerBlock(magicSand, "Magic Sand");
        registerBlock(aquaticManip, "Aquatic Manipulator");
        registerBlock(clearTank, "Clear Tank");
        registerBlock(magmaFurnace, "Magma Furnace");

        GameRegistry.registerBlock(treatedWool, ItemTreatedWool.class, "Treated Wool");
        GameRegistry.registerBlock(decor, ItemDecor.class, "Decor Block");
        GameRegistry.registerBlock(ingotBlock, ItemIngotBlocks.class, "Ingot Blocks");
        GameRegistry.registerBlock(machineFrame, ItemFrameBlocks.class, "Machine Frames");
        GameRegistry.registerBlock(glass, ItemGlassBlock.class, "Fancy Glass");
        GameRegistry.registerBlock(air, ItemAirBlock.class, "Air");

        for (int i = 0; i < 16; i++) {
            treatedWoolColors[i] = new ItemStack(treatedWool, 1, i);
            if (i < 9)
                ingotBlocks[i] = new ItemStack(ingotBlock, 1, i);
            if (i < 4)
                decorBlocks[i] = new ItemStack(decor, 1, i);
            if (i < 2)
                frames[i] = new ItemStack(machineFrame, 1, i);
        }

        for (int i = 0; i < 5; i++) {
            MinecraftForge.setBlockHarvestLevel(ingotBlock, i, "pickaxe", 2);
        }

        OreDictionary.registerOre("oreStamatic", ingotBlocks[0]);
        OreDictionary.registerOre("oreAquatic", ingotBlocks[1]);
        OreDictionary.registerOre("oreIgnious", ingotBlocks[2]);
        OreDictionary.registerOre("oreNeo", ingotBlocks[3]);
        OreDictionary.registerOre("oreIgnious", ingotBlocks[4]);
    }
}
