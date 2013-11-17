package net.lomeli.mt.api.interfaces;

import java.util.HashMap;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IFlyingRingFuel {
    public void registerFuel(ItemStack stack, int value);

    public void registerFuel(Item item, int value);

    public void registerFuel(int id, int meta, int value);

    public boolean isFuelRegistered(int id, int meta);

    public boolean isFuelRegistered(ItemStack stack);

    public int getFuelValue(int id, int meta);

    public int getFuelValue(ItemStack stack);

    public HashMap<List<Integer>, Integer> getList();
}
