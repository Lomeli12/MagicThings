package net.lomeli.mt.recipes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import net.lomeli.mt.MThings;
import net.lomeli.mt.api.interfaces.IFlyingRingFuel;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FlyingRingFuelRegistry implements IFlyingRingFuel {
    private HashMap<List<Integer>, Integer> itemList = new HashMap<List<Integer>, Integer>();

    public FlyingRingFuelRegistry() {
        this.registerFuel(Item.glowstone, 150);
        this.registerFuel(Item.netherQuartz, 80);
        this.registerFuel(Item.coal, 50);
        this.registerFuel(Item.redstone, 30);
    }

    public void registerFuel(ItemStack stack, int value) {
        if(stack != null)
            registerFuel(stack.itemID, stack.getItemDamage(), value);
    }

    public void registerFuel(Item item, int value) {
        if(item != null)
            registerFuel(item.itemID, 0, value);
    }

    public void registerFuel(int id, int meta, int value) {
        if(MThings.debug)
            MThings.logger.log(Level.INFO, "[Magic Things]: Reigstered Item id: " + id + " metadata: " + meta);
        itemList.put(Arrays.asList(id, meta), value);
    }

    public boolean isFuelRegistered(int id, int meta) {
        return itemList.containsKey(Arrays.asList(id, meta));
    }

    public boolean isFuelRegistered(ItemStack stack) {
        return isFuelRegistered(stack.itemID, stack.getItemDamage());
    }

    public int getFuelValue(int id, int meta) {
        return itemList.get(Arrays.asList(id, meta));
    }

    public int getFuelValue(ItemStack stack) {
        return getFuelValue(stack.itemID, stack.getItemDamage());
    }

    public HashMap<List<Integer>, Integer> getList() {
        return itemList;
    }
}
