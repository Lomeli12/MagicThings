package net.lomeli.mt.inventory;

import net.lomeli.mt.item.ItemCondenseBag;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBag extends Slot {

    public SlotBag(IInventory par1iInventory, int par2, int par3, int par4) {
        super(par1iInventory, par2, par3, par4);
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack) {
        try {
            return !(par1ItemStack.getItem() instanceof ItemCondenseBag || (Class.forName("forestry.storage.items.ItemBackpack").isInstance(par1ItemStack.getItem())));
        } catch (ClassNotFoundException e) {
            return true;
        }
    }

}
