package net.lomeli.mt.recipes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.lomeli.mt.api.interfaces.IMagmaFurnaceRecipes;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

public class MagmaFurnaceRecipe implements IMagmaFurnaceRecipes {
    private HashMap<List<Integer>, Integer> smeltingList = new HashMap<List<Integer>, Integer>();

    public MagmaFurnaceRecipe() {
        for(int i = 0; i < OreDictionary.getOres("stone").size(); i++) {
            addSmelting(OreDictionary.getOres("stone").get(i), 10);
        }
        for(int i = 0; i < OreDictionary.getOres("cobblestone").size(); i++) {
            addSmelting(OreDictionary.getOres("cobblestone").get(i), 8);
        }

        addSmelting(Block.obsidian.blockID, 25);
        addSmelting(Item.ingotIron.itemID, 15);
        addSmelting(Item.ingotGold.itemID, 16);
        addSmelting(Item.diamond.itemID, 20);
        addSmelting(Block.blockIron.blockID, 140);
        addSmelting(Block.blockGold.blockID, 145);
        addSmelting(Block.blockDiamond.blockID, 185);
    }

    public void addSmelting(int id, int meta, int outputAmount) {
        smeltingList.put(Arrays.asList(id, meta), outputAmount);
    }

    public void addSmelting(int id, int outputAmount) {
        addSmelting(id, 0, outputAmount);
    }

    public void addSmelting(ItemStack itemStack, int outputAmount) {
        addSmelting(itemStack.itemID, itemStack.getItemDamage(), outputAmount);
    }

    public boolean isItemValid(int id, int meta) {
        return smeltingList.containsKey(Arrays.asList(id, meta));
    }

    public boolean isItemValid(ItemStack stack) {
        return isItemValid(stack.itemID, stack.getItemDamage());
    }

    public int getResult(int id, int meta) {
        if(isItemValid(id, meta)) {
            return smeltingList.get(Arrays.asList(id, meta));
        }
        return 0;
    }

    public int getResult(ItemStack stack) {
        return getResult(stack.itemID, stack.getItemDamage());
    }

    public HashMap<List<Integer>, Integer> getList() {
        return smeltingList;
    }
}
