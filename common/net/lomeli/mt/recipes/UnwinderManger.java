package net.lomeli.mt.recipes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.lomeli.mt.api.interfaces.IUnwinder;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class UnwinderManger implements IUnwinder {
    private HashMap<List<Integer>, ItemStack> recipes = new HashMap<List<Integer>, ItemStack>();
    private HashMap<List<Integer>, Integer> cranksRequired = new HashMap<List<Integer>, Integer>();

    public UnwinderManger() {
        for(int i = 0; i < 16; i++) {
            addRecipe(new ItemStack(Block.cloth, 1, i), new ItemStack(Item.silk, 3), 10);
        }
    }

    public void addRecipe(int id, int meta, ItemStack stack, int cranks) {
        recipes.put(Arrays.asList(id, meta), stack);
        cranksRequired.put(Arrays.asList(id, meta), cranks);
    }

    public void addRecipe(ItemStack input, ItemStack stack, int cranks) {
        addRecipe(input.itemID, input.getItemDamage(), stack, cranks);
    }

    public boolean isItemValid(int id, int meta) {
        return recipes.containsKey(Arrays.asList(id, meta));
    }

    public boolean isItemValid(ItemStack stack) {
        return isItemValid(stack.itemID, stack.getItemDamage());
    }

    public ItemStack getOutput(int id, int meta) {
        ItemStack output = null;

        if(isItemValid(id, meta))
            output = recipes.get(Arrays.asList(id, meta));

        return output;
    }

    public ItemStack getOutput(ItemStack stack) {
        return getOutput(stack.itemID, stack.getItemDamage());
    }

    public int getCranksRequired(int id, int meta) {
        return cranksRequired.get(Arrays.asList(id, meta));
    }

    public int getCranksRequired(ItemStack stack) {
        return getCranksRequired(stack.itemID, stack.getItemDamage());
    }
}
