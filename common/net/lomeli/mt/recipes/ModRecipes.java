package net.lomeli.mt.recipes;

import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.mt.block.ModBlocks;
import net.lomeli.mt.item.ModItems;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModRecipes {
    public static void loadRecipes() {
        itemRecipes();
        blockRecipes();
        MedKitRecipes.medKits();
        AddonRecipes.loadAddons();
    }

    private static void itemRecipes() {
        GameRegistry.addRecipe(new ItemStack(ModItems.emptyCase), new Object[] { " R ", "RCR", " R ", 'R',
                new ItemStack(Item.dyePowder, 1, 1), 'C', Block.chest });
        GameRegistry.addRecipe(new ItemStack(ModItems.ironStick, 2), new Object[] { "I", "I", 'I', Item.ingotIron });
        GameRegistry.addRecipe(new ItemStack(ModItems.ironRing), new Object[] { " S ", "SIS", " S ", 'S', ModItems.ironStick,
                'I', Item.ingotIron });
        GameRegistry.addRecipe(new ItemStack(ModItems.peaceTreaty), new Object[] { "IGI", "JPJ", "NRN", 'I', Item.dyePowder, 'N',
                Item.goldNugget, 'J', Item.ingotGold, 'R', Block.plantRed, 'G', Item.appleGold, 'P', Item.paper });
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.portaCraft), new Object[] { Item.ingotIron, Block.workbench,
                Block.pressurePlatePlanks });
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.baseMaterial), "logWood", "cobblestone", Block.sand,
                Block.dirt));
        GameRegistry.addRecipe(new ItemStack(ModItems.advMaterial), new Object[] { "RDR", "IBI", "GOG", 'O', Block.blockLapis,
                'R', Item.redstone, 'D', Item.enderPearl, 'I', Item.ingotIron, 'G', Item.ingotGold, 'B', ModItems.baseMaterial });
        GameRegistry.addRecipe(new ItemStack(ModItems.quantumMaterial, 2), new Object[] { "ARA", "RNR", "ADA", 'A',
                ModItems.advMaterial, 'R', Block.blockRedstone, 'N', Item.diamond, 'D', ModItems.neoGem });
        GameRegistry.addRecipe(new ItemStack(ModItems.flyingRing), new Object[] { "EQF", "QIP", "FPQ", 'F', Item.feather, 'Q',
                ModItems.quantumMaterial, 'E', Item.emerald, 'P', Item.eyeOfEnder, 'I', ModItems.ironRing });
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.wrench), true, "I I", "IMI", " I ", 'I',
                "ingotStamatic", 'M', ModItems.baseMaterial));

        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.conBag, true, "S S", "IGI", "WIW", 'S', Item.silk, 'I',
                "ingotStamatic", 'G', Item.ingotGold, 'W', ModBlocks.treatedWool));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.exBag, true, "ANA", "IBI", "SNS", 'S', "ingotStamatic", 'A',
                "ingotIgnious", 'N', "neoGem", 'I', Item.ingotIron, 'B', ModItems.conBag));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.tankUpgrade, true, " I ", "IAI", " I ", 'I', Item.ingotIron, 'A',
                "ingotAqua"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingotAqua, 2), Item.ingotIron, Item.bucketWater));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.liquidReader, true, " IS", "IBI", "AI ", 'S', ModItems.ironStick,
                'I', Item.ingotIron, 'B', Block.woodenButton, 'A', "ingotAqua"));

        if(!OreDictionary.getOres("dustIron").isEmpty()) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.dustStamatic), "dustIron", "cobblestone"));
        }
        if(!OreDictionary.getOres("ironDust").isEmpty()) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.dustStamatic), "ironDust", "cobblestone"));
        }

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
    }

    public static void addFurnaceRecipe(ItemStack input, ItemStack output, int xp) {
        FurnaceRecipes.smelting().addSmelting(input.itemID, input.getItemDamage(), output, xp);
    }

    public static void addFurnaceRecipe(Item input, int meta, ItemStack output, int xp) {
        FurnaceRecipes.smelting().addSmelting(input.itemID, meta, output, xp);
    }

    private static void blockRecipes() {
        GameRegistry.addRecipe(new ItemStack(ModBlocks.magicSand), new Object[] { "BPB", "BRB", "BSB", 'S', Block.sand, 'R',
                Block.blockRedstone, 'B', new ItemStack(Item.dyePowder, 1, 15), 'P', new ItemStack(Item.potion, 1, 8193) });
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machineFrame), true, "BSB", "SIS", "BSB", 'I',
                "ingotStamatic", 'B', ModItems.baseMaterial, 'S', ModItems.ironStick));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.advFrame), true, "GRG", "AMA", "GRG", 'G',
                Item.ingotGold, 'R', Item.redstone, 'A', ModItems.advMaterial, 'M', ModBlocks.machineFrame));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.clearTank), new Object[] { "IGI", "GBG", "IGI", 'G', Block.glass, 'I',
                Item.ingotIron, 'B', ModItems.ingotAqua });
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.aquaticManip), true, "ARA", "BFB", "IGI", 'I',
                Item.ingotIron, 'G', Item.ingotGold, 'B', ModBlocks.clearTank, 'A', "ingotAqua", 'R', Item.redstone, 'F',
                ModBlocks.machineFrame));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.cobGen), "SRS", "CMC", "III", 'S', Block.stone, 'R', Item.redstone, 'C',
                ModBlocks.clearTank, 'M', ModBlocks.machineFrame, 'I', Item.ingotIron);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.magmaFurnace), true, "OIO", "IAI", "OIO", 'O',
                Block.obsidian, 'A', ModBlocks.advFrame, 'I', "ingotIgnious"));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.fertileBush, true, "BMB", "IFI", "BPB", 'B', "treeLeaves", 'M',
                new ItemStack(Item.dyePowder, 1, 15), 'I', Item.ingotIron, 'P', new ItemStack(Item.potion, 1, 8193), 'F',
                ModBlocks.advFrame));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.altar, true, "SSS", "GBG", "OOO", 'S', "slabWood",
                'G', Block.blockGold, 'B',Block.beacon, 'O',Block.obsidian));
        
        treatedWoolRecipes();
    }

    private static void treatedWoolRecipes() {
        for(int i = 0; i < 16; i++) {
            GameRegistry.addShapelessRecipe(ModBlocks.treatedWoolColors[i], new Object[] { new ItemStack(Block.cloth, 1, i),
                    Item.slimeBall, Item.flint, Item.potion });
        }
    }
}
