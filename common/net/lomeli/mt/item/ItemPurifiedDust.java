package net.lomeli.mt.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemPurifiedDust extends ItemMT {

    public Icon[] iconArray;

    public ItemPurifiedDust(int id) {
        super(id, "dust_");
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public int getMetadata(int par1) {
        return par1;
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        iconArray = new Icon[6];
        for(int i = 0; i < iconArray.length; i++) {
            iconArray[i] = iconRegister.registerIcon(this.modID + ":" + this.itemTexture + i);
        }
    }

    @Override
    public Icon getIconFromDamage(int i) {
        return iconArray[i];
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubItems(int itemID, CreativeTabs tabs, List list) {
        for(int i = 0; i < iconArray.length; i++) {
            list.add(new ItemStack(itemID, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
    }
}
