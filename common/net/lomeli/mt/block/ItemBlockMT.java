package net.lomeli.mt.block;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMT extends ItemBlock {

    public ItemBlockMT(int par1) {
        super(par1);
    }

    @Override
    public String getItemDisplayName(ItemStack par1ItemStack) {
        return super.getItemDisplayName(par1ItemStack).substring(0, super.getItemDisplayName(par1ItemStack).length() - 5);
    }

}
