package net.lomeli.mt.core;

import net.lomeli.mt.item.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabMT extends CreativeTabs {

    public CreativeTabMT(int par1, String par2Str) {
        super(par1, par2Str);
    }

    @Override
    public ItemStack getIconItemStack() {
        return new ItemStack(ModItems.wrench);
    }

    @Override
    public String getTranslatedTabLabel() {
        return this.getTabLabel();
    }
}
