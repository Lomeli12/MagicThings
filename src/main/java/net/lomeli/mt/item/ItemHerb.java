package net.lomeli.mt.item;

import java.util.List;

import net.lomeli.mt.MThings;
import net.lomeli.mt.lib.Strings;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemHerb extends ItemMT {

    public Icon[] iconArray;

    public ItemHerb(int par1) {
        super(par1, ":herb_");
        this.setMaxDamage(0);
        this.setCreativeTab(MThings.modtab);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (player != null && !player.capabilities.isCreativeMode) {
            int meta = itemStack.getItemDamage();
            switch (meta) {
            case 0:
                player.heal(2F);
                break;
            case 1:
                player.removePotionEffect(Potion.poison.id);
                break;
            case 3:
                player.addPotionEffect(new PotionEffect(22, 500, 1));
                break;
            }
            itemStack.stackSize--;
        }
        return itemStack;
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        iconArray = new Icon[4];
        for (int i = 0; i < iconArray.length; i++) {
            iconArray[i] = iconRegister.registerIcon(Strings.MOD_ID + this.itemTexture + i);
        }
    }

    @Override
    public Icon getIconFromDamage(int i) {
        return iconArray[i];
    }

    @Override
    public int getMetadata(int par1) {
        return par1;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubItems(int itemID, CreativeTabs tabs, List list) {
        for (int i = 0; i < iconArray.length; i++) {
            list.add(new ItemStack(itemID, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
    }
}
