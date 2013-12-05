package net.lomeli.mt.item;

import net.lomeli.lomlib.item.ItemGeneric;

import net.lomeli.mt.MThings;
import net.lomeli.mt.lib.Strings;

import net.minecraft.item.Item;

public class ItemMT extends ItemGeneric {
    public ItemMT(int id, String Texture) {
        super(id, Strings.MOD_ID, Texture);
        this.setCreativeTab(MThings.modtab);
        this.setHasSubtypes(true);
    }

    @Override
    public Item setUnlocalizedName(String str) {
        super.setUnlocalizedName(Strings.MOD_ID.toLowerCase() + ":" + str);
        return this;
    }
}
