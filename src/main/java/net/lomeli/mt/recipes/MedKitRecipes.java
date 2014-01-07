package net.lomeli.mt.recipes;

import net.lomeli.mt.item.ModItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

public class MedKitRecipes {
    public static void medKits() {
        recipe(0, ModItems.herbArray[0]);
        recipe(1, ModItems.herbArray[0], ModItems.herbArray[1]);
        recipe(2, ModItems.herbArray[0], ModItems.herbArray[3]);
        singleItem(3, ModItems.herbArray[0]);
        recipe(4, ModItems.herbArray[0], ModItems.herbArray[2], ModItems.herbArray[1]);
        recipe(5, ModItems.herbArray[0], ModItems.herbArray[2], ModItems.herbArray[3]);
        recipe(6, ModItems.herbArray[0], ModItems.herbArray[2]);
        recipe(6, ModItems.herbArray[0], ModItems.herbArray[0], ModItems.herbArray[0]);
    }

    private static void recipe(int meta, ItemStack stack1, ItemStack stack2, ItemStack stack3) {
        GameRegistry.addRecipe(new ItemStack(ModItems.medKit, 1, meta), new Object[] { " P ", "STY", " C ", 'C', ModItems.emptyCase, 'P', Item.paper, 'S', stack1, 'T', stack2,
                'Y', stack3 });
        GameRegistry.addRecipe(new ItemStack(ModItems.medKit, 1, meta), new Object[] { " P ", "YTS", " C ", 'C', ModItems.emptyCase, 'P', Item.paper, 'S', stack1, 'T', stack2,
                'Y', stack3 });
    }

    private static void recipe(int meta, ItemStack stack) {
        GameRegistry.addRecipe(new ItemStack(ModItems.medKit, 1, meta), new Object[] { "P", "G", "C", 'P', Item.paper, 'C', ModItems.emptyCase, 'G', stack });
    }

    private static void recipe(int meta, ItemStack stack1, ItemStack stack2) {
        GameRegistry.addRecipe(new ItemStack(ModItems.medKit, 1, meta), new Object[] { " P ", "ST ", " C ", 'C', ModItems.emptyCase, 'P', Item.paper, 'S', stack1, 'T', stack2 });
        GameRegistry.addRecipe(new ItemStack(ModItems.medKit, 1, meta), new Object[] { " P ", "TS ", " C ", 'C', ModItems.emptyCase, 'P', Item.paper, 'S', stack1, 'T', stack2 });
        GameRegistry.addRecipe(new ItemStack(ModItems.medKit, 1, meta), new Object[] { " P ", " ST", " C ", 'C', ModItems.emptyCase, 'P', Item.paper, 'S', stack1, 'T', stack2 });
        GameRegistry.addRecipe(new ItemStack(ModItems.medKit, 1, meta), new Object[] { " P ", " TS", " C ", 'C', ModItems.emptyCase, 'P', Item.paper, 'S', stack1, 'T', stack2 });
        GameRegistry.addRecipe(new ItemStack(ModItems.medKit, 1, meta), new Object[] { " P ", "S T", " C ", 'C', ModItems.emptyCase, 'P', Item.paper, 'S', stack1, 'T', stack2 });
        GameRegistry.addRecipe(new ItemStack(ModItems.medKit, 1, meta), new Object[] { " P ", "T S", " C ", 'C', ModItems.emptyCase, 'P', Item.paper, 'S', stack1, 'T', stack2 });
    }

    private static void singleItem(int meta, ItemStack stack) {
        recipe(meta, stack, stack);
    }
}
