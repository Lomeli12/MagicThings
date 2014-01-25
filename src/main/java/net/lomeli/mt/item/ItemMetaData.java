package net.lomeli.mt.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMetaData extends ItemMT {
    @SideOnly(Side.CLIENT)
    protected Icon[] iconArray;

    protected int n;

    public ItemMetaData(int id, String Texture, int itemNumber) {
        super(id, Texture);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.n = itemNumber;
    }

    @Override
    public int getMetadata(int par1) {
        return par1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        iconArray = new Icon[this.n];
        for (int i = 0; i < iconArray.length; i++) {
            iconArray[i] = iconRegister.registerIcon(this.modID + ":" + this.itemTexture + i);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int meta) {
        return meta <  iconArray.length ? iconArray[meta] : iconArray[0];
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(int itemID, CreativeTabs tabs, List list) {
        for (int i = 0; i < iconArray.length; i++) {
            list.add(new ItemStack(itemID, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
    }
}
