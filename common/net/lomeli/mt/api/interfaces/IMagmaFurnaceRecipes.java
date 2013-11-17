package net.lomeli.mt.api.interfaces;

import java.util.HashMap;
import java.util.List;

import net.minecraft.item.ItemStack;

public interface IMagmaFurnaceRecipes {

    public void addSmelting(int id, int meta, int outputAmount);

    public void addSmelting(int id, int outputAmount);

    public void addSmelting(ItemStack itemStack, int outputAmount);

    public boolean isItemValid(int id, int meta);

    public boolean isItemValid(ItemStack stack);

    public int getResult(int id, int meta);

    public int getResult(ItemStack stack);

    public HashMap<List<Integer>, Integer> getList();
}
