package net.lomeli.mt.recipes;

import forestry.api.core.ItemInterface;
import forestry.api.recipes.RecipeManagers;
import ic2.api.item.Items;
import ic2.api.recipe.ICraftingRecipeManager;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;
import appeng.api.IGrinderRecipeManager;
import appeng.api.Materials;
import appeng.api.Util;
import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.lomlib.block.BlockUtil;
import net.lomeli.lomlib.util.ModLoaded;

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

public class AddonRecipes {
    private static boolean[] useModRecipe;

    public static void loadAddons() {
        useModRecipe = new boolean[] { false, false, false, false };
        ic2Recipes();
        appengRecipes();
        forestryRecipes();
        bcRecipes();

        getModdedRecipes();
    }

    private static void bcRecipes() {
        if(ModLoaded.isModInstalled("BuildCraft|Factory")) {
            ItemStack tankBlock = BlockUtil.getBlockFromMod("tankBlock", "buildcraft.BuildCraftFactory");
            GameRegistry.addRecipe(new ItemStack(ModBlocks.clearTank), " I ", "ITI", " I ", 'I', Item.ingotIron, 'T', tankBlock);
        }
    }

    private static void ic2Recipes() {
        if(ModLoaded.isModInstalled("IC2")) {
            NBTTagCompound metadata = new NBTTagCompound();

            useModRecipe[0] = true;

            RecipeInputItemStack stamatic = new RecipeInputItemStack(new ItemStack(ModItems.ingotStamatic, 1, 0));
            RecipeInputItemStack aquatic = new RecipeInputItemStack(new ItemStack(ModItems.ingotAqua, 1, 0));
            RecipeInputItemStack ignious = new RecipeInputItemStack(new ItemStack(ModItems.ingotIgnious, 1, 0));

            RecipeInputItemStack stamaticDust = new RecipeInputItemStack(new ItemStack(ModItems.purifiedDust, 1, 3));
            RecipeInputItemStack aquaticDust = new RecipeInputItemStack(new ItemStack(ModItems.purifiedDust, 1, 4));
            RecipeInputItemStack igniousDust = new RecipeInputItemStack(new ItemStack(ModItems.purifiedDust, 1, 5));

            RecipeInputItemStack pureStamaticDust = new RecipeInputItemStack(new ItemStack(ModItems.purifiedDust, 1, 0));
            RecipeInputItemStack pureAquaticDust = new RecipeInputItemStack(new ItemStack(ModItems.purifiedDust, 1, 1));
            RecipeInputItemStack pureIgniousDust = new RecipeInputItemStack(new ItemStack(ModItems.purifiedDust, 1, 2));

            RecipeInputItemStack stamaticOre = new RecipeInputItemStack(new ItemStack(ModBlocks.stamaticOre));
            RecipeInputItemStack aquaticOre = new RecipeInputItemStack(new ItemStack(ModBlocks.aquaticOre));
            RecipeInputItemStack igniousOre = new RecipeInputItemStack(new ItemStack(ModBlocks.igniousOre));

            Recipes.macerator.addRecipe(stamatic, metadata, new ItemStack(ModItems.dustStamatic));
            Recipes.macerator.addRecipe(aquatic, metadata, new ItemStack(ModItems.dustAqua));
            Recipes.macerator.addRecipe(ignious, metadata, new ItemStack(ModItems.dustIgnious));

            Recipes.macerator.addRecipe(stamaticOre, metadata, new ItemStack(ModItems.purifiedDust, 2, 3));
            Recipes.macerator.addRecipe(aquaticOre, metadata, new ItemStack(ModItems.purifiedDust, 2, 4));
            Recipes.macerator.addRecipe(igniousOre, metadata, new ItemStack(ModItems.purifiedDust, 2, 5));

            metadata.setInteger("amount", 1000);

            Recipes.oreWashing.addRecipe(stamaticDust, metadata, new ItemStack(ModItems.purifiedDust, 1, 0));
            Recipes.oreWashing.addRecipe(aquaticDust, metadata, new ItemStack(ModItems.purifiedDust, 1, 1));
            Recipes.oreWashing.addRecipe(igniousDust, metadata, new ItemStack(ModItems.purifiedDust, 1, 2));

            metadata.setInteger("minHeat", 500);

            Recipes.centrifuge.addRecipe(pureStamaticDust, metadata, new ItemStack(ModItems.dustStamatic, 1, 1));
            Recipes.centrifuge.addRecipe(pureAquaticDust, metadata, new ItemStack(ModItems.dustAqua, 1, 1));
            Recipes.centrifuge.addRecipe(pureIgniousDust, metadata, new ItemStack(ModItems.dustIgnious, 1, 1));
        }
    }

    private static void forestryRecipes() {
        if(ModLoaded.isModInstalled("Forestry", false)) {
            useModRecipe[1] = true;

            RecipeManagers.carpenterManager.addRecipe(150, new FluidStack(FluidRegistry.WATER, 1000), null, new ItemStack(
                    ModItems.ingotAqua, 2), new Object[] { "I  ", 'I', Item.ingotIron });
            RecipeManagers.carpenterManager.addRecipe(200, new FluidStack(FluidRegistry.LAVA, 1000), null, new ItemStack(
                    ModItems.ingotIgnious, 2), new Object[] { "I  ", 'I', Item.ingotGold });

            for(int i = 0; i < 16; i++) {
                RecipeManagers.carpenterManager.addRecipe(150, new FluidStack(FluidRegistry.WATER, 500), null,
                        ModBlocks.treatedWoolColors[i], new Object[] { "FSW", 'F', Item.flint, 'S', Item.slimeBall, 'W',
                                new ItemStack(Block.cloth, 1, i) });
            }
        }
    }

    private static void appengRecipes() {
        if(ModLoaded.isModInstalled("AppliedEnergistics")) {
            useModRecipe[2] = true;
            IGrinderRecipeManager grinder = Util.getGrinderRecipeManage();

            grinder.addRecipe(new ItemStack(ModBlocks.stamaticOre), new ItemStack(ModItems.dustStamatic, 2), 10);
            grinder.addRecipe(new ItemStack(ModBlocks.aquaticOre), new ItemStack(ModItems.dustAqua, 2), 20);
            grinder.addRecipe(new ItemStack(ModBlocks.igniousOre), new ItemStack(ModItems.dustIgnious, 2), 15);
        }
    }

    private static void getModdedRecipes() {
        boolean useNewRecipe = false;
        for(int i = 0; i < useModRecipe.length; i++) {
            if(useModRecipe[i]) {
                useNewRecipe = true;
                break;
            }
        }
        if(useNewRecipe) {
            if(useModRecipe[0]) {
                ICraftingRecipeManager advRecipes = Recipes.advRecipes;
                ItemStack battery = new ItemStack(Items.getItem("chargedReBattery").getItem(), 1, OreDictionary.WILDCARD_VALUE);
                ItemStack matter = Items.getItem("matter");

                advRecipes.addRecipe(new ItemStack(ModItems.shaver), new Object[] { "IFI", "ISI", "IBI", 'I', "plateRefinedIron",
                        'F', Items.getItem("electronicCircuit"), 'S', Item.shears, 'B', battery });

                advRecipes.addRecipe(new ItemStack(ModItems.quantumMaterial), new Object[] { "MMM", "MDM", "MMM", 'M', matter,
                        'D', Item.diamond });
            }
            if(useModRecipe[1]) {
                ItemStack circuitBoard = ItemInterface.getItem("circuitboards").copy();
                ItemStack sturdyCase = ItemInterface.getItem("sturdyCasing").copy();

                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.shaver), true, "BCB", "BSB", "BTB", 'B',
                        "ingotBronze", 'C', circuitBoard, 'S', Item.shears, 'T', sturdyCase));
            }
            if(useModRecipe[2]) {
                ItemStack quartz = Materials.matQuartz.copy();
                ItemStack flux = Materials.matFluxCrystal.copy();
                ItemStack silicon = Materials.matSilicon.copy();
                GameRegistry.addRecipe(new ItemStack(ModItems.shaver), new Object[] { "IFI", "ISI", "IRI", 'I', quartz, 'S',
                        Item.shears, 'R', flux, 'F', silicon });
            }
        }else {
            GameRegistry.addRecipe(new ItemStack(ModItems.shaver), new Object[] { "IFI", "ISI", "IRI", 'I', Item.ingotIron, 'S',
                    Item.shears, 'R', Block.blockRedstone, 'F', Item.flint });
        }
    }
}
