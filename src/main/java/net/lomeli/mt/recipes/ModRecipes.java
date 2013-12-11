package net.lomeli.mt.recipes;

import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.lomlib.recipes.ShapedFluidRecipe;
import net.lomeli.lomlib.recipes.ShapelessFluidRecipe;

import net.lomeli.mt.block.ModBlocks;
import net.lomeli.mt.item.ModItems;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import net.minecraftforge.oredict.OreDictionary;

public class ModRecipes {
    public static void loadRecipes() {
        itemRecipes();
        blockRecipes();
        MedKitRecipes.medKits();
        AddonRecipes.loadAddons();
    }

    private static void itemRecipes() {
        addRecipe(new ItemStack(ModItems.emptyCase), true, " R ", "RCR", " R ", 'R', new ItemStack(Item.dyePowder, 1, 1), 'C', Block.chest);
        addRecipe(new ItemStack(ModItems.ironStick, 2), true, "I", "I", 'I', Item.ingotIron);
        addRecipe(new ItemStack(ModItems.ironRing), true, " S ", "SIS", " S ", 'S', ModItems.ironStick, 'I', Item.ingotIron);
        addRecipe(new ItemStack(ModItems.peaceTreaty), true, "IGI", "JPJ", "NRN", 'I', Item.dyePowder, 'N', Item.goldNugget, 'J', Item.ingotGold, 'R', Block.plantRed, 'G',
                Item.appleGold, 'P', Item.paper);
        addShapelessRecipe(new ItemStack(ModItems.portaCraft), Item.ingotIron, Block.workbench, Block.pressurePlatePlanks);
        addShapelessRecipe(new ItemStack(ModItems.baseMaterial), "logWood", "cobblestone", Block.sand, Block.dirt);
        addRecipe(new ItemStack(ModItems.advMaterial), true, "RDR", "IBI", "GOG", 'O', Block.blockLapis, 'R', Item.redstone, 'D', Item.enderPearl, 'I', Item.ingotIron, 'G',
                Item.ingotGold, 'B', ModItems.baseMaterial);
        addRecipe(new ItemStack(ModItems.quantumMaterial, 2), true, "ARA", "RNR", "ADA", 'A', ModItems.advMaterial, 'R', Block.blockRedstone, 'N', Item.diamond, 'D',
                ModItems.neoGem);
        addRecipe(ModItems.flyingRing, true, "EQF", "QIP", "FPQ", 'F', Item.feather, 'Q', ModItems.quantumMaterial, 'E', Item.emerald, 'P', Item.eyeOfEnder, 'I', ModItems.ironRing);
        addRecipe(new ItemStack(ModItems.wrench), true, "I I", "IMI", " I ", 'I', "ingotStamatic", 'M', ModItems.baseMaterial);

        addRecipe(ModItems.conBag, true, "S S", "IGI", "WIW", 'S', Item.silk, 'I', "ingotStamatic", 'G', Item.ingotGold, 'W', ModBlocks.treatedWool);
        addRecipe(ModItems.exBag, true, "ANA", "IBI", "SNS", 'S', "ingotStamatic", 'A', "ingotIgnious", 'N', "neoGem", 'I', Item.ingotIron, 'B', ModItems.conBag);
        addRecipe(ModItems.tankUpgrade, true, " I ", "IAI", " I ", 'I', Item.ingotIron, 'A', "ingotAqua");

        addRecipe(ModItems.liquidReader, true, " IS", "IBI", "AI ", 'S', ModItems.ironStick, 'I', Item.ingotIron, 'B', Block.woodenButton, 'A', "ingotAqua");

        if (!OreDictionary.getOres("dustIron").isEmpty())
            addRecipe(new ItemStack(ModItems.dustStamatic), "dustIron", "cobblestone");
        addShapelessRecipe(new ItemStack(ModItems.ingotAqua, 2), "liquid$water", Item.ingotIron);
        addShapelessRecipe(new ItemStack(ModItems.ingotIgnious, 2), "liquid$lava", Item.ingotGold);

        furnaceItems();
    }

    private static void furnaceItems() {
        addFurnaceRecipe(new ItemStack(ModBlocks.machineFrame), new ItemStack(ModItems.record), 0);
        addFurnaceRecipe(new ItemStack(ModBlocks.stamaticOre), new ItemStack(ModItems.ingotStamatic), 10);
        addFurnaceRecipe(new ItemStack(ModBlocks.aquaticOre), new ItemStack(ModItems.ingotAqua), 10);
        addFurnaceRecipe(new ItemStack(ModBlocks.igniousOre), new ItemStack(ModItems.ingotIgnious), 10);

        addFurnaceRecipe(ModItems.dustStamatic, 1, new ItemStack(ModItems.ingotStamatic, 2), 10);
        addFurnaceRecipe(ModItems.dustAqua, 1, new ItemStack(ModItems.ingotAqua, 2), 10);
        addFurnaceRecipe(ModItems.dustIgnious, 1, new ItemStack(ModItems.ingotIgnious, 2), 10);

        addFurnaceRecipe(ModItems.dustStamatic, 0, new ItemStack(ModItems.ingotStamatic), 5);
        addFurnaceRecipe(ModItems.dustAqua, 0, new ItemStack(ModItems.ingotAqua), 5);
        addFurnaceRecipe(ModItems.dustIgnious, 0, new ItemStack(ModItems.ingotIgnious), 5);

        addFurnaceRecipe(ModItems.stamaticOre, new ItemStack(ModItems.ingotStamatic), 5);
        addFurnaceRecipe(ModItems.aquaticOre, new ItemStack(ModItems.ingotAqua), 5);
        addFurnaceRecipe(ModItems.igniousOre, new ItemStack(ModItems.ingotIgnious), 5);

        addFurnaceRecipe(new ItemStack(Item.brick), new ItemStack(ModItems.smokedBrick), 2);
    }

    private static void blockRecipes() {
        addRecipe(new ItemStack(ModBlocks.magicSand), "BPB", "BRB", "BSB", 'S', Block.sand, 'R', Block.blockRedstone, 'B', new ItemStack(Item.dyePowder, 1, 15), 'P',
                new ItemStack(Item.potion, 1, 8193));
        addRecipe(new ItemStack(ModBlocks.machineFrame), true, "BSB", "SIS", "BSB", 'I', "ingotStamatic", 'B', ModItems.baseMaterial, 'S', ModItems.ironStick);
        addRecipe(new ItemStack(ModBlocks.advFrame), true, "GRG", "AMA", "GRG", 'G', Item.ingotGold, 'R', Item.redstone, 'A', ModItems.advMaterial, 'M', ModBlocks.machineFrame);
        addRecipe(new ItemStack(ModBlocks.clearTank), true, "IGI", "GBG", "IGI", 'G', Block.glass, 'I', Item.ingotIron, 'B', ModItems.ingotAqua);
        addRecipe(new ItemStack(ModBlocks.aquaticManip), true, "ARA", "BFB", "IGI", 'I', Item.ingotIron, 'G', Item.ingotGold, 'B', ModBlocks.clearTank, 'A', "ingotAqua", 'R',
                Item.redstone, 'F', ModBlocks.machineFrame);
        addRecipe(new ItemStack(ModBlocks.cobGen), "SRS", "CMC", "III", 'S', Block.stone, 'R', Item.redstone, 'C', ModBlocks.clearTank, 'M', ModBlocks.machineFrame, 'I',
                Item.ingotIron);
        addRecipe(new ItemStack(ModBlocks.magmaFurnace), true, "OIO", "IAI", "OIO", 'O', Block.obsidian, 'A', ModBlocks.advFrame, 'I', "ingotIgnious");
        addRecipe(ModBlocks.fertileBush, true, "BMB", "IFI", "BPB", 'B', "treeLeaves", 'M', new ItemStack(Item.dyePowder, 1, 15), 'I', Item.ingotIron, 'P', new ItemStack(
                Item.potion, 1, 8193), 'F', ModBlocks.advFrame);
        addRecipe(ModBlocks.altar, true, "SSS", "GBG", "OOO", 'S', "slabWood", 'G', Block.blockGold, 'B', Block.beacon, 'O', Block.obsidian);

        treatedWoolRecipes();
        smokedBrickRecipes();
    }

    private static void treatedWoolRecipes() {
        for (int i = 0; i < 16; i++) {
            addShapelessRecipe(ModBlocks.treatedWoolColors[i], new ItemStack(Block.cloth, 1, i), Item.slimeBall, Item.flint, Item.potion);
        }
    }

    private static void smokedBrickRecipes() {
        addShapelessRecipe(ModBlocks.decorBlocks[0], ModItems.smokedBrick, ModItems.smokedBrick, ModItems.smokedBrick, ModItems.smokedBrick);
        addShapelessRecipe(ModBlocks.decorBlocks[1], ModItems.smokedBrick, ModItems.smokedBrick, ModItems.smokedBrick, ModItems.smokedBrick, "dyeRed");
        addShapelessRecipe(ModBlocks.decorBlocks[2], ModItems.smokedBrick, ModItems.smokedBrick, ModItems.smokedBrick, ModItems.smokedBrick, "dyeGreen");
        addShapelessRecipe(ModBlocks.decorBlocks[3], ModItems.smokedBrick, ModItems.smokedBrick, ModItems.smokedBrick, ModItems.smokedBrick, "dyeBlue");

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
