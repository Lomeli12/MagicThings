package net.lomeli.mt.block;

import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.mt.block.BlockDecor.ItemDecor;
import net.lomeli.mt.block.BlockTreatedWool.ItemTreatedWool;
import net.lomeli.mt.block.energy.BlockBatteryTier1;
import net.lomeli.mt.block.energy.BlockCable;
import net.lomeli.mt.block.energy.BlockCable.ItemCable;
import net.lomeli.mt.block.energy.BlockCoalGenerator;
import net.lomeli.mt.item.ModItems;
import net.lomeli.mt.lib.BlockInfo;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

public class ModBlocks {
    public static Block stamaticOre, aquaticOre, igniousOre, neoOre, chargeStation, unwinder, mobFarmer, infectedBlock, cobGen, fertileBush, magicSand, aquaticManip, clearTank,
            machineFrame, magmaFurnace, advFrame, altar, treatedWool, decor;

    public static Block coalGen, cable, batBox, conductiveBox, neoBox;

    public static ItemStack[] treatedWoolColors, decorBlocks, cables;

    public static void loadBlocks() {
        initBlocks();
        registerBlocks();
    }

    private static void initBlocks() {
        treatedWoolColors = new ItemStack[16];
        decorBlocks = new ItemStack[4];
        cables = new ItemStack[4];

        stamaticOre = new BlockMT(BlockInfo.stamaticOreID, Material.rock, "orestamatic").setHardness(0.5F).setResistance(20F).setHardness(3.0F).setResistance(5.0F)
                .setUnlocalizedName("StamaticOre").setStepSound(Block.soundStoneFootstep);
        aquaticOre = new BlockMT(BlockInfo.aquaticOreID, Material.rock, "oreaquatic").setHardness(0.5F).setResistance(20F).setHardness(3.0F).setResistance(5.0F)
                .setUnlocalizedName("AquaticOre").setStepSound(Block.soundStoneFootstep);
        igniousOre = new BlockMT(BlockInfo.igniousOreID, Material.rock, "oreignious").setHardness(0.5F).setResistance(20F).setHardness(3.0F).setResistance(5.0F)
                .setUnlocalizedName("IgniousOre").setStepSound(Block.soundStoneFootstep);
        neoOre = new BlockMT(BlockInfo.neoOreID, Material.rock, "neoniteOre").setDropID(ModItems.neoGem.itemID).setHardness(3.0F).setResistance(5.0F).setUnlocalizedName("NeoOre");

        unwinder = new BlockUnwinder(BlockInfo.unwindID).setUnlocalizedName("UnWinder");

        infectedBlock = new BlockInfected(BlockInfo.infectedBlockID).setResistance(20F).setUnlocalizedName("InfectedBlock");
        cobGen = new BlockCobbleGen(BlockInfo.compactGenID).setUnlocalizedName("CobGen");
        fertileBush = new BlockFertileBush(BlockInfo.fertileBushID).setUnlocalizedName("FertileBush");
        magicSand = new BlockMagicSand(BlockInfo.magicSandID).setUnlocalizedName("MagicSand");
        aquaticManip = new BlockAquaticManipulator(BlockInfo.aquaticManipID).setUnlocalizedName("AquaticManipulator");
        clearTank = new BlockClearTank(BlockInfo.clearTankID).setUnlocalizedName("ClearTank");
        machineFrame = new BlockItem(BlockInfo.machineFrameID, "machineFrame").setUnlocalizedName("MachineFrame");
        magmaFurnace = new BlockMagmaFurnace(BlockInfo.magmaFurnaceID).setUnlocalizedName("MagmaFurnace");
        advFrame = new BlockItem(BlockInfo.advFrameID, "advFrame").setUnlocalizedName("AdvFrame");
        altar = new BlockAltar(BlockInfo.altarID).setUnlocalizedName("Altar");
        treatedWool = new BlockTreatedWool(BlockInfo.treatedWoolID);
        decor = new BlockDecor(BlockInfo.decorID);
        cable = new BlockCable(BlockInfo.cableID);
        batBox = new BlockBatteryTier1(BlockInfo.bat1ID);

        coalGen = new BlockCoalGenerator(BlockInfo.coalGenID);
    }

    private static void registerBlock(Block blk, String name) {
        GameRegistry.registerBlock(blk, name);
    }

    private static void registerBlocks() {
        registerBlock(stamaticOre, "Stamatic Ore");
        registerBlock(aquaticOre, "Aquatic Ore");
        registerBlock(igniousOre, "Ignious Ore");
        registerBlock(neoOre, "Neo Ore");

        registerBlock(unwinder, "Unwinder");

        registerBlock(infectedBlock, "Infected Block");
        registerBlock(cobGen, "Compact CobGen");
        registerBlock(fertileBush, "Fertile Bush");
        registerBlock(magicSand, "Magic Sand");
        registerBlock(aquaticManip, "Aquatic Manipulator");
        registerBlock(clearTank, "Clear Tank");
        registerBlock(machineFrame, "Machine Frame");
        registerBlock(magmaFurnace, "Magma Furnace");
        registerBlock(advFrame, "Advanced Machine Frame");
        registerBlock(altar, "Altar");

        registerBlock(coalGen, "Coal Generator");
        registerBlock(batBox, "Batbox");

        GameRegistry.registerBlock(treatedWool, ItemTreatedWool.class, "Treated Wool");
        GameRegistry.registerBlock(decor, ItemDecor.class, "DecorBlock");
        GameRegistry.registerBlock(cable, ItemCable.class, "Cable");

        for (int i = 0; i < 16; i++) {
            treatedWoolColors[i] = new ItemStack(treatedWool, 1, i);
        }
        for (int i = 0; i < 4; i++) {
            decorBlocks[i] = new ItemStack(decor, 1, i);
        }
        for (int i = 0; i < 4; i++) {
            cables[i] = new ItemStack(cable, 1, i);
        }

        OreDictionary.registerOre("oreStamatic", stamaticOre);
        OreDictionary.registerOre("oreAquatic", aquaticOre);
        OreDictionary.registerOre("oreIgnious", igniousOre);
        OreDictionary.registerOre("oreNeo", neoOre);

        MinecraftForge.setBlockHarvestLevel(stamaticOre, "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(aquaticOre, "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(igniousOre, "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(neoOre, "pickaxe", 2);
    }
}
