package net.lomeli.mt.api.interfaces;

import net.minecraft.item.ItemStack;

public interface IUnwinder {
    
    public void addRecipe(int id, int meta, ItemStack stack, int cranks);
    
    public void addRecipe(ItemStack input, ItemStack stack, int cranks);
    
    public boolean isItemValid(int id, int meta);
    
    public boolean isItemValid(ItemStack stack);
    
    public ItemStack getOutput(int id, int meta);
    
    public ItemStack getOutput(ItemStack stack);
    
    public int getCranksRequired(int id, int meta);
    
    public int getCranksRequired(ItemStack stack);

}
