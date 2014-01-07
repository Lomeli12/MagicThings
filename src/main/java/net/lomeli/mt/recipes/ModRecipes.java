package net.lomeli.mt.recipes;

import net.lomeli.lomlib.recipes.ShapedFluidRecipe;
import net.lomeli.lomlib.recipes.ShapelessFluidRecipe;

import net.lomeli.mt.block.ModBlocks;
import net.lomeli.mt.item.ModItems;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModRecipes {
    public static void loadRecipes() {
        itemRecipes();
        blockRecipes();
        MedKitRecipes.medKits();
        AddonRecipes.loadAddons();
    }

    private static void itemRecipes() {
        addRecipe(new ItemStack(ModItems.emptyCase), true, " R ", "RCR", " R ", 'R', new ItemStack(Item.dyePowder, 1, 1), 'C', Block.chest);
        addRecipe(new ItemStack(ModItems.standardItem, 2, 0), true, "I", "I", 'I', Item.ingotIron);
        addRecipe(new ItemStack(ModItems.standardItem, 2, 1), true, " S ", "SIS", " S ", 'S', new ItemStack(ModItems.standardItem, 2, 0), 'I', Item.ingotIron);
        addRecipe(new ItemStack(ModItems.peaceTreaty), true, "IGI", "JPJ", "NRN", 'I', Item.dyePowder, 'N', Item.goldNugget, 'J', Item.ingotGold, 'R', Block.plantRed, 'G',
                Item.appleGold, 'P', Item.paper);
        addShapelessRecipe(new ItemStack(ModItems.portaCraft), Item.ingotIron, Block.workbench, Block.pressurePlatePlanks);
        addShapelessRecipe(ModItems.materialArray[0], "logWood", "cobblestone", Block.sand, Block.dirt);
        addRecipe(ModItems.materialArray[1], true, "RDR", "IBI", "GOG", 'O', "dyeBlue", 'R', Item.redstone, 'D', Item.enderPearl, 'I', Item.ingotIron, 'G', Item.ingotGold,
                'B', ModItems.materialArray[0]);
        addRecipe(new ItemStack(ModItems.material, 2, 2), true, "ARA", "RNR", "ADA", 'A', ModItems.materialArray[1], 'R', Block.blockRedstone, 'N', Item.diamond, 'D', "neoGem");
        addRecipe(ModItems.flyingRing, true, "EQF", "QIP", "FPQ", 'F', Item.feather, 'Q', ModItems.materialArray[2], 'E', Item.emerald, 'P', Item.eyeOfEnder, 'I',
                ModItems.itemArray[1]);
        addRecipe(new ItemStack(ModItems.wrench), true, "I I", "IMI", " I ", 'I', "ingotStamatic", 'M', ModItems.materialArray[0]);

        addRecipe(ModItems.conBag, true, "S S", "IGI", "WIW", 'S', Item.silk, 'I', "ingotStamatic", 'G', Item.ingotGold, 'W', ModBlocks.treatedWool);
        addRecipe(ModItems.exBag, true, "ANA", "IBI", "SNS", 'S', "ingotStamatic", 'A', "ingotIgnious", 'N', "neoGem", 'I', Item.ingotIron, 'B', ModItems.conBag);
        addRecipe(ModItems.tankUpgrade, true, " I ", "IAI", " I ", 'I', Item.ingotIron, 'A', "ingotAqua");

        addRecipe(ModItems.liquidReader, true, " IS", "IBI", "AI ", 'S', ModItems.itemArray[0], 'I', Item.ingotIron, 'B', Block.woodenButton, 'A', "ingotAqua");

        furnaceItems();
    }

    private static void furnaceItems() {
        addFurnaceRecipe(new ItemStack(ModBlocks.machineFrame), new ItemStack(ModItems.record), 0);
        addFurnaceRecipe(new ItemStack(ModBlocks.ingotBlock, 1, 0), ModItems.ingotArray[0], 10);
        addFurnaceRecipe(new ItemStack(ModBlocks.ingotBlock, 1, 1), ModItems.ingotArray[1], 10);
        addFurnaceRecipe(new ItemStack(ModBlocks.ingotBlock, 1, 2), ModItems.ingotArray[2], 10);
        addFurnaceRecipe(new ItemStack(ModBlocks.ingotBlock, 1, 3), ModItems.ingotArray[3], 10);
        addFurnaceRecipe(new ItemStack(ModBlocks.ingotBlock, 1, 4), new ItemStack(ModItems.ingots, 2, 2), 10);

        addFurnaceRecipe(ModItems.dusts, 6, ModItems.ingotArray[0], 5);
        addFurnaceRecipe(ModItems.dusts, 7, ModItems.ingotArray[1], 5);
        addFurnaceRecipe(ModItems.dusts, 8, ModItems.ingotArray[2], 5);

        addFurnaceRecipe(ModItems.dusts, 3, ModItems.ingotArray[0], 5);
        addFurnaceRecipe(ModItems.dusts, 4, ModItems.ingotArray[1], 5);
        addFurnaceRecipe(ModItems.dusts, 5, ModItems.ingotArray[2], 5);

        addFurnaceRecipe(new ItemStack(Item.brick), ModItems.ingotArray[4], 2);
    }

    private static void blockRecipes() {
        addRecipe(new ItemStack(ModBlocks.magicSand), "BPB", "BRB", "BSB", 'S', Block.sand, 'R', Block.blockRedstone, 'B', new ItemStack(Item.dyePowder, 1, 15), 'P',
                new ItemStack(Item.potion, 1, 8193));
        addRecipe(ModBlocks.frames[0], true, "BSB", "SIS", "BSB", 'I', "ingotStamatic", 'B', ModItems.materialArray[0], 'S', ModItems.itemArray[0]);
        addRecipe(ModBlocks.frames[1], true, "GRG", "AMA", "GRG", 'G', Item.ingotGold, 'R', Item.redstone, 'A', ModItems.materialArray[1], 'M', ModBlocks.frames[0]);
        addRecipe(new ItemStack(ModBlocks.clearTank), true, "IGI", "GBG", "IGI", 'G', Block.glass, 'I', Item.ingotIron, 'B', ModItems.ingotArray[1]);
        addRecipe(new ItemStack(ModBlocks.aquaticManip), true, "ARA", "BFB", "IGI", 'I', Item.ingotIron, 'G', Item.ingotGold, 'B', ModBlocks.clearTank, 'A', "ingotAqua", 'R',
                Item.redstone, 'F', ModBlocks.machineFrame);
        addRecipe(new ItemStack(ModBlocks.cobGen), "SRS", "CMC", "III", 'S', Block.stone, 'R', Item.redstone, 'C', ModBlocks.clearTank, 'M', ModBlocks.machineFrame, 'I',
                Item.ingotIron);
        addRecipe(new ItemStack(ModBlocks.magmaFurnace), true, "OIO", "IAI", "OIO", 'O', Block.obsidian, 'A', ModBlocks.frames[1], 'I', "ingotIgnious");
        addRecipe(ModBlocks.fertileBush, true, "BMB", "IFI", "BPB", 'B', "treeLeaves", 'M', new ItemStack(Item.dyePowder, 1, 15), 'I', Item.ingotIron, 'P', new ItemStack(
                Item.potion, 1, 8193), 'F', ModBlocks.frames[1]);
        addRecipe(new ItemStack(ModBlocks.glass, 4), true, "RGR", "GSG", "RGR", 'R', Item.redstone, 'G', Block.glass, 'S', "ingotStamatic");
        addShapelessRecipe(new ItemStack(ModBlocks.glass, 1, 1), new ItemStack(ModBlocks.glass, 1, 0), Item.glowstone);
        
        treatedWoolRecipes();
        smokedBrickRecipes();
    }

    private static void treatedWoolRecipes() {
        for (int i = 0; i < 16; i++) {
            addShapelessRecipe(ModBlocks.treatedWoolColors[i], new ItemStack(Block.cloth, 1, i), Item.slimeBall, Item.flint, Item.potion);
        }
    }

    private static void smokedBrickRecipes() {
        addShapelessRecipe(ModBlocks.decorBlocks[0], ModItems.ingotArray[4], ModItems.ingotArray[4], ModItems.ingotArray[4], ModItems.ingotArray[4]);
        addShapelessRecipe(ModBlocks.decorBlocks[1], ModItems.ingotArray[4], ModItems.ingotArray[4], ModItems.ingotArray[4], ModItems.ingotArray[4], "dyeRed");
        addShapelessRecipe(ModBlocks.decorBlocks[2], ModItems.ingotArray[4], ModItems.ingotArray[4], ModItems.ingotArray[4], ModItems.ingotArray[4], "dyeGreen");
        addShapelessRecipe(ModBlocks.decorBlocks[3], ModItems.ingotArray[4], ModItems.ingotArray[4], ModItems.ingotArray[4], ModItems.ingotArray[4], "dyeBlue");

        addShapelessRecipe(ModBlocks.decorBlocks[0], new ItemStack(ModBlocks.decor, 1, OreDictionary.WILDCARD_VALUE), "dyeBlack");
        addShapelessRecipe(ModBlocks.decorBlocks[1], new ItemStack(ModBlocks.decor, 1, OreDictionary.WILDCARD_VALUE), "dyeRed");
        addShapelessRecipe(ModBlocks.decorBlocks[2], new ItemStack(ModBlocks.decor, 1, OreDictionary.WILDCARD_VALUE), "dyeGreen");
        addShapelessRecipe(ModBlocks.decorBlocks[3], new ItemStack(ModBlocks.decor, 1, OreDictionary.WILDCARD_VALUE), "dyeBlue");
    }

    public static void addFurnaceRecipe(ItemStack input, ItemStack output, int xp) {
        FurnaceRecipes.smelting().addSmelting(input.itemID, input.getItemDamage(), output, xp);
    }

    public static void addFurnaceRecipe(Item input, int meta, ItemStack output, int xp) {
        FurnaceRecipes.smelting().addSmelting(input.itemID, meta, output, xp);
    }

    public static void addRecipe(ItemStack result, Object... input) {
        GameRegistry.addRecipe(new ShapedFluidRecipe(result, input));
    }

    public static void addRecipe(Block result, Object... input) {
        GameRegistry.addRecipe(new ShapedFluidRecipe(result, input));
    }

    public static void addRecipe(Item result, Object... input) {
        GameRegistry.addRecipe(new ShapedFluidRecipe(result, input));
    }

    public static void addShapelessRecipe(ItemStack result, Object... input) {
        GameRegistry.addRecipe(new ShapelessFluidRecipe(result, input));
    }

    public static void addShapelessRecipe(Block result, Object... input) {
        GameRegistry.addRecipe(new ShapelessFluidRecipe(result, input));
    }

    public static void addShapelessRecipe(Item result, Object... input) {
        GameRegistry.addRecipe(new ShapelessFluidRecipe(result, input));
    }
}
