package net.lomeli.mt.recipes;

import java.lang.reflect.Method;
import java.util.logging.Level;

import net.lomeli.lomlib.block.BlockUtil;
import net.lomeli.lomlib.util.ModLoaded;

import net.lomeli.mt.MThings;
import net.lomeli.mt.block.ModBlocks;
import net.lomeli.mt.item.ModItems;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import cpw.mods.fml.common.registry.GameRegistry;

import forestry.api.recipes.RecipeManagers;

import appeng.api.IGrinderRecipeManager;
import appeng.api.Materials;
import appeng.api.Util;

import thermalexpansion.item.TEItems;
import thermalexpansion.util.crafting.PulverizerManager;

import ic2.api.item.Items;
import ic2.api.recipe.ICraftingRecipeManager;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;

public class AddonRecipes {
    private static boolean[] useModRecipe;

    public static void loadAddons() {
        useModRecipe = new boolean[] { false, false, false, false, false };
        ic2Recipes();
        appengRecipes();
        forestryRecipes();
        bcRecipes();
        teRecipes();

        getModdedRecipes();
    }

    private static void bcRecipes() {
        if (ModLoaded.isModInstalled("BuildCraft|Factory")) {
            ItemStack tankBlock = BlockUtil.getBlockFromMod("tankBlock", "buildcraft.BuildCraftFactory");
            GameRegistry.addRecipe(new ItemStack(ModBlocks.clearTank), " I ", "ITI", " I ", 'I', Item.ingotIron, 'T', tankBlock);
        }
    }

    private static void ic2Recipes() {
        if (ModLoaded.isModInstalled("IC2")) {
            NBTTagCompound metadata = new NBTTagCompound();

            useModRecipe[0] = true;

            RecipeInputItemStack stamatic = new RecipeInputItemStack(ModItems.ingotArray[0]);
            RecipeInputItemStack aquatic = new RecipeInputItemStack(ModItems.ingotArray[1]);
            RecipeInputItemStack ignious = new RecipeInputItemStack(ModItems.ingotArray[2]);

            RecipeInputItemStack stamaticDust = new RecipeInputItemStack(new ItemStack(ModItems.dusts, 1, 3));
            RecipeInputItemStack aquaticDust = new RecipeInputItemStack(new ItemStack(ModItems.dusts, 1, 4));
            RecipeInputItemStack igniousDust = new RecipeInputItemStack(new ItemStack(ModItems.dusts, 1, 5));

            RecipeInputItemStack pureStamaticDust = new RecipeInputItemStack(new ItemStack(ModItems.dusts, 1, 0));
            RecipeInputItemStack pureAquaticDust = new RecipeInputItemStack(new ItemStack(ModItems.dusts, 1, 1));
            RecipeInputItemStack pureIgniousDust = new RecipeInputItemStack(new ItemStack(ModItems.dusts, 1, 2));

            RecipeInputItemStack stamaticOre = new RecipeInputItemStack(ModBlocks.ingotBlocks[0]);
            RecipeInputItemStack aquaticOre = new RecipeInputItemStack(ModBlocks.ingotBlocks[1]);
            RecipeInputItemStack igniousOre = new RecipeInputItemStack(ModBlocks.ingotBlocks[2]);

            Recipes.macerator.addRecipe(stamatic, metadata, ModItems.dustArray[6]);
            Recipes.macerator.addRecipe(aquatic, metadata, ModItems.dustArray[7]);
            Recipes.macerator.addRecipe(ignious, metadata, ModItems.dustArray[8]);

            Recipes.macerator.addRecipe(stamaticOre, metadata, new ItemStack(ModItems.dusts, 2, 3));
            Recipes.macerator.addRecipe(aquaticOre, metadata, new ItemStack(ModItems.dusts, 2, 4));
            Recipes.macerator.addRecipe(igniousOre, metadata, new ItemStack(ModItems.dusts, 2, 5));

            metadata.setInteger("amount", 1000);

            Recipes.oreWashing.addRecipe(stamaticDust, metadata, new ItemStack(ModItems.dusts, 1, 0));
            Recipes.oreWashing.addRecipe(aquaticDust, metadata, new ItemStack(ModItems.dusts, 1, 1));
            Recipes.oreWashing.addRecipe(igniousDust, metadata, new ItemStack(ModItems.dusts, 1, 2));

            metadata.setInteger("minHeat", 500);

            Recipes.centrifuge.addRecipe(pureStamaticDust, metadata, new ItemStack(ModItems.dusts, 2, 6));
            Recipes.centrifuge.addRecipe(pureAquaticDust, metadata, new ItemStack(ModItems.dusts, 2, 7));
            Recipes.centrifuge.addRecipe(pureIgniousDust, metadata, new ItemStack(ModItems.dusts, 2, 8));
        }
    }

    private static void forestryRecipes() {
        if (ModLoaded.isModInstalled("Forestry", false)) {
            useModRecipe[1] = true;

            RecipeManagers.carpenterManager.addRecipe(150, new FluidStack(FluidRegistry.WATER, 1000), null, new ItemStack(ModItems.ingots, 2, 1), new Object[] { "I  ", 'I',
                    Item.ingotIron });
            RecipeManagers.carpenterManager.addRecipe(200, new FluidStack(FluidRegistry.LAVA, 1000), null, new ItemStack(ModItems.ingots, 2, 2), new Object[] { "I  ", 'I',
                    Item.ingotGold });

            for (int i = 0; i < 16; i++) {
                RecipeManagers.carpenterManager.addRecipe(150, new FluidStack(FluidRegistry.WATER, 500), null, ModBlocks.treatedWoolColors[i], new Object[] { "FSW", 'F',
                        Item.flint, 'S', Item.slimeBall, 'W', new ItemStack(Block.cloth, 1, i) });
            }
        }
    }

    private static void appengRecipes() {
        if (ModLoaded.isModInstalled("AppliedEnergistics")) {
            useModRecipe[2] = true;
            IGrinderRecipeManager grinder = Util.getGrinderRecipeManage();

            grinder.addRecipe(ModBlocks.ingotBlocks[0], new ItemStack(ModItems.dusts, 2, 6), 10);
            grinder.addRecipe(ModBlocks.ingotBlocks[1], new ItemStack(ModItems.dusts, 2, 7), 20);
            grinder.addRecipe(ModBlocks.ingotBlocks[2], new ItemStack(ModItems.dusts, 2, 8), 15);
        }
    }

    private static void teRecipes() {
        if (ModLoaded.isModInstalled("ThermalExpansion")) {
            useModRecipe[3] = true;
            ItemStack iron = OreDictionary.getOres("dustIron").get(0);
            ItemStack gold = OreDictionary.getOres("dustGold").get(0);

            PulverizerManager.addRecipe(4000, ModBlocks.ingotBlocks[0], new ItemStack(ModItems.dusts, 2, 6), null, 0);
            PulverizerManager.addRecipe(4000, ModBlocks.ingotBlocks[1], new ItemStack(ModItems.dusts, 2, 7), iron, 10);
            PulverizerManager.addRecipe(4000, ModBlocks.ingotBlocks[2], new ItemStack(ModItems.dusts, 2, 8), gold, 10);

            PulverizerManager.addRecipe(2500, ModItems.ingotArray[0], new ItemStack(ModItems.dusts, 1, 6), null, 0);
            PulverizerManager.addRecipe(2500, ModItems.ingotArray[1], new ItemStack(ModItems.dusts, 1, 7), null, 0);
            PulverizerManager.addRecipe(2500, ModItems.ingotArray[2], new ItemStack(ModItems.dusts, 1, 8), null, 0);
        }
    }

    private static void getModdedRecipes() {
        boolean useNewRecipe = false;
        for (int i = 0; i < useModRecipe.length; i++) {
            if (useModRecipe[i]) {
                useNewRecipe = true;
                break;
            }
        }
        if (useNewRecipe) {
            if (useModRecipe[0]) {
                ICraftingRecipeManager advRecipes = Recipes.advRecipes;
                ItemStack battery = new ItemStack(Items.getItem("chargedReBattery").getItem(), 1, OreDictionary.WILDCARD_VALUE);
                ItemStack matter = Items.getItem("matter");

                advRecipes.addRecipe(new ItemStack(ModItems.shaver), new Object[] { "IFI", "ISI", "IBI", 'I', "plateRefinedIron", 'F', Items.getItem("electronicCircuit"), 'S',
                        Item.shears, 'B', battery });

                advRecipes.addRecipe(new ItemStack(ModItems.material, 1, 2), new Object[] { "M M", "MDM", "M M", 'M', matter, 'D', Item.diamond });
            }
            if (useModRecipe[1]) {
                ItemStack circuitBoard = getForestryItem("circuitboards").copy();
                ItemStack sturdyCase = getForestryItem("sturdyCasing").copy();

                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.shaver), true, "BCB", "BSB", "BTB", 'B', "ingotBronze", 'C', circuitBoard, 'S', Item.shears, 'T',
                        sturdyCase));
            }
            if (useModRecipe[2]) {
                ItemStack quartz = Materials.matQuartz.copy();
                ItemStack flux = Materials.matFluxCrystal.copy();
                ItemStack silicon = Materials.matSilicon.copy();
                GameRegistry.addRecipe(new ItemStack(ModItems.shaver), new Object[] { "IFI", "ISI", "IRI", 'I', quartz, 'S', Item.shears, 'R', flux, 'F', silicon });
            }
            if (useModRecipe[3]) {
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.shaver), true, "TIT", "TST", "TBT", 'T', "ingotTin", 'I', TEItems.powerCoilGold, 'S',
                        Item.shears, 'B', TEItems.capacitorBasic));
            }
        } else {
            GameRegistry.addRecipe(new ItemStack(ModItems.shaver), new Object[] { "IFI", "ISI", "IRI", 'I', Item.ingotIron, 'S', Item.shears, 'R', Block.blockRedstone, 'F',
                    Item.flint });
        }
    }

    private static ItemStack getForestryItem(String ident) {
        ItemStack item = null;
        try {
            String itemClass = "forestry.core.config.ForestryItem";
            Object[] enums = Class.forName(itemClass).getEnumConstants();
            for (Object e : enums)
                if (e.toString().equals(ident)) {
                    Method m = e.getClass().getMethod("getItemStack", new Class[0]);
                    return (ItemStack) m.invoke(e, new Object[0]);
                }
        } catch (Exception e) {
            MThings.logger.log(Level.WARNING, "Could not retrieve Forestry item identified by: " + ident);
        }
        return item;
    }
}
